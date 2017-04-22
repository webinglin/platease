package com.piedra.platease.model.system;

/**
 * @author webinglin
 * @since 2017-04-21
 */
public class Function {
    private String funcId;
    private String funcTitle;
    private String funcUrl;
    private String imageUrl;
    private String funcType;
    private String permission;
    private String parentId;
    private String remark;
    private Integer orderNum;

    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Function function = (Function) o;

        if (funcId != null ? !funcId.equals(function.funcId) : function.funcId != null) return false;
        if (funcTitle != null ? !funcTitle.equals(function.funcTitle) : function.funcTitle != null) return false;
        if (funcUrl != null ? !funcUrl.equals(function.funcUrl) : function.funcUrl != null) return false;
        if (imageUrl != null ? !imageUrl.equals(function.imageUrl) : function.imageUrl != null) return false;
        if (funcType != null ? !funcType.equals(function.funcType) : function.funcType != null) return false;
        if (permission != null ? !permission.equals(function.permission) : function.permission != null) return false;
        if (parentId != null ? !parentId.equals(function.parentId) : function.parentId != null) return false;
        if (remark != null ? !remark.equals(function.remark) : function.remark != null) return false;
        if (orderNum != null ? !orderNum.equals(function.orderNum) : function.orderNum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = funcId != null ? funcId.hashCode() : 0;
        result = 31 * result + (funcTitle != null ? funcTitle.hashCode() : 0);
        result = 31 * result + (funcUrl != null ? funcUrl.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (funcType != null ? funcType.hashCode() : 0);
        result = 31 * result + (permission != null ? permission.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (orderNum != null ? orderNum.hashCode() : 0);
        return result;
    }
}
