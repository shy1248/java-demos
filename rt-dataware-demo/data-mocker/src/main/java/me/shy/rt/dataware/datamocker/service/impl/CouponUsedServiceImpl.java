/**
 * @Date        : 2021-04-13 21:05:15
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 优惠券领用表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.lang3.tuple.Pair;

import me.shy.rt.dataware.datamocker.bean.CouponUsed;
import me.shy.rt.dataware.datamocker.bean.OrderDetailCoupon;
import me.shy.rt.dataware.datamocker.bean.OrderInfo;
import me.shy.rt.dataware.datamocker.mapper.CouponUsedMapper;
import me.shy.rt.dataware.datamocker.service.CouponUsedService;

public class CouponUsedServiceImpl extends ServiceImpl<CouponUsedMapper, CouponUsed> implements CouponUsedService {

    @Override
    public void genCoupon(Boolean isClear) {
    }

    @Override
    public void usedCoupon(List<OrderInfo> orderInfoList) {

    }

    @Override
    public Pair<List<CouponUsed>, List<OrderDetailCoupon>> usingCoupon(List<OrderInfo> orderInfoList) {
        return null;
    }

    @Override
    public void saveCouponUseList(List<CouponUsed> couponUseList) {

    }
}
