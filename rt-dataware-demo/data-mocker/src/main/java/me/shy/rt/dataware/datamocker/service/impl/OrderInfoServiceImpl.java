/**
 * @Date        : 2021-04-13 21:14:51
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 订单表 订单表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import me.shy.rt.dataware.datamocker.bean.CartInfo;
import me.shy.rt.dataware.datamocker.bean.CouponUsed;
import me.shy.rt.dataware.datamocker.bean.OrderDetail;
import me.shy.rt.dataware.datamocker.bean.OrderDetailActivity;
import me.shy.rt.dataware.datamocker.bean.OrderDetailCoupon;
import me.shy.rt.dataware.datamocker.bean.OrderInfo;
import me.shy.rt.dataware.datamocker.bean.SkuInfo;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.constant.Constant;
import me.shy.rt.dataware.datamocker.mapper.OrderInfoMapper;
import me.shy.rt.dataware.datamocker.mapper.ProvinceMapper;
import me.shy.rt.dataware.datamocker.service.CartInfoService;
import me.shy.rt.dataware.datamocker.service.CouponUsedService;
import me.shy.rt.dataware.datamocker.service.OrderDetailActivityService;
import me.shy.rt.dataware.datamocker.service.OrderDetailCouponService;
import me.shy.rt.dataware.datamocker.service.OrderDetailService;
import me.shy.rt.dataware.datamocker.service.OrderInfoService;
import me.shy.rt.dataware.datamocker.service.OrderStatusService;
import me.shy.rt.dataware.datamocker.service.SkuInfoService;
import me.shy.rt.dataware.datamocker.util.RandomName;
import me.shy.rt.dataware.datamocker.util.RandomNumeric;
import me.shy.rt.dataware.datamocker.util.RandomWeightOption;

@Slf4j
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    DataMockerConfig config;
    @Autowired
    CartInfoService cartInfoService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    OrderStatusService orderStatusService;
    @Autowired
    SkuInfoService skuInfoService;
    @Autowired
    CouponUsedService couponUsedService;
    @Autowired
    ProvinceMapper provinceMapper;
    @Autowired
    OrderDetailActivityService orderDetailActivityService;
    @Autowired
    OrderDetailCouponService orderDetailCouponService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void genOrderInfos(boolean isClear) {
        if (config.orderUsedCoupon) {
            couponUsedService.genCoupon(isClear);
        }

        if (isClear) {
            remove(new QueryWrapper<>());
            orderDetailService.remove(new QueryWrapper<>());
            orderStatusService.remove(new QueryWrapper<>());
        }

        List<CartInfo> cartInfos = cartInfoService
                .list(new QueryWrapper<CartInfo>().select("distinct user_id").eq("is_ordered", 0));
        Integer provinceTotal = provinceMapper.selectCount(new QueryWrapper<>());

        List<OrderInfo> orderInfos = new ArrayList<>();
        List<Long> cartIdsForUpdate = new ArrayList<>();
        for (CartInfo cartInfo : cartInfos) {
            OrderInfo orderInfo = initOrderInfo(cartInfo.getUserId(), provinceTotal, cartIdsForUpdate);
            if (orderInfo != null && orderInfo.getOrderDetailList() != null
                    && orderInfo.getOrderDetailList().size() > 0) {
                orderInfos.add(orderInfo);
            }
        }
        CartInfo cartInfo = new CartInfo();
        cartInfo.setIsOrdered(1);
        cartInfo.setOrderTime(config.businessDate.atTime(LocalTime.now()));
        cartInfoService.update(cartInfo, new QueryWrapper<CartInfo>().in("id", cartIdsForUpdate));
        log.warn("共生成{}条订单。", orderInfos.size());

        List<OrderDetailActivity> activityOrderList = null;
        if (config.orderJoinActivity) {
            activityOrderList = orderDetailActivityService.genActivityOrder(orderInfos, isClear);
        }

        List<CouponUsed> couponUseds = null;
        List<OrderDetailCoupon> orderDetailCoupons = null;
        if (config.orderUsedCoupon) {
            Pair<List<CouponUsed>, List<OrderDetailCoupon>> couponPair = couponUsedService.usingCoupon(orderInfos);
            couponUseds = couponPair.getLeft();
            orderDetailCoupons = couponPair.getRight();
        }
        saveBatch(orderInfos);

        if (activityOrderList != null && activityOrderList.size() > 0) {
            for (OrderDetailActivity activityOrder : activityOrderList) {
                activityOrder.setOrderId(activityOrder.getOrderInfo().getId());
                activityOrder.setOrderDetailId(activityOrder.getOrderDetail().getId());
            }
            orderDetailActivityService.saveActivityOrderList(activityOrderList);
        }

        if (couponUseds != null && couponUseds.size() > 0) {
            for (CouponUsed couponUse : couponUseds) {
                couponUse.setOrderId(couponUse.getOrderInfo().getId());
            }
            couponUsedService.saveCouponUseList(couponUseds);
        }

        if (orderDetailCoupons != null && orderDetailCoupons.size() > 0) {
            for (OrderDetailCoupon orderDetailCoupon : orderDetailCoupons) {
                orderDetailCoupon.setOrderId(orderDetailCoupon.getOrderInfo().getId());
                orderDetailCoupon.setOrderDetailId(orderDetailCoupon.getOrderDetail().getId());
            }
            orderDetailCouponService.saveBatch(orderDetailCoupons, 100);
        }
        orderStatusService.genOrderStatusLog(orderInfos);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderStatus(List<OrderInfo> orderInfos) {
        if (orderInfos.size() == 0) {
            log.warn("没有需要更新状态的订单。");
            return;
        }

        List<OrderInfo> orderInfosForUpdate = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfos) {
            OrderInfo orderInfoForUpdate = new OrderInfo();
            orderInfoForUpdate.setId(orderInfo.getId());
            orderInfoForUpdate.setOrderStatus(orderInfo.getOrderStatus());
            orderInfoForUpdate.setOperateTime(config.businessDate.atTime(LocalTime.now()));
            orderInfosForUpdate.add(orderInfoForUpdate);
        }
        log.warn("共有{}个订单的状态被更新。", orderInfosForUpdate.size());
        orderStatusService.genOrderStatusLog(orderInfosForUpdate);
        saveOrUpdateBatch(orderInfosForUpdate, 100);
    }

    @Override
    public List<OrderInfo> listWithDetail(Wrapper<OrderInfo> queryWrapper) {
        return listWithDetail(queryWrapper, false);
    }

    @Override
    public List<OrderInfo> listWithDetail(Wrapper<OrderInfo> queryWrapper, Boolean withSkuInfo) {
        List<OrderInfo> orderInfos = super.list(queryWrapper);
        for (OrderInfo orderInfo : orderInfos) {
            List<OrderDetail> orderDetails = orderDetailService
                    .list(new QueryWrapper<OrderDetail>().eq("order_id", orderInfo.getId()));
            if (withSkuInfo) {
                for (OrderDetail orderDetail : orderDetails) {
                    SkuInfo skuInfo = skuInfoService.getById(orderDetail.getSkuId());
                    orderDetail.setSkuInfo(skuInfo);
                }
            }
            orderInfo.setOrderDetailList(orderDetails);
        }
        return orderInfos;
    }

    @Transactional(rollbackFor = Exception.class)
    private boolean saveBatch(List<OrderInfo> orderInfos) {
        super.saveBatch(orderInfos, 100);
        List<OrderDetail> allOrderDetails = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfos) {
            Long orderId = orderInfo.getId();
            List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
            for (OrderDetail orderDetail : orderDetailList) {
                orderDetail.setOrderId(orderId);
                allOrderDetails.add(orderDetail);
            }
        }
        return orderDetailService.saveBatch(allOrderDetails, 100);
    }

    private OrderInfo initOrderInfo(Long userId, Integer provinceTotal, List<Long> cartIdsForUpdate) {
        Boolean isUserOrdered = RandomWeightOption.<Boolean>builder().add(true, config.perUserOrderedRate)
                .add(false, 100 - config.perUserOrderedRate).build().nextPayload();
        Boolean isSkuOrdered = RandomWeightOption.<Boolean>builder().add(true, config.perSkuOrderedRate)
                .add(false, 100 - config.perSkuOrderedRate).build().nextPayload();
        if (!isUserOrdered) {
            return null;
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(userId);
        orderInfo.setConsignee(RandomName.nextFamilyName() + RandomName
                .nextLastName(RandomWeightOption.<String>builder().add("M", 50).add("F", 50).build().nextPayload()));
        orderInfo.setConsigneeTel("13" + RandomNumeric.nextString(0, 9, 9, ""));
        orderInfo.setDeliveryAddress("第" + RandomNumeric.nextInteger(0, 20) + "大街第" + RandomNumeric.nextInteger(1, 40)
                + "号楼" + RandomNumeric.nextInteger(1, 9) + "单元" + RandomNumeric.nextString(1, 9, 3, "") + "号");
        orderInfo.setExpireTime(config.businessDate.atTime(LocalTime.now()).minus(15, ChronoUnit.MINUTES));
        orderInfo.setImgUrl("http://rt.shy.me/" + RandomNumeric.nextString(1, 9, 6, "") + ".jpg");
        orderInfo.setOrderStatus(Constant.ORDER_STATUS_UNPAID);
        orderInfo.setOrderComment("描述" + RandomNumeric.nextString(1, 9, 6, ""));
        orderInfo.setOutTradeNo(RandomNumeric.nextString(1, 9, 15, ""));
        orderInfo.setFeightFee(BigDecimal.valueOf(RandomNumeric.nextInteger(5, 20)));
        orderInfo.setProvinceId(RandomNumeric.nextInteger(1, provinceTotal));

        List<CartInfo> cartInfosOfUser = cartInfoService.list(new QueryWrapper<CartInfo>().eq("user_id", userId));
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartInfo cartInfo : cartInfosOfUser) {
            if (isSkuOrdered) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setImgUrl(cartInfo.getImgUrl());
                orderDetail.setSkuNumber(cartInfo.getSkuNumber());
                orderDetail.setSkuName(cartInfo.getSkuName());
                orderDetail.setSkuId(cartInfo.getSkuId());
                orderDetail.setOrderPrice(cartInfo.getCartPrice());
                orderDetail.setCreateTime(config.businessDate.atTime(LocalTime.now()));
                orderDetail.setSourceId(cartInfo.getSourceId());
                orderDetail.setSourceType(cartInfo.getSourceType());
                orderDetails.add(orderDetail);
                cartIdsForUpdate.add(cartInfo.getId());
            }
        }

        orderInfo.setOrderDetailList(orderDetails);
        orderInfo.sumTotalAmount();
        orderInfo.setTradeBody(orderInfo.getOrderSubject());
        return orderInfo;
    }
}
