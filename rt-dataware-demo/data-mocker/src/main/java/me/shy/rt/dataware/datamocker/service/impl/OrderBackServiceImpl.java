/**
 * @Date        : 2021-04-13 21:16:37
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 退单表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import me.shy.rt.dataware.datamocker.bean.OrderBackInfo;
import me.shy.rt.dataware.datamocker.bean.OrderDetail;
import me.shy.rt.dataware.datamocker.bean.OrderInfo;
import me.shy.rt.dataware.datamocker.bean.PaymentBack;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.constant.Constant;
import me.shy.rt.dataware.datamocker.mapper.OrderBackMapper;
import me.shy.rt.dataware.datamocker.service.OrderBackService;
import me.shy.rt.dataware.datamocker.service.OrderInfoService;
import me.shy.rt.dataware.datamocker.service.PaymentBackService;
import me.shy.rt.dataware.datamocker.util.RandomNumeric;
import me.shy.rt.dataware.datamocker.util.RandomWeightOption;

@Slf4j
@Service
public class OrderBackServiceImpl extends ServiceImpl<OrderBackMapper, OrderBackInfo> implements OrderBackService {

    @Autowired
    DataMockerConfig config;
    @Autowired
    PaymentBackService paymentBackService;
    @Autowired
    OrderInfoService orderInfoService;

    @Override
    public void genRefundsOrFinish(Boolean isClear) {
        if (isClear) {
            remove(new QueryWrapper<OrderBackInfo>());
            paymentBackService.remove(new QueryWrapper<PaymentBack>());
        }

        RandomWeightOption<Boolean> orderBackOption = RandomWeightOption.<Boolean>builder()
                .add(true, config.orderBackRate).add(false, 100 - config.orderBackRate).build();
        RandomWeightOption<String> orderBackTypeOption = RandomWeightOption.<String>builder()
                .add(Constant.BACK_TYPE_ONLY_MONEY, 30).add(Constant.BACK_TYPE_WITH_GOODS, 70).build();
        RandomWeightOption<String> orderBackReasonOption = RandomWeightOption.<String>builder()
                .add(Constant.BACK_REASON_BAD_GOODS, config.orderBackReasonRates[0])
                .add(Constant.BACK_REASON_WRONG_DESC, config.orderBackReasonRates[1])
                .add(Constant.BACK_REASON_SALE_OUT, config.orderBackReasonRates[2])
                .add(Constant.BACK_REASON_SIZE_ISSUE, config.orderBackReasonRates[3])
                .add(Constant.BACK_REASON_MISTAKE, config.orderBackReasonRates[4])
                .add(Constant.BACK_REASON_NO_REASON, config.orderBackReasonRates[5])
                .add(Constant.BACK_REASON_OTHER, config.orderBackReasonRates[6]).build();

        List<OrderInfo> orderInfos = orderInfoService.listWithDetail(new QueryWrapper<OrderInfo>()
                .in("order_status", Constant.ORDER_STATUS_PAID, Constant.ORDER_STATUS_FINISH).orderByAsc("id"));
        List<OrderBackInfo> orderBackInfos = new ArrayList<>();
        List<PaymentBack> paymentBacks = new ArrayList<>();
        List<OrderInfo> orderInfosForUpdate = new ArrayList<>();

        if (orderInfos.size() == 0) {
            log.warn("没有需要退款或完结的订单。");
            return;
        }

        for (OrderInfo orderInfo : orderInfos) {
            if (orderBackOption.nextPayload()) {
                OrderBackInfo orderBackInfo = new OrderBackInfo();
                orderBackInfo.setOrderId(orderInfo.getId());
                OrderDetail orderDetail = orderInfo.getOrderDetailList().get(0);
                orderBackInfo.setBackAmount(
                        orderDetail.getOrderPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNumber())));
                orderBackInfo.setSkuId(orderDetail.getSkuId());
                orderBackInfo.setUserId(orderInfo.getUserId());
                orderBackInfo.setBackNumber(orderDetail.getSkuNumber());
                orderBackInfo.setCreateTime(config.businessDate.atTime(LocalTime.now()));
                orderBackInfo.setBackReasonText("退款原因具体：" + RandomNumeric.nextString(0, 9, 10, ""));
                orderBackInfo.setBackType(orderBackTypeOption.nextPayload());
                orderBackInfo.setBackReasonType(orderBackReasonOption.nextPayload());
                orderBackInfos.add(orderBackInfo);

                PaymentBack paymentBack = new PaymentBack();
                paymentBack.setOrderId(orderInfo.getId());
                paymentBack.setSkuId(orderBackInfo.getSkuId());
                paymentBack.setPaymentType(Constant.PAYMENT_TYPE_ALIPAY);
                paymentBack.setBackStatus(Constant.BACK_STATUS_APPROVED);
                paymentBack.setTotalAmount(orderBackInfo.getBackAmount());
                paymentBack.setCreateTime(config.businessDate.atTime(LocalTime.now()));
                paymentBack.setCallbackTime(config.businessDate.atTime(LocalTime.now()).plus(15, ChronoUnit.SECONDS));
                paymentBack.setOutTradeNo(RandomNumeric.nextString(1, 9, 15, ""));
                paymentBack.setSubject("退款");
                paymentBacks.add(paymentBack);
                orderInfo.setOrderStatus(Constant.ORDER_STATUS_BACK);
                orderInfosForUpdate.add(orderInfo);
            } else {
                if (orderInfo.getOrderStatus().equals(Constant.ORDER_STATUS_PAID)) {
                    orderInfo.setOrderStatus(Constant.ORDER_STATUS_FINISH);
                    orderInfosForUpdate.add(orderInfo);
                }
            }
        }
        orderInfoService.updateOrderStatus(orderInfosForUpdate);
        saveBatch(orderBackInfos, 1000);
        log.warn("共生成{}条退款订单。", orderBackInfos.size());
        paymentBackService.saveBatch(paymentBacks);
        log.warn("共生成{}条退款支付明细。", paymentBacks.size());
    }
}
