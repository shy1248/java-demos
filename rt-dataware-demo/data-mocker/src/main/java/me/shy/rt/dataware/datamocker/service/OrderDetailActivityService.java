/**
 * @Date        : 2021-04-12 23:07:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 活动与订单关联表 服务类
 */

package me.shy.rt.dataware.datamocker.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import me.shy.rt.dataware.datamocker.bean.OrderDetailActivity;
import me.shy.rt.dataware.datamocker.bean.OrderInfo;

public interface OrderDetailActivityService extends IService<OrderDetailActivity> {
    public List<OrderDetailActivity>  genActivityOrder(List<OrderInfo> orderInfoList, Boolean isClear);
    public  void  saveActivityOrderList( List<OrderDetailActivity> activityOrderList);
}
