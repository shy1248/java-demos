/**
// @Date        : 2021-04-12 21:46:59
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 商品评论表
 */
package me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfo implements Serializable {
    private static final long serialVersionUID = 3899584192276486533L;

    /** 编号 */
    private Long id;
    /** 用户名称 */
    private Long userId;
    /** 商品库存 id */
    private Long skuId;
    /** 商品 id */
    private Long spuId;
    /** 订单编号 */
    private Long orderId;
    /** 评价，1：好评，2：中评，3：差评 */
    private String grade;
    /** 评价内容 */
    private String commentText;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 修改时间 */
    private LocalDateTime operateTime;
}
