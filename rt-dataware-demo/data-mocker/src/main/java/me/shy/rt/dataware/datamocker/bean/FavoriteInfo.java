/**
 * @Date        : 2021-04-12 22:40:51
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 商品收藏表
 */
package me.shy.rt.dataware.datamocker.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteInfo implements Serializable {
    private static final long serialVersionUID = 5941182637916115010L;

    /** 编号 */
    private Long id;
    /** 用户名称 */
    private Long userId;
    /** 商品库存 id */
    private Long skuId;
    /** 商品 id */
    private Long spuId;
    /** 是否已取消，0：正常，1：已取消 */
    private String isCancel;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 修改时间 */
    private LocalDateTime cancelTime;
}
