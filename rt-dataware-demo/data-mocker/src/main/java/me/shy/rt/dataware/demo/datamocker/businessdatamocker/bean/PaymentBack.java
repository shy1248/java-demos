/**
 * @Date        : 2021-04-12 23:02:48
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 退款信息表
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
public class PaymentBack implements Serializable {
    private static final long serialVersionUID = 4553372395109685918L;
    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 对外业务编号 */
    private String outTradeNo;
    /** 订单 id */
    private Long orderId;
    /** 商品库存 id */
    private Long skuId;
    /** 支付方式 */
    private String paymentType;
    /** 总金额 */
    private BigDecimal totalAmount;
    /** 交易内容 */
    private String subject;
    /** 退款状态 */
    private String backStatus;
    /** 发起退款时间 */
    private LocalDateTime createTime;
    /** 第三方支付回调时间 */
    private LocalDateTime callbackTime;
    /** 第三方支付回调信息 */
    private String callbackContent;
}
