/**
 * @Date        : 2021-04-12 21:26:31
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 优惠规则
 */

package me.shy.rt.dataware.datamocker.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRule implements Serializable {
    private static final long serialVersionUID = 98705653138985586L;

    /** 编号 */
    private Long id;
    /** 活动 id */
    private Long activityId;
    /** 满减金额 */
    private BigDecimal conditionAmount;
    /** 满减件数 */
    private Long conditionNum;
    /** 优惠金额 */
    private BigDecimal benefitAmount;
    /** 优惠折扣 */
    private BigDecimal benefitDiscount;
    /** 优惠级别 */
    private Long benefitLevel;
    /** 活动类型 */
    private String activityType;
}
