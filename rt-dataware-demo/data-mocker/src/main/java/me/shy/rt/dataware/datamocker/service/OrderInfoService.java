/**
 * @Date        : 2021-04-12 23:07:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 订单表 服务类
 */

package me.shy.rt.dataware.datamocker.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import me.shy.rt.dataware.datamocker.bean.OrderInfo;

public interface OrderInfoService extends IService<OrderInfo> {
    public void genOrderInfos(boolean isClear);
    public void updateOrderStatus(List<OrderInfo> orderInfoList);
    public List<OrderInfo> listWithDetail(Wrapper<OrderInfo> queryWrapper);
    public List<OrderInfo> listWithDetail(Wrapper<OrderInfo> queryWrapper, Boolean withSkuInfo);


}
