/**
 * @Date        : 2021-04-12 22:18:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 订单明细表
 */

package me.shy.rt.dataware.datamocker.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 5005617082420641235L;

    /** 编号 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 订单编号 */
    private Long orderId;
    /** sku_id */
    private Long skuId;
    /** sku名称（冗余) */
    private String skuName;
    /** 图片名称（冗余) */
    private String imgUrl;
    /** 购买价格(下单时sku价格） */
    private BigDecimal orderPrice;
    /** 购买个数 */
    private Long skuNum;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 分摊的总金额 */
    private BigDecimal splitTotalAmount;
    /** 分摊的优惠券减免金额 */
    private BigDecimal splitCouponAmount;
    /** 分摊的活动减免金额 */
    private BigDecimal splitActivityAmount;
    /** 商品库存信息 */
    @TableField(exist = false)
    private SkuInfo skuInfo;
    /** 订单来源类型 */
    private String sourceType;
    /** 订单来源 id */
    private Long sourceId;
}
