/**
 * @Date        : 2021-04-13 21:17:55
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 订单状态服务实现类
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean.OrderInfo;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean.OrderStatus;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.mapper.OrderStatusMapper;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.OrderStatusService;

public class OrderStatusServiceImpl extends ServiceImpl<OrderStatusMapper, OrderStatus> implements OrderStatusService {

    @Override
    public void genOrderStatusLog(List<OrderInfo> orderInfoList) {

    }
}
