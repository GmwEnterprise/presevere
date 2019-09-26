package cn.gmwenterprise.website.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.*;

/**
 * sys_user 
 */
@Data
@Alias("sysUser")
public class SysUser {
    /**
     * PRIMARY KEY<br>
     * AUTO INCREMENT<br>
     * [id] 主键
     */
    private Integer id;
    /**
     * [nickname] 昵称
     */
    private String nickname;
    /**
     * [username] 用户名
     */
    private String username;
    /**
     * [password] 密码
     */
    private String password;
    /**
     * [available] 是否可用
     */
    private Boolean available;
    /**
     * [sex] 性别
     */
    private Integer sex;
    /**
     * [phone] 手机号
     */
    private String phone;
    /**
     * [email] 邮箱
     */
    private String email;
    /**
     * [create_time] 创建时间
     */
    private LocalDateTime createTime;
    /**
     * [update_time] 更新时间
     */
    private LocalDateTime updateTime;
}
