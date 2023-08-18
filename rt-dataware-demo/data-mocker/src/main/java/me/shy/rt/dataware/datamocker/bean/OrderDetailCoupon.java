/**
 * @Date        : 2021-04-12 22:49:06
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 订单明细与优惠券关联表
 */
package me.shy.rt.dataware.datamocker.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailCoupon implements Serializable {
    private static final long serialVersionUID = -8196718086531105850L;

    /** id */
    private Long id;
    /** 订单 id */
    private Long orderId;
    /** 订单明细 id */
    private Long orderDetailId;
    /** 优惠券 id */
    private Long couponId;
    /** 商品库存 id */
    private Long skuId;
    /** 发生的时间 */
    private LocalDateTime createTime;
    /** 订单明细 */
    @TableField(exist = false)
    private OrderDetail orderDetail;
    /** 订单 */
    @TableField(exist = false)
    private OrderInfo orderInfo;
    /** 优惠券 */
    @TableField(exist = false)
    private CouponInfo couponInfo;
    /** 优惠券领用 */
    @TableField(exist = false)
    private CouponUsed couponUsed;
}
