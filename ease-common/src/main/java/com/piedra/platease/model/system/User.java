package com.piedra.platease.model.system;

import java.util.Date;

/**
 * @author webinglin
 * @since 2017-04-06
 */
public class User {
    private String id;
    private String userName;
    private String idcard;
    private String realName;
    private String password;
    /** 加密密码使用，新增用户的时候随机生成 */
    private String salt;
    private String deptId;
    private String deptCode;
    private Byte status;
    private String lastLoginIp;
    private Date lastLoginTime;
    private String telphone;
    private String homeBgurl;
    private String creatorId;
    private Date createTime;
    private String remark;

    private String deptName;

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

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User sysUser = (User) o;

        if (id != null ? !id.equals(sysUser.id) : sysUser.id != null) return false;
        if (userName != null ? !userName.equals(sysUser.userName) : sysUser.userName != null) return false;
        if (idcard != null ? !idcard.equals(sysUser.idcard) : sysUser.idcard != null) return false;
        if (realName != null ? !realName.equals(sysUser.realName) : sysUser.realName != null) return false;
        if (password != null ? !password.equals(sysUser.password) : sysUser.password != null) return false;
        if (deptId != null ? !deptId.equals(sysUser.deptId) : sysUser.deptId != null) return false;
        if (deptCode != null ? !deptCode.equals(sysUser.deptCode) : sysUser.deptCode != null) return false;
        if (status != null ? !status.equals(sysUser.status) : sysUser.status != null) return false;
        if (lastLoginIp != null ? !lastLoginIp.equals(sysUser.lastLoginIp) : sysUser.lastLoginIp != null) return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(sysUser.lastLoginTime) : sysUser.lastLoginTime != null)
            return false;
        if (telphone != null ? !telphone.equals(sysUser.telphone) : sysUser.telphone != null) return false;
        if (homeBgurl != null ? !homeBgurl.equals(sysUser.homeBgurl) : sysUser.homeBgurl != null) return false;
        if (creatorId != null ? !creatorId.equals(sysUser.creatorId) : sysUser.creatorId != null) return false;
        if (createTime != null ? !createTime.equals(sysUser.createTime) : sysUser.createTime != null) return false;
        if (remark != null ? !remark.equals(sysUser.remark) : sysUser.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (idcard != null ? idcard.hashCode() : 0);
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (deptId != null ? deptId.hashCode() : 0);
        result = 31 * result + (deptCode != null ? deptCode.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (lastLoginIp != null ? lastLoginIp.hashCode() : 0);
        result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
        result = 31 * result + (telphone != null ? telphone.hashCode() : 0);
        result = 31 * result + (homeBgurl != null ? homeBgurl.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
