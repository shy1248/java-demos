/**
 * @Date        : 2021-04-12 23:07:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 订单状态 服务类
 */

package me.shy.rt.dataware.datamocker.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import me.shy.rt.dataware.datamocker.bean.OrderInfo;
import me.shy.rt.dataware.datamocker.bean.OrderStatus;

public interface OrderStatusService extends IService<OrderStatus> {
    public void  genOrderStatusLog(List<OrderInfo> orderInfoList);
}
