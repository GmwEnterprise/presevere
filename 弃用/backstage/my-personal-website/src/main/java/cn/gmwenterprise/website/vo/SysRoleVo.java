package cn.gmwenterprise.website.vo;

import lombok.Data;

import java.time.*;

/**
 * SysRole 业务对象
 */
@Data
public class SysRoleVo {
    /**
     * PRIMARY KEY<br>
     * AUTO INCREMENT<br>
     * [id] 主键
     */
    private Integer id;
    /**
     * [role_name] 角色名
     */
    private String roleName;
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
