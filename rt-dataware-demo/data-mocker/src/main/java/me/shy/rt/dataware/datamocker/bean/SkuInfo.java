/**
 * @Date        : 2021-04-12 22:27:26
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 库存单元表
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
public class SkuInfo implements Serializable {
    private static final long serialVersionUID = 2381555242171309588L;

    /** 商品库存 id (itemID) */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 商品 id */
    private Long spuId;
    /** 价格 */
    private BigDecimal price;
    /** 商品库存名称 */
    private String skuName;
    /** 商品规格描述 */
    private String skuDescription;
    /** 重量 */
    private BigDecimal weight;
    /** 品牌 (冗余) */
    private Long brandId;
    /** 三级分类 id（冗余) */
    private Long category3Id;
    /** 默认显示图片 (冗余) */
    private String skuDefaultImg;
    /** 创建时间 */
    private LocalDateTime createTime;
}
