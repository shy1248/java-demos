/**
 * @Date        : 2021-04-12 21:53:26
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 优惠券表
 */
package me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponInfo implements Serializable {
    private static final long serialVersionUID = 5897365513384135112L;

    /** 购物券编号 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 购物券名称 */
    private String couponName;
    /** 购物券类型，1：现金券，2：折扣券，3：满减券，4：满件打折券 */
    private String couponType;
    /** 满额数 */
    private BigDecimal conditionAmount;
    /** 满件数 */
    private Long conditionNum;
    /** 活动编号 */
    private Long activityId;
    /** 减金额 */
    private BigDecimal benefitAmount;
    /** 折扣 */
    private BigDecimal benefitDiscount;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 范围类型，1：商品，2：品类，3：品牌 */
    private String rangeType;
    /** 最多领用次数 */
    private Integer limitNum;
    /** 修改时间 */
    private LocalDateTime operateTime;
    /** 过期时间 */
    private LocalDateTime expireTime;
}
