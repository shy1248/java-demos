/**
 * @Date        : 2021-04-12 22:59:28
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 支付信息表
 */

package me.shy.rt.dataware.datamocker.bean;

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
public class PaymentInfo implements Serializable {
    private static final long serialVersionUID = 5723616239370543444L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 对外业务编号 */
    private String outTradeNo;
    /** 订单 id */
    private Long orderId;
    /** 用户 id */
    private Long userId;
    /** 第三方交易流水编号 */
    private String tradeNo;
    /** 支付金额 */
    private BigDecimal totalAmount;
    /** 交易内容 */
    private String subject;
    /** 支付方式 */
    private String paymentType;
    /** 发起支付时间 */
    private LocalDateTime createTime;
    /** 第三方支付回调时间 */
    private LocalDateTime callbackTime;
    /** 第三方支付回调信息 */
    private String callbackContent;
}
