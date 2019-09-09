package cn.gmwenterprise.website.vo;

import cn.gmwenterprise.website.constants.Sex;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * SysUser 业务对象
 */
@Data
public class SysUserVo {
    /**
     * PRIMARY KEY<br>
     * AUTO INCREMENT<br>
     * [id] 主键
     */
    private Integer id;
    /**
     * [user_name] 用户名
     */
    private String userName;
    /**
     * [salt] 盐
     */
    private String salt;
    /**
     * [pwd] 密码
     */
    private String pwd;
    /**
     * [available] 是否可用
     */
    private Boolean available;
    /**
     * [sex] 性别
     */
    private Sex sex;
    /**
     * [phone] 手机号
     */
    private String phone;
    /**
     * [email] 邮箱
     */
    private String email;
    /**
     * [create_datetime] 创建时间
     */
    private LocalDateTime createDatetime;
    /**
     * [update_datetime] 更新时间
     */
    private LocalDateTime updateDatetime;

    /**
     * 当前页（入参）
     */
    private Integer currentPage;
    /**
     * 每页条数（入参）
     */
    private Integer pageSize;
}
