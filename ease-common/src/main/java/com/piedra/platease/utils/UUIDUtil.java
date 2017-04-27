package com.piedra.platease.utils;

import java.util.UUID;

/**
 * UUID生成工具
 * @author webinglin
 * @since 2017-04-26
 */
public class UUIDUtil {

    /**
     * 生成32位的UUID
     * @return  返回32位的UUID
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
