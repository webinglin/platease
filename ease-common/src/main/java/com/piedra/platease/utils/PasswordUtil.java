package com.piedra.platease.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 密码工具类
 * @author webinglin
 * @since 2017-04-20
 */
public class PasswordUtil {


    /**
     * 根据salt对已经计算md5的密码再度加密
     * @param md5Password   已经用MD5加密过一次的密码
     * @param salt          盐
     * @return  返回再度加密的密码
     */
    public static String encryptPassword(String md5Password, String salt    ){
        return new Md5Hash(md5Password, salt, 1).toString();
    }


    public static void main(String[] args) {
        System.out.println( encryptPassword("f379eaf3c831b04de153469d1bec345e","asd"));
    }



}
