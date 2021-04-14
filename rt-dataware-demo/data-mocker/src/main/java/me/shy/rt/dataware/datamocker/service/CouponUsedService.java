/**
 * @Date        : 2021-04-12 23:07:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 优惠券领用表 服务类
 */

package me.shy.rt.dataware.datamocker.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.lang3.tuple.Pair;

import me.shy.rt.dataware.datamocker.bean.CouponUsed;
import me.shy.rt.dataware.datamocker.bean.OrderDetailCoupon;
import me.shy.rt.dataware.datamocker.bean.OrderInfo;

public interface CouponUsedService extends IService<CouponUsed> {
    public void genCoupon(Boolean isClear);
    public  void  usedCoupon(List<OrderInfo> orderInfoList);
    public Pair<List<CouponUsed>,List<OrderDetailCoupon>> usingCoupon(List<OrderInfo> orderInfoList);
    public  void  saveCouponUseList( List<CouponUsed> couponUseList);


}
