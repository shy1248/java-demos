/**
 * @Date        : 2021-04-12 22:57:25
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 订单状态表
 */

package me.shy.rt.dataware.datamocker.bean;

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
public class OrderStatus implements Serializable {
    private static final long serialVersionUID = 3431182121474772764L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 订单 id */
    private Long orderId;
    /** 订单状态 */
    private String orderStatus;
    /** 操作时间 */
    private LocalDateTime operateTime;
}
