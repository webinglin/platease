package com.piedra.platease.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * @author webinglin
 * @since 2017-05-06
 */
public class StringUtil {

    /**
     * 将字符串用圆括号包围
     * @param str   字符串
     * @return  返回圆括号包含的字符串
     */
    public static String fillParenthesis(String str){
        if(str == null){
            str = StringUtils.EMPTY;
        }
        return "(" + str + ")";
    }

}
