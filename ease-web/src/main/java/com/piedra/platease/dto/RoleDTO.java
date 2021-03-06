package com.piedra.platease.dto;

/**
 * 角色DTO定义
 * @author webinglin
 * @since 2017-05-06
 */
public class RoleDTO {
    private String id;
    private String roleName;
    private String remark;

    private String searchCont;

    public String getSearchCont() {
        return searchCont;
    }

    public void setSearchCont(String searchCont) {
        this.searchCont = searchCont;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
