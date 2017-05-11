package com.piedra.platease.model;

import com.piedra.platease.constants.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 * @param <M>   分页对象
 * @since 2017-04-05 by webinglin
 */
public class Page<M> {
    private Class<M> entityClass ;


    /** 分页数据 */
    private List<M> datas = new ArrayList<>();
    /** 总页数 */
    private Integer total = 0;
    /** 总条数 */
    private Integer records = 0;


    /* * 前端请求参数 ***/

    /** 当前第几页 */
    private int page = 1;
    /** 每页几条记录 */
    private int rows = 10;
    /** 排序字段 */
    private String sidx;
    /** 排序顺序 */
    private String sord = Constants.Order.ASC;


    public Page(){
    }

    public Page(int page, int rows){
        this.page = page;
        this.rows = rows;
    }

    public List<M> getDatas() {
        return datas;
    }

    public void setDatas(List<M> datas) {
        this.datas = datas;
    }

    public Integer getTotal() {
        this.total = this.records%this.rows==0 ? (this.records/this.rows) : (this.records/this.rows+1);
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(Integer records) {
        this.records = records;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }
}
