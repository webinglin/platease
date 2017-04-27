package com.piedra.platease.dto;

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
}
