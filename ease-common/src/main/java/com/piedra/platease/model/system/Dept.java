package com.piedra.platease.model.system;

/**
 * @author webinglin
 * @since 2017-04-21
 */
public class Dept {
    private String deptId;
    private String deptName;
    private String deptCode;
    private String parentId;
    private String alias;
    private String remark;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dept dept = (Dept) o;

        if (deptId != null ? !deptId.equals(dept.deptId) : dept.deptId != null) return false;
        if (deptName != null ? !deptName.equals(dept.deptName) : dept.deptName != null) return false;
        if (deptCode != null ? !deptCode.equals(dept.deptCode) : dept.deptCode != null) return false;
        if (parentId != null ? !parentId.equals(dept.parentId) : dept.parentId != null) return false;
        if (alias != null ? !alias.equals(dept.alias) : dept.alias != null) return false;
        if (remark != null ? !remark.equals(dept.remark) : dept.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = deptId != null ? deptId.hashCode() : 0;
        result = 31 * result + (deptName != null ? deptName.hashCode() : 0);
        result = 31 * result + (deptCode != null ? deptCode.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
