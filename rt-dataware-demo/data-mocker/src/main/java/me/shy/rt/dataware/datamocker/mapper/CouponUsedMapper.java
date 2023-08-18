/**
 * @Date        : 2021-04-13 20:27:11
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 优惠券领用表 Mapper 接口
 */

package me.shy.rt.dataware.datamocker.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.*;

import me.shy.rt.dataware.datamocker.bean.CouponUsed;
import me.shy.rt.dataware.datamocker.constant.Constant;

@Mapper
public interface CouponUsedMapper extends BaseMapper<CouponUsed> {
    @Select("select * from coupon_used where coupon_status='" + Constant.COUPON_STATUS_UNUSED + "'")
    @Results(id = "couponMap", value = { @Result(id = true, column = "id", property = "id"),
            @Result(property = "couponId", column = "coupon_id"), @Result(property = "orderId", column = "order_id"),
            @Result(property = "couponStatus", column = "coupon_status"),
            @Result(property = "getTime", column = "get_time"), @Result(property = "usingTime", column = "expire_time"),
            @Result(property = "usedTime", column = "userd_time"),
            @Result(property = "expireTime", column = "expire_time"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "couponInfo", column = "coupon_id", one = @One(select = "me.shy.rt.dataware.datamocker.mapper.CouponInfoMapper.selectById")),
            @Result(property = "couponRangeList", column = "coupon_id", many = @Many(select = "me.shy.rt.dataware.datamocker.mapper.CouponRangeMapper.selectById")) })
    public List<CouponUsed> selectUnusedCouponUseListWithInfo();
}
