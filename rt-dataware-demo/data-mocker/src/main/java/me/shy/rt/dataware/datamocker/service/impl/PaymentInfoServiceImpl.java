/**
 * @Date        : 2021-04-13 21:19:21
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 支付流水表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import me.shy.rt.dataware.datamocker.bean.OrderInfo;
import me.shy.rt.dataware.datamocker.bean.PaymentInfo;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.constant.Constant;
import me.shy.rt.dataware.datamocker.mapper.PaymentInfoMapper;
import me.shy.rt.dataware.datamocker.service.CouponUsedService;
import me.shy.rt.dataware.datamocker.service.OrderInfoService;
import me.shy.rt.dataware.datamocker.service.OrderStatusService;
import me.shy.rt.dataware.datamocker.service.PaymentInfoService;
import me.shy.rt.dataware.datamocker.util.RandomNumeric;
import me.shy.rt.dataware.datamocker.util.RandomWeightOption;
import me.shy.rt.dataware.datamocker.util.WeightOption;

@Slf4j
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    @Autowired
    DataMockerConfig config;
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    OrderStatusService orderStatusService;
    @Autowired
    CouponUsedService couponUsedService;

    @Override
    public void genPayments(Boolean isClear) {
        RandomWeightOption<Boolean> isPaymentOption = new RandomWeightOption<Boolean>(
                new WeightOption<Boolean>(true, config.paymentRate),
                new WeightOption<Boolean>(false, 100 - config.paymentRate));
        RandomWeightOption<String> paymentTypeOption = new RandomWeightOption<String>(
                new WeightOption<String>(Constant.PAYMENT_TYPE_ALIPAY, config.paymentTypeRates[0]),
                new WeightOption<String>(Constant.PAYMENT_TYPE_WECHAT, config.paymentTypeRates[1]),
                new WeightOption<String>(Constant.PAYMENT_TYPE_UNION, config.paymentTypeRates[2]));

        if (isClear) {
            remove(new QueryWrapper<PaymentInfo>());
        }

        QueryWrapper<OrderInfo> unpaidOrderInfos = new QueryWrapper<>();
        unpaidOrderInfos.eq("order_status", Constant.ORDER_STATUS_UNPAID);
        unpaidOrderInfos.orderByAsc("id");
        List<OrderInfo> orderInfoWithDetails = orderInfoService.listWithDetail(unpaidOrderInfos);
        List<PaymentInfo> paymentList = new ArrayList<>();

        if (orderInfoWithDetails.size() == 0) {
            System.out.println("没有需要支付的订单。");
            return;
        }

        for (OrderInfo orderInfo : orderInfoWithDetails) {
            if (isPaymentOption.nextPayload()) {
                PaymentInfo paymentInfo = new PaymentInfo();
                paymentInfo.setOrderId(orderInfo.getId());
                paymentInfo.setTotalAmount(orderInfo.getTotalAmount());
                paymentInfo.setUserId(orderInfo.getUserId());
                paymentInfo.setOutTradeNo(orderInfo.getOutTradeNo());
                paymentInfo.setTradeNo(RandomNumeric.nextString(1, 9, 34, ""));
                paymentInfo.setPaymentType(paymentTypeOption.nextPayload());
                paymentInfo.setSubject(orderInfo.getTradeBody());
                paymentInfo.setCreateTime(config.businessDate.atTime(LocalTime.now()));
                paymentInfo.setCallbackTime(config.businessDate.atTime(LocalTime.now()).plus(20, ChronoUnit.SECONDS));
                paymentList.add(paymentInfo);
                orderInfo.setOrderStatus(Constant.ORDER_STATUS_PAID);
            }
        }

        couponUsedService.usedCoupon(orderInfoWithDetails);
        orderInfoService.updateOrderStatus(orderInfoWithDetails);
        log.warn("共有" + paymentList.size() + "订单完成支付。");
        saveBatch(paymentList, 1000);
    }
}
