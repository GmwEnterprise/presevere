package cn.gmwenterprise.website.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.*;

/**
 * sys_router 
 */
@Data
@Alias("sysRouter")
public class SysRouter {
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
}
