package com.piedra.platease.constants;

/**
 * 系统通用常量
 * @author webinglin
 * @since 2017-04-02
 */
public class Constants {

    public static final String COMMA = ",";
    public static final String PAUSE = "、";
    public static final String SEMICOLON = ";";
    public static final String SINGLE_QUOTES="'";
    public static final String MIDDLELINE = "-";
    public static final String UNDERLINE = "_";
    public static final String VERTICALLINE = "|";
    public static final String SLASH = "/";
    public static final String PERCENT = "%";

    /** 用户默认密码 */
    public static final String DEFAULT_PASSWORD = "Plat-Ease-666";

    /** 顶级节点的ID */
    public static final String PARENT_ID = "00000000000000000000000000000000";

    /* 字符集 */
    public static final String CHAR = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /* 私钥 */
    public static final String PRIVATE_KEY = "privateKey";
    /* 公钥 */
    public static final String PUBLIC_KEY = "publicKey";

    /* 排序顺序 */
    public static class Order {
        public static final String DESC = "desc";
        public static final String ASC = "asc";
    }

    /**  状态码 */
    public static class Status {
        /** 可用 */
        public static final byte AVAILABLE = 1;
        /** 删除 */
        public static final byte DELETE = 2;
    }

}
