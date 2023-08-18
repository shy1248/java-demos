/**
 * @Date        : 2021-04-13 21:17:55
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 订单状态服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.shy.rt.dataware.datamocker.bean.OrderInfo;
import me.shy.rt.dataware.datamocker.bean.OrderStatus;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.mapper.OrderStatusMapper;
import me.shy.rt.dataware.datamocker.service.OrderStatusService;

@Service
public class OrderStatusServiceImpl extends ServiceImpl<OrderStatusMapper, OrderStatus> implements OrderStatusService {

    @Autowired
    DataMockerConfig config;

    @Override
    public void genOrderStatusLog(List<OrderInfo> orderInfoList) {
        List<OrderStatus> ordersStatus = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfoList) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setOperateTime(config.businessDate.atTime(LocalTime.now()));
            orderStatus.setOrderStatus(orderInfo.getOrderStatus());
            orderStatus.setOrderId(orderInfo.getId());
            ordersStatus.add(orderStatus);
        }
        saveBatch(ordersStatus, 1000);
    }
}
