/**
 * @Date        : 2021-04-12 23:07:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 用户信息表
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
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -4446550240437158500L;
    /** 编号 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 用户名称 */
    private String loginName;
    /** 用户昵称 */
    private String nickName;
    /** 用户密码 */
    private String passwd;
    /** 用户姓名 */
    private String name;
    /** 手机号 */
    private String phoneNumber;
    /** 邮箱 */
    private String email;
    /** 头像 */
    private String headImage;
    /** 用户级别 */
    private String userLevel;
    /** 用户生日 */
    private LocalDateTime birthday;
    /** 性别 M男,F女 */
    private String gender;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 修改时间 */
    private LocalDateTime operateTime;
}
