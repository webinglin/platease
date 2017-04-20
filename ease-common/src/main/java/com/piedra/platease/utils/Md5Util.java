package com.piedra.platease.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * MD5工具类
 * @author webinglin
 * @since 2017-04-20
 */
public class Md5Util {


    /**
     * 获取MD5值
     * @param input 待加密的值
     * @return  返回MD5加密之后的值
     */
    public static String getMd5(String input){
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(input.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(getMd5("666666"));
    }
}
