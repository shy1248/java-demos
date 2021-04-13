/**
 * @Date        : 2021-04-13 21:13:34
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 订单明细表 服务实现类
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean.OrderDetail;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.mapper.OrderDetailMapper;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.OrderDetailService;

public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
