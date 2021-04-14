/**
 * @Date        : 2021-04-12 21:41:58
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 购物车信息表，用户登录系统时更新冗余
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
public class CartInfo implements Serializable {
    private static final long serialVersionUID = 7542308585184712559L;

    /** 编号 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 用户id */
    private Long userId;
    /** skuid */
    private Long skuId;
    /** 放入购物车时价格 */
    private BigDecimal cartPrice;
    /** 数量 */
    private Long skuNum;
    /** 图片文件 */
    private String imgUrl;
    /** sku名称，冗余 */
    private String skuName;
    /** 新增时间 */
    private LocalDateTime createTime;
    /** 操作时间 */
    private LocalDateTime operateTime;
    /** 是否下单 */
    private Integer isOrdered;
    /** 下单时间 */
    private LocalDateTime orderTime;
    /** 来源类型 */
    private String sourceType;
    /** 来源 id */
    private Long sourceId;
}
