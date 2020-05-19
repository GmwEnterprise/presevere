package com.example.pmq.base;

import java.io.Serializable;
import java.util.List;

public class Page<E extends Serializable> {
    private List<E> data;

    /**
     * 是否有数据
     */
    private Boolean hasData;
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 总共多少页
     */
    private int totalPageSize;
    /**
     * 是否有下一页；若没有，则当前页为最后一页
     */
    private Boolean hasNext;
    /**
     * 是否有上一页；若没有，则当前页为第一页
     */
    private Boolean hasPrev;

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public Boolean getHasData() {
        return hasData;
    }

    public void setHasData(Boolean hasData) {
        this.hasData = hasData;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPageSize() {
        return totalPageSize;
    }

    public void setTotalPageSize(int totalPageSize) {
        this.totalPageSize = totalPageSize;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Boolean getHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(Boolean hasPrev) {
        this.hasPrev = hasPrev;
    }
}
