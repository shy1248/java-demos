/**
 * @Date        : 2021-04-12 21:16:26
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 活动表
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
public class ActivityInfo implements Serializable {
    private static final long serialVersionUID = -6020233747392900028L;

    /** 活动 id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 活动名称 */
    private String activityName;
     /** 活动类型 */
    private String activityType;
    /** 活动描述 */
    private String activityDescription;
    /** 开始时间 */
    private LocalDateTime startTime;
    /** 结束时间 */
    private LocalDateTime endTime;
    /** 创建时间 */
    private LocalDateTime createTime;
}
