package com.piedra.platease.model;

import java.util.List;

import org.hibernate.criterion.MatchMode;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 分页对象
 * @param <T>   分页对象
 * @since 2017-04-05 by webinglin
 */
public class Page<T> {

    public enum OrderType {
        asc, desc
    }

    /** 分页数据 */
    private List<T> list;

    /** 分页大小 */
    private Integer pageSize = 10;

    /** 分页页码 */
    private Integer pageIndex = 1;

    /** 总条数 */
    private Integer totalCount = 0;

    /** 总页数 */
    private Integer totalPageSize = 0;

    /** 排序字段 */
    @JSONField(serialize = false)
    private String orderBy;

    /** 排序方式 */
    @JSONField(serialize = false)
    private OrderType orderType = OrderType.asc;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPageSize() {
        this.totalPageSize = this.totalCount / this.pageSize;
        return this.totalCount % this.pageSize > 0 ? ++this.totalPageSize : this.totalPageSize;
    }

    public void setTotalPageSize(Integer totalPageSize) {
        this.totalPageSize = totalPageSize;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}
