/**
 * @Date        : 2021-04-13 21:05:15
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 优惠券领用表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import me.shy.rt.dataware.datamocker.bean.CouponInfo;
import me.shy.rt.dataware.datamocker.bean.CouponRange;
import me.shy.rt.dataware.datamocker.bean.CouponUsed;
import me.shy.rt.dataware.datamocker.bean.OrderDetail;
import me.shy.rt.dataware.datamocker.bean.OrderDetailCoupon;
import me.shy.rt.dataware.datamocker.bean.OrderInfo;
import me.shy.rt.dataware.datamocker.bean.SkuInfo;
import me.shy.rt.dataware.datamocker.bean.UserInfo;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.constant.Constant;
import me.shy.rt.dataware.datamocker.mapper.CouponUsedMapper;
import me.shy.rt.dataware.datamocker.service.CouponInfoService;
import me.shy.rt.dataware.datamocker.service.CouponUsedService;
import me.shy.rt.dataware.datamocker.service.SkuInfoService;
import me.shy.rt.dataware.datamocker.service.UserInfoService;

@Slf4j
@Service
public class CouponUsedServiceImpl extends ServiceImpl<CouponUsedMapper, CouponUsed> implements CouponUsedService {

    @Autowired
    DataMockerConfig config;
    @Autowired
    CouponUsedMapper couponUsedMapper;
    @Autowired
    SkuInfoService skuInfoService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    CouponInfoService couponInfoService;

    @Override
    public void genCoupon(Boolean isClear) {
        if (isClear) {
            remove(new QueryWrapper<>());
        }
        int usersCountOfGotCoupon = config.usersCountOfGotCoupon;
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.last("limit " + usersCountOfGotCoupon);
        Integer userTotal = userInfoService.count(userInfoQueryWrapper);

        List<CouponInfo> couponInfos = couponInfoService.list(new QueryWrapper<>());
        usersCountOfGotCoupon = Math.min(userTotal, usersCountOfGotCoupon);
        List<CouponUsed> couponUseList = new ArrayList<>();
        for (CouponInfo couponInfo : couponInfos) {
            for (int userId = 1; userId <= usersCountOfGotCoupon; userId++) {
                CouponUsed couponUsed = new CouponUsed();
                couponUsed.setCouponStatus(Constant.COUPON_STATUS_UNUSED);
                couponUsed.setGetTime(config.businessDate.atTime(LocalTime.now()));
                couponUsed.setExpireTime(couponInfo.getExpireTime());
                couponUsed.setUserId(userId + 0L);
                couponUsed.setCouponId(couponInfo.getId());
                couponUseList.add(couponUsed);
            }
        }
        log.warn("共优惠券" + couponUseList.size() + "张");
        saveBatch(couponUseList);
    }

    @Override
    public void usedCoupon(List<OrderInfo> orderInfoList) {
        List<Long> orderIds = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfoList) {
            orderIds.add(orderInfo.getId());
        }
        CouponUsed couponUsed = new CouponUsed();
        couponUsed.setUsedTime(config.businessDate.atTime(LocalTime.now()));
        couponUsed.setCouponStatus(Constant.COUPON_STATUS_USED);
        update(couponUsed, new QueryWrapper<CouponUsed>().in("order_id", orderIds));
    }

    @Override
    public Pair<List<CouponUsed>, List<OrderDetailCoupon>> usingCoupon(List<OrderInfo> orderInfoList) {
        List<CouponUsed> couponsUsedForUpdate = new ArrayList<>();
        List<CouponUsed> unUsedCoupons = couponUsedMapper.selectUnusedCouponUseListWithInfo();
        List<SkuInfo> skuInfoList = skuInfoService.list(new QueryWrapper<>());
        List<OrderDetailCoupon> orderDetailsCoupon = new ArrayList<>();

        int orderCount = 0;
        for (OrderInfo orderInfo : orderInfoList) {
            // 检查每个订单里是否有对应的活动商品 如果有随机进行优惠
            List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
            List<OrderDetailCoupon> curOrderDetailCouponList = new ArrayList<>();

            for (OrderDetail orderDetail : orderDetailList) {
                for (CouponUsed unUsedCoupon : unUsedCoupons) {
                    if (!orderInfo.getUserId().equals(unUsedCoupon.getUserId())) {
                        continue;
                    }
                    orderDetail.setSkuInfo(skuInfoService.getSkuInfoById(skuInfoList, orderDetail.getSkuId()));
                    boolean isMatched = matchCouponByRange(orderDetail, unUsedCoupon);
                    if (isMatched) {
                        OrderDetailCoupon orderDetailCoupon = OrderDetailCoupon.builder().skuId(orderDetail.getSkuId())
                                .createTime(config.businessDate.atTime(LocalTime.now())).orderInfo(orderInfo)
                                .orderDetail(orderDetail).couponId(unUsedCoupon.getCouponInfo().getId())
                                .couponInfo(unUsedCoupon.getCouponInfo()).couponUsed(unUsedCoupon).build();
                        curOrderDetailCouponList.add(orderDetailCoupon);
                    }
                }
            }
            if (curOrderDetailCouponList.size() > 0) {
                List<OrderDetailCoupon> matchedOrderDetailCouponList = matchRule(orderInfo, curOrderDetailCouponList);
                if (matchedOrderDetailCouponList != null && matchedOrderDetailCouponList.size() > 0) {
                    for (OrderDetailCoupon orderDetailCoupon : matchedOrderDetailCouponList) {
                        CouponUsed couponUsed = orderDetailCoupon.getCouponUsed();
                        couponUsed.setOrderInfo(orderInfo);
                        couponUsed.setCouponStatus(Constant.COUPON_STATUS_USING);
                        couponUsed.setUsingTime(config.businessDate.atTime(LocalTime.now()));
                        couponsUsedForUpdate.add(couponUsed);
                    }
                    orderCount++;
                    orderDetailsCoupon.addAll(matchedOrderDetailCouponList);
                    orderInfo.sumTotalAmount();
                }
            }
        }
        log.warn("共有{}条订单参与活动。", orderCount);
        return new ImmutablePair<>(couponsUsedForUpdate, orderDetailsCoupon);
    }

    @Override
    public void saveCouponUseList(List<CouponUsed> couponUseList) {
        saveBatch(couponUseList, 100);
    }

    // 目的是获得以适用的优惠券为单位的聚类，以便批量参加某个活动的商品是否达到要求
    private Map<Long, OrderCouponSum> genOrderCouponSumMap(List<OrderDetailCoupon> orderDetailCouponList) {
        Map<Long, OrderCouponSum> orderCouponSumMap = new HashMap<>();
        for (OrderDetailCoupon orderDetailCoupon : orderDetailCouponList) {
            OrderCouponSum orderCouponSum = orderCouponSumMap.get(orderDetailCoupon.getCouponId());
            if (orderCouponSum != null) {
                OrderDetail orderDetail = orderDetailCoupon.getOrderDetail();
                BigDecimal orderDetailAmount = orderDetail.getOrderPrice()
                        .multiply(BigDecimal.valueOf(orderDetail.getSkuNumber()));
                orderCouponSum.setOrderDetailAmountSum(orderCouponSum.getOrderDetailAmountSum().add(orderDetailAmount));
                orderCouponSum.setSkuNumSum(orderCouponSum.getSkuNumSum() + orderDetail.getSkuNumber());
                orderCouponSum.getOrderDetailCouponList().add(orderDetailCoupon);
            } else {
                OrderDetail orderDetail = orderDetailCoupon.getOrderDetail();
                BigDecimal orderDetailAmount = orderDetail.getOrderPrice()
                        .multiply(BigDecimal.valueOf(orderDetail.getSkuNumber()));
                OrderCouponSum orderCouponSumNew = new OrderCouponSum(orderDetailCoupon.getCouponId(),
                        orderDetailAmount, orderDetail.getSkuNumber(),
                        new ArrayList<>(Arrays.asList(orderDetailCoupon)), orderDetailCoupon.getCouponInfo(),
                        BigDecimal.ZERO);
                orderCouponSumMap.put(orderDetailCoupon.getCouponId(), orderCouponSumNew);
            }
        }
        return orderCouponSumMap;
    }

    // 取金额优惠最大的且符合要求的优惠券涉及的订单
    private List<OrderDetailCoupon> matchRule(OrderInfo orderInfo, List<OrderDetailCoupon> orderDetailCouponList) {
        Map<Long, OrderCouponSum> orderCouponSumMap = genOrderCouponSumMap(orderDetailCouponList);
        OrderCouponSum maxAmountCouponSum = null;
        for (OrderCouponSum orderCouponSum : orderCouponSumMap.values()) {
            if (orderCouponSum.getCouponInfo().getCouponType().equals(Constant.COUPON_TYPE_MJ)
                    && orderCouponSum.orderDetailAmountSum
                            .compareTo(orderCouponSum.getCouponInfo().getConditionAmount()) >= 0) {
                orderCouponSum.setReduceAmount(orderCouponSum.getCouponInfo().getBenefitAmount());
                if (maxAmountCouponSum == null
                        || orderCouponSum.getReduceAmount().compareTo(maxAmountCouponSum.getReduceAmount()) > 0) {
                    maxAmountCouponSum = orderCouponSum;
                }
            } else if (orderCouponSum.getCouponInfo().getCouponType().equals(Constant.COUPON_TYPE_ML) && orderCouponSum
                    .getSkuNumSum().compareTo(orderCouponSum.getCouponInfo().getConditionNumber()) >= 0) {
                orderCouponSum.setReduceAmount(orderCouponSum.getOrderDetailAmountSum()
                        .multiply(orderCouponSum.getCouponInfo().getBenefitDiscount()));
                if (maxAmountCouponSum == null
                        || orderCouponSum.getReduceAmount().compareTo(maxAmountCouponSum.getReduceAmount()) > 0) {
                    maxAmountCouponSum = orderCouponSum;
                }
            }
        }
        if (maxAmountCouponSum != null) {
            List<OrderDetailCoupon> curOrderDetailCouponList = maxAmountCouponSum.getOrderDetailCouponList();
            if (maxAmountCouponSum.getCouponInfo().getCouponType().equals(Constant.COUPON_TYPE_MJ)) {
                orderInfo.setCouponReduceAmount(maxAmountCouponSum.getReduceAmount());
                BigDecimal splitCouponAmountSum = BigDecimal.ZERO;
                for (int i = 0; i < curOrderDetailCouponList.size(); i++) {
                    OrderDetailCoupon orderDetailCoupon = curOrderDetailCouponList.get(i);
                    if (i < curOrderDetailCouponList.size() - 1) {
                        BigDecimal orderPrice = orderDetailCoupon.getOrderDetail().getOrderPrice();
                        BigDecimal skuNum = BigDecimal.valueOf(orderDetailCoupon.getOrderDetail().getSkuNumber());
                        BigDecimal splitDetailAmount = orderPrice.multiply(skuNum);
                        // 分摊活动金额/活动总金额 = 订单明细金额/活动涉及订单金额
                        //移项 分摊活动金额 = 活动总金额 * 订单明细金额 / 活动涉及订单金额
                        BigDecimal couponReduceAmount = orderInfo.getCouponReduceAmount();
                        BigDecimal splitCouponAmount = couponReduceAmount.multiply(splitDetailAmount)
                                .divide(maxAmountCouponSum.getOrderDetailAmountSum(), 2, RoundingMode.HALF_UP);
                        orderDetailCoupon.getOrderDetail().setSplitCouponAmount(splitCouponAmount);
                        splitCouponAmountSum = splitCouponAmountSum.add(splitCouponAmount);
                    } else {
                        BigDecimal splitCouponAmount = orderInfo.getCouponReduceAmount().subtract(splitCouponAmountSum);
                        orderDetailCoupon.getOrderDetail().setSplitCouponAmount(splitCouponAmount);
                    }
                }

            } else if (maxAmountCouponSum.getCouponInfo().getCouponType().equals(Constant.COUPON_TYPE_ML)) {
                BigDecimal reduceAmount = BigDecimal.ZERO;
                ;
                for (OrderDetailCoupon orderDetailCoupon : curOrderDetailCouponList) {
                    BigDecimal orderPrice = orderDetailCoupon.getOrderDetail().getOrderPrice();
                    BigDecimal skuNum = BigDecimal.valueOf(orderDetailCoupon.getOrderDetail().getSkuNumber());
                    BigDecimal splitDetailAmount = orderPrice.multiply(skuNum);
                    BigDecimal splitCouponAmount = splitDetailAmount.multiply(
                            maxAmountCouponSum.couponInfo.getBenefitDiscount(),
                            new MathContext(2, RoundingMode.HALF_UP));
                    orderDetailCoupon.getOrderDetail().setSplitCouponAmount(splitCouponAmount);
                    reduceAmount.add(splitCouponAmount);
                }
                orderInfo.setCouponReduceAmount(reduceAmount);
            }
            return maxAmountCouponSum.getOrderDetailCouponList();
        }
        return null;
    }

    private boolean matchCouponByRange(OrderDetail orderDetail, CouponUsed couponUsed) {
        List<CouponRange> couponRangeList = couponUsed.getCouponRangeList();
        for (CouponRange couponRange : couponRangeList) {
            if (couponRange.getRangeType().equals(Constant.COUPON_RANGE_TYPE_CATEGORY3)
                    && couponRange.getRangeId().equals(orderDetail.getSkuInfo().getCategory3Id())) {
                return true;
            } else if (couponRange.getRangeType().equals(Constant.COUPON_RANGE_TYPE_TRADEMARK)
                    && couponRange.getRangeId().equals(orderDetail.getSkuInfo().getBrandId())) {
                return true;
            } else if (couponRange.getRangeType().equals(Constant.COUPON_RANGE_TYPE_SPU)
                    && couponRange.getRangeId().equals(orderDetail.getSkuInfo().getSpuId())) {
                return true;
            }
        }
        return false;
    }

    @Data
    @AllArgsConstructor
    class OrderCouponSum {
        private Long couponId = 0L;
        private BigDecimal orderDetailAmountSum = BigDecimal.ZERO;
        private Long skuNumSum = 0L;
        private List<OrderDetailCoupon> orderDetailCouponList = new ArrayList<>();
        private CouponInfo couponInfo;
        private BigDecimal reduceAmount = BigDecimal.ZERO;

    }
}
