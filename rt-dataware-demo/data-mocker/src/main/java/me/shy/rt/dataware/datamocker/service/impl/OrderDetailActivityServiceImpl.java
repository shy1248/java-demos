/**
 * @Date        : 2021-04-13 20:43:56
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 活动与订单关联表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.shy.rt.dataware.datamocker.bean.OrderDetailActivity;
import me.shy.rt.dataware.datamocker.bean.OrderInfo;
import me.shy.rt.dataware.datamocker.mapper.OrderDetailActivityMapper;
import me.shy.rt.dataware.datamocker.service.OrderDetailActivityService;

public class OrderDetailActivityServiceImpl extends ServiceImpl<OrderDetailActivityMapper, OrderDetailActivity>
        implements OrderDetailActivityService {

    @Override
    public List<OrderDetailActivity> genActivityOrder(List<OrderInfo> orderInfoList, Boolean isClear) {
        return null;
    }

    @Override
    public void saveActivityOrderList(List<OrderDetailActivity> activityOrderList) {

    }
}
