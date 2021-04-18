/**
 * @Date        : 2021-04-12 22:43:55
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 活动与订单关联表
 */
package me.shy.rt.dataware.datamocker.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailActivity implements Serializable {
    private static final long serialVersionUID = -423472970828299935L;

    /** 编号 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 订单明细 id */
    private Long orderDetailId;
    /** 活动规则 id */
    private Long activityRuleId;
    /** 商品库存 id */
    private Long skuId;
    /** 活动id */
    private Long activityId;
    /** 订单 id */
    private Long orderId;
    /** 订单明细 */
    @TableField(exist = false)
    private OrderDetail orderDetail;
    /** 订单 */
    @TableField(exist = false)
    private OrderInfo orderInfo;
    /** 活动规则 */
    @TableField(exist = false)
    private ActivityRule activityRule;
    /** 发生日期 */
    private LocalDateTime createTime;
}
