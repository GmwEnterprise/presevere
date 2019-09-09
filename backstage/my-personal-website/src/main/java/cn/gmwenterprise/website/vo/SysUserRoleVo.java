package cn.gmwenterprise.website.vo;

import lombok.Data;

import java.time.*;

/**
 * SysUserRole 业务对象
 */
@Data
public class SysUserRoleVo {
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
     * 当前页（入参）
     */
    private Integer currentPage;
    /**
     * 每页条数（入参）
     */
    private Integer pageSize;
}
