package cn.gmwenterprise.website.config.mybatis;

import lombok.Data;

import java.util.List;

@Data
public class Page<E> {
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 每页数据条数
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private Integer total;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 是否有下一页
     */
    private Boolean hasNextPage;
    /**
     * 是否有上一页
     */
    private Boolean hasPrevPage;
    /**
     * 本页数据
     */
    private List<E> list;
}
