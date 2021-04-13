/**
 * @Date        : 2021-04-12 21:31:05
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 参与活动的库存商品
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySku implements Serializable {
    private static final long serialVersionUID = -3264880735253116276L;

    /** 编号 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 活动id */
    private Long activityId;
    /** 库存ID */
    private Long skuId;
    /** 创建时间 */
    private LocalDateTime createTime;
}
