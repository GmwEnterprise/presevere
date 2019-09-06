package cn.gmwenterprise.website.config.mybatis;

import lombok.Data;

@Data
class PageInfo {
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 页长
     */
    private Integer pageSize;
}
