package com.piedra.platease.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * 集合操作工具类
 * @author webinglin
 * @since 2017-05-06
 */
public class CollectionUtil {

    /**
     * 将集合转成逗号分割的字符串
     * @param set   待转换集合
     * @return  返回逗号分割的字符串
     */
    public static String join(Set<String> set){
        if(CollectionUtils.isEmpty(set)){
            return StringUtils.EMPTY;
        }
        StringBuilder strBuilder = new StringBuilder();
        set.forEach(str -> strBuilder.append(",").append(str));
        return strBuilder.toString().substring(1);
    }

    public static String join(List<String> list){
        if(CollectionUtils.isEmpty(list)){
            return StringUtils.EMPTY;
        }
        StringBuilder strBuilder = new StringBuilder();
        list.forEach(str -> strBuilder.append(",").append(str));
        return strBuilder.toString().substring(1);
    }

}
