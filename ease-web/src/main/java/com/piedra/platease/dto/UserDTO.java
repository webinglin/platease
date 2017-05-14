package com.piedra.platease.dto;

import java.util.Date;

/**
 * @author webinglin
 * @since 2017-04-26
 */
public class UserDTO {
    /** 用户ID */
    private String id;
    /** 用户名 */
    private String userName;
    /** 证件号码 */
    private String idcard;
    /** 实名 */
    private String realName;
    /** 密码 */
    private String password;
    /** 单位ID */
    private String deptId;
    /** 单位编码 */
    private String deptCode;
    /** 手机号码 */
    private String telphone;
    /** 背景图 */
    private String homeBgurl;
    /** 备注 */
    private String remark;
    /** 单位名称 */
    private String deptName;

    /** 状态 */
    private Byte status;
    /** 最后登录时间 */
    private Date lastLoginTime;
    /** 创建者ID */
    private String creatorId;
    /** 创建时间 */
    private Date createTime;


    /** 创建者姓名 */
    private String creatorName;
    /** 创建时间 */
    private String createTimeStr;
    /** 最后登录时间 */
    private String lastLoginTimeStr;
    /** 最后登录IP */
    private String lastLoginIp;
    /** 拥有的角色 */
    private String roleNames;


    /** 用户拥有的角色ID */
    private String roleIds;

    private String searchCont;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getHomeBgurl() {
        return homeBgurl;
    }

    public void setHomeBgurl(String homeBgurl) {
        this.homeBgurl = homeBgurl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getLastLoginTimeStr() {
        return lastLoginTimeStr;
    }

    public void setLastLoginTimeStr(String lastLoginTimeStr) {
        this.lastLoginTimeStr = lastLoginTimeStr;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
