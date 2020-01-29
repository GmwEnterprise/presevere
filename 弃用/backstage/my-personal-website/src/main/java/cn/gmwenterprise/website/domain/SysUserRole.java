package cn.gmwenterprise.website.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.*;

/**
 * sys_user_role 
 */
@Data
@Alias("sysUserRole")
public class SysUserRole {
    /**
     * PRIMARY KEY<br>
     * AUTO INCREMENT<br>
     * [id] 主键
     */
    private Integer id;
    /**
     * [user_id] 关联用户
     */
    private Integer userId;
    /**
     * [role_id] 关联角色
     */
    private Integer roleId;
    /**
     * [create_datetime] 创建时间
     */
    private LocalDateTime createDatetime;
    /**
     * [update_datetime] 更新时间
     */
    private LocalDateTime updateDatetime;
    /**
     * [available] 是否可用
     */
    private Integer available;
}
