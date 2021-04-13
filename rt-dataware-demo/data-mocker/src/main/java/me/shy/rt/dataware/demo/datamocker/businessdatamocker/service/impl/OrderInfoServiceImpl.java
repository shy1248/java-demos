/**
 * @Date        : 2021-04-13 21:14:51
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 订单表 订单表 服务实现类
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean.OrderInfo;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.mapper.OrderInfoMapper;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.OrderInfoService;

public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Override
    public void genOrderInfos(boolean isClear) {
    }

    @Override
    public void updateOrderStatus(List<OrderInfo> orderInfoList) {
    }

    @Override
    public List<OrderInfo> listWithDetail(Wrapper<OrderInfo> queryWrapper) {
        return null;
    }

    @Override
    public List<OrderInfo> listWithDetail(Wrapper<OrderInfo> queryWrapper, Boolean withSkuInfo) {
        return null;
    }
}
