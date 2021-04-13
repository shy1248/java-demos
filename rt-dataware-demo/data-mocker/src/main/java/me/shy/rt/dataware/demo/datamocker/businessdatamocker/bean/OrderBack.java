/**
 * @Date        : 2021-04-12 22:53:24
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 退单表
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
public class OrderBack implements Serializable {
    private static final long serialVersionUID = 5910501547106970685L;

    /** 编号 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 用户 id */
    private Long userId;
    /** 订单编号 */
    private Long orderId;
    /** 商品库存 id */
    private Long skuId;
    /** 退款类型 */
    private String backType;
    /** 退款件数 */
    private Long backNumber;
    /** 退款金额 */
    private BigDecimal backAmount;
    /** 原因类型 */
    private String backReasonType;
    /** 原因内容描述 */
    private String backReasonText;
    /** 创建时间 */
    private LocalDateTime createTime;
}
