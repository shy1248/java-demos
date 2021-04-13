/**
 * @Date        : 2021-04-13 21:16:37
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 退单表 服务实现类
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean.OrderBack;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.mapper.OrderBackMapper;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.OrderBackService;

public class OrderBackServiceImpl extends ServiceImpl<OrderBackMapper, OrderBack> implements OrderBackService {

    @Override
    public void genRefundsOrFinish(Boolean isClear) {
    }
}
