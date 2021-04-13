/**
 * @Date        : 2021-04-12 22:00:33
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 优惠券领用表
 */
package me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponUsed implements Serializable {
    private static final long serialVersionUID = 4850614471780046580L;

    /** 编号 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 优惠券 id */
    private Long couponId;
    /** 用户 id */
    private Long userId;
    /** 订单 id */
    private Long orderId;
    /** 优惠券状态 */
    private String couponStatus;
    /** 领券时间 */
    private LocalDateTime getTime;
    /** 使用时间 */
    private LocalDateTime usingTime;
    /** 支付时间 */
    private LocalDateTime usedTime;
    /** 过期时间 */
    private LocalDateTime expireTime;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 订单 */
    @TableField(exist = false)
    private OrderInfo orderInfo;
    /** 优惠券 */
    @TableField(exist = false)
    private CouponInfo couponInfo;
    /** 优惠券使用范围列表 */
    @TableField(exist = false)
    private List<CouponRange> couponRangeList;

}
