/**
 * @Date        : 2021-04-13 21:19:21
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 支付流水表 服务实现类
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean.PaymentInfo;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.mapper.PaymentInfoMapper;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.PaymentInfoService;

public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    @Override
    public void genPayments(Boolean isClear) {
    }
}
