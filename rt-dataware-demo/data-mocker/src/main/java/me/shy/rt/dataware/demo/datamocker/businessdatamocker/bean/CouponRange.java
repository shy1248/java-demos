/**
 * @Date        : 2021-04-12 21:57:59
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 优惠券使用范围
 */
package me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponRange implements Serializable {
    private static final long serialVersionUID = 6921713418954223602L;

    /** id */
    private Long id;
    /** 优惠券 id */
    private Long couponId;
    /** 优惠券使用范围的类型 */
    private String rangeType;
    /** 优惠券使用范围的 id */
    private Long rangeId;
}
