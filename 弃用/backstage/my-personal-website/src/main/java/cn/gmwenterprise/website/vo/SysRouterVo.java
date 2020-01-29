package cn.gmwenterprise.website.vo;

import lombok.Data;

import java.time.*;

/**
 * SysRouter 业务对象
 */
@Data
public class SysRouterVo {
    /**
     * PRIMARY KEY<br>
     * AUTO INCREMENT<br>
     * [id] 主键
     */
    private Integer id;
    /**
     * [router_title] 路由标题
     */
    private String routerTitle;
    /**
     * [router_name] 路由名称
     */
    private String routerName;
    /**
     * [create_datetime] 创建时间
     */
    private LocalDateTime createDatetime;
    /**
     * [last_update_datetime] 最后更新时间
     */
    private LocalDateTime lastUpdateDatetime;

    /**
     * 当前页（入参）
     */
    private Integer currentPage;
    /**
     * 每页条数（入参）
     */
    private Integer pageSize;
}
