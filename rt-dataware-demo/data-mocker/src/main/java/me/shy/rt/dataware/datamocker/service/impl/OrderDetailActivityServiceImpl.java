/**
 * @Date        : 2021-04-13 20:43:56
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 活动与订单关联表 服务实现类
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import me.shy.rt.dataware.datamocker.bean.ActivityRule;
import me.shy.rt.dataware.datamocker.bean.ActivitySku;
import me.shy.rt.dataware.datamocker.bean.OrderDetail;
import me.shy.rt.dataware.datamocker.bean.OrderDetailActivity;
import me.shy.rt.dataware.datamocker.bean.OrderInfo;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.constant.Constant;
import me.shy.rt.dataware.datamocker.mapper.OrderDetailActivityMapper;
import me.shy.rt.dataware.datamocker.service.ActivityRuleService;
import me.shy.rt.dataware.datamocker.service.ActivitySkuService;
import me.shy.rt.dataware.datamocker.service.OrderDetailActivityService;

@Slf4j
@Service
public class OrderDetailActivityServiceImpl extends ServiceImpl<OrderDetailActivityMapper, OrderDetailActivity>
        implements OrderDetailActivityService {

    @Autowired
    DataMockerConfig config;
    @Autowired
    ActivitySkuService activitySkuService;
    @Autowired
    ActivityRuleService activityRuleService;

    @Override
    public List<OrderDetailActivity> genActivityOrder(List<OrderInfo> orderInfos, Boolean isClear) {
        if (isClear) {
            remove(new QueryWrapper<OrderDetailActivity>());
        }

        List<ActivitySku> activitySkus = activitySkuService.list(new QueryWrapper<>());
        List<ActivityRule> activityRules = activityRuleService.list(new QueryWrapper<>());
        List<OrderDetailActivity> ordersJoinActivity = new ArrayList<>();

        int orderCount = 0;
        for (OrderInfo orderInfo : orderInfos) {
            // 检查每个订单里是否有对应的活动商品，如果有随机进行优惠
            List<OrderDetailActivity> orderDetailsActivity = new ArrayList<>();
            List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
            for (OrderDetail orderDetail : orderDetailList) {
                for (ActivitySku activitySku : activitySkus) {
                    if (orderDetail.getSkuId().equals(activitySku.getSkuId())) {
                        orderDetailsActivity.add(
                                OrderDetailActivity.builder().skuId(orderDetail.getSkuId()).orderDetail(orderDetail)
                                        .orderInfo(orderInfo).createTime(config.businessDate.atTime(LocalTime.now()))
                                        .activityId(activitySku.getActivityId()).build());
                    }
                }
            }
            if (orderDetailsActivity.size() > 0) {
                List<OrderDetailActivity> matchedOrderDetailActivitieList = matchRule(orderInfo, ordersJoinActivity,
                        activityRules);
                if (matchedOrderDetailActivitieList.size() > 0) {
                    orderCount++;
                    ordersJoinActivity.addAll(matchedOrderDetailActivitieList);
                    orderInfo.sumTotalAmount();
                }
            }
        }
        log.warn("共有{}条订单参与活动。", orderCount);
        return ordersJoinActivity;
    }

    @Override
    public void saveActivityOrderList(List<OrderDetailActivity> activityOrderList) {
        saveBatch(activityOrderList, 100);
    }

    private List<OrderDetailActivity> matchRule(OrderInfo orderInfo, List<OrderDetailActivity> activityOrderList,
            List<ActivityRule> activityRuleList) {
        List<OrderDetailActivity> matchedActivityOrderList = new ArrayList<>();
        Map<Long, OrderActivitySum> orderActivitySumMap = genOrderActivitySumList(activityOrderList);
        for (OrderActivitySum orderActivitySum : orderActivitySumMap.values()) {
            ActivityRule matchedRule = null;
            for (ActivityRule activityRule : activityRuleList) {
                if (orderActivitySum.getActivityId().equals(activityRule.getActivityId())) {
                    if (matchedRule != null && activityRule.getBenefitLevel() < matchedRule.getBenefitLevel()) {
                        continue;
                    }
                    if (activityRule.getActivityType().equals(Constant.ACTIVITY_RULE_TYPE_MJ) && orderActivitySum
                            .getOrderDetailAmountSum().compareTo(activityRule.getConditionAmount()) >= 0) {
                        matchedRule = activityRule;
                    } else if (activityRule.getActivityType().equals(Constant.ACTIVITY_RULE_TYPE_ML)
                            && orderActivitySum.getSkuNumSum().compareTo(activityRule.getConditionNumber()) >= 0) {
                        matchedRule = activityRule;
                    }
                }
            }
            if (matchedRule != null) {
                List<OrderDetailActivity> orderDetailActivityList = orderActivitySum.getOrderDetailActivityList();
                for (OrderDetailActivity orderDetailActivity : orderDetailActivityList) {
                    orderDetailActivity.setActivityRule(matchedRule);
                    orderDetailActivity.setActivityRuleId(matchedRule.getId());
                    matchedActivityOrderList.add(orderDetailActivity);
                }
                calculateOrderActivityReduceAmount(orderInfo, orderActivitySum, matchedRule);
            }
        }
        return matchedActivityOrderList;
    }

    // 目的是获得以参加的活动为单位的聚类，以便批量参加某个活动的商品是否达到要求
    private Map<Long, OrderActivitySum> genOrderActivitySumList(List<OrderDetailActivity> activityOrderList) {
        Map<Long, OrderActivitySum> orderActivitySumMap = new HashMap<>();
        for (OrderDetailActivity orderDetailActivity : activityOrderList) {
            OrderActivitySum orderActivitySum = orderActivitySumMap.get(orderDetailActivity.getActivityId());
            if (orderActivitySum != null) {
                OrderDetail orderDetail = orderDetailActivity.getOrderDetail();
                BigDecimal orderDetailAmount = orderDetail.getOrderPrice()
                        .multiply(BigDecimal.valueOf(orderDetail.getSkuNumber()));
                orderActivitySum
                        .setOrderDetailAmountSum(orderActivitySum.getOrderDetailAmountSum().add(orderDetailAmount));
                orderActivitySum.setSkuNumSum(orderActivitySum.getSkuNumSum() + orderDetail.getSkuNumber());
                orderActivitySum.getOrderDetailActivityList().add(orderDetailActivity);
            } else {
                OrderDetail orderDetail = orderDetailActivity.getOrderDetail();
                BigDecimal orderDetailAmount = orderDetail.getOrderPrice()
                        .multiply(BigDecimal.valueOf(orderDetail.getSkuNumber()));
                OrderActivitySum orderActivitySumNew = new OrderActivitySum(orderDetailActivity.getActivityId(),
                        orderDetailAmount, orderDetail.getSkuNumber(),
                        new ArrayList<>(Arrays.asList(orderDetailActivity)));
                orderActivitySumMap.put(orderDetailActivity.getActivityId(), orderActivitySumNew);
            }
        }
        return orderActivitySumMap;
    }

    private void calculateOrderActivityReduceAmount(OrderInfo orderInfo, OrderActivitySum orderActivitySum,
            ActivityRule matchedRule) {
        if (matchedRule.getActivityType().equals(Constant.ACTIVITY_RULE_TYPE_MJ)) {
            orderInfo.setActivityReduceAmount(orderInfo.getActivityReduceAmount().add(matchedRule.getBenefitAmount()));

            List<OrderDetailActivity> orderDetailActivityList = orderActivitySum.getOrderDetailActivityList();
            BigDecimal splitActivityAmountSum = BigDecimal.ZERO;
            for (int i = 0; i < orderDetailActivityList.size(); i++) {
                OrderDetailActivity orderDetailActivity = orderDetailActivityList.get(i);
                if (i < orderDetailActivityList.size() - 1) {
                    BigDecimal orderPrice = orderDetailActivity.getOrderDetail().getOrderPrice();
                    BigDecimal skuNum = BigDecimal.valueOf(orderDetailActivity.getOrderDetail().getSkuNumber());
                    BigDecimal splitDetailAmount = orderPrice.multiply(skuNum);
                    // 分摊活动金额/活动总金额 = 订单明细金额/活动涉及订单金额
                    // 移项 分摊活动金额 = 活动总金额 * 订单明细金额 / 活动涉及订单金额
                    BigDecimal activityReduceAmount = orderInfo.getActivityReduceAmount();
                    BigDecimal splitActivityAmount = activityReduceAmount.multiply(splitDetailAmount)
                            .divide(orderActivitySum.getOrderDetailAmountSum(), 2, RoundingMode.HALF_UP);
                    orderDetailActivity.getOrderDetail().setSplitActivityAmount(splitActivityAmount);
                    splitActivityAmountSum = splitActivityAmountSum.add(splitActivityAmount);
                } else {
                    BigDecimal splitActivityAmount = orderInfo.getActivityReduceAmount()
                            .subtract(splitActivityAmountSum);
                    orderDetailActivity.getOrderDetail().setSplitActivityAmount(splitActivityAmount);
                }
            }
        } else if (matchedRule.getActivityType().equals(Constant.ACTIVITY_RULE_TYPE_ML)) {
            BigDecimal reduceAmount = BigDecimal.ZERO;
            List<OrderDetailActivity> orderDetailActivityList = orderActivitySum.getOrderDetailActivityList();
            for (OrderDetailActivity orderDetailActivity : orderDetailActivityList) {
                BigDecimal orderPrice = orderDetailActivity.getOrderDetail().getOrderPrice();
                BigDecimal skuNum = BigDecimal.valueOf(orderDetailActivity.getOrderDetail().getSkuNumber());
                BigDecimal splitDetailAmount = orderPrice.multiply(skuNum);
                BigDecimal splitActivityAmount = splitDetailAmount.multiply(matchedRule.getBenefitDiscount(),
                        new MathContext(2, RoundingMode.HALF_UP));
                orderDetailActivity.getOrderDetail().setSplitActivityAmount(splitActivityAmount);
                reduceAmount.add(splitActivityAmount);
            }
            orderInfo.setActivityReduceAmount(reduceAmount);
        }
    }

    @Data
    @AllArgsConstructor
    class OrderActivitySum {
        Long activityId = 0L;
        BigDecimal orderDetailAmountSum;
        Long skuNumSum = 0L;
        List<OrderDetailActivity> orderDetailActivityList = new ArrayList<>();
    }
}
