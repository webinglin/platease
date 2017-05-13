package com.piedra.platease.dto;

/**
 * @author webinglin
 * @since 2017-05-06
 */
public class FunctionDTO {
    private String id;
    private String funcTitle;
    private String funcUrl;
    private String imageUrl;
    private String funcType;
    private String permission;
    private String parentId;
    private String remark;
    private String orderStr ;

    /** from - tree 表示属性结构  其他*/
    private String from;

    /** 搜索内容 */
    private String searchCont;

    public String getSearchCont() {
        return searchCont;
    }

    public void setSearchCont(String searchCont) {
        this.searchCont = searchCont;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFuncTitle() {
        return funcTitle;
    }

    public void setFuncTitle(String funcTitle) {
        this.funcTitle = funcTitle;
    }

    public String getFuncUrl() {
        return funcUrl;
    }

    public void setFuncUrl(String funcUrl) {
        this.funcUrl = funcUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFuncType() {
        return funcType;
    }

    public void setFuncType(String funcType) {
        this.funcType = funcType;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
