package com.piedra.platease.utils;

import com.piedra.platease.model.system.User;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.applet.Main;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体类和Map对象转化工具类
 * @author webinglin
 * @since 2017-04-28
 */
public class BeanMapUtil {
    private static Logger logger = LoggerFactory.getLogger(BeanMapUtil.class);

    /**
     * 将对象转成Map， 如果属性值为空，那么就跳过
     * @param obj   需要转成Map的对象
     * @return  返回Map集合
     */
    public static Map<String,Object> trans2Map(Object obj){
        Map<String, Object> map = new HashMap<>();
        try {
            Class<?> clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                String fieldName = field.getName();
                String methodName = "get" + StringUtils.uppercaseFirstChar(fieldName);
                Method method = clazz.getMethod(methodName);
                method.setAccessible(true);
                Object val = method.invoke(obj);
                if(val==null){
                    continue ;
                }

                //TODO 转成具体的类型？ 然后继续判断是否需要添加到Map中？
                map.put(fieldName, val);
            }
        } catch(Exception e){
            logger.error("Bean转成Map出错", e );
        }

        return map;
    }


    public static void main(String[] args) {
        User user = new User();
        user.setRealName("a");
        user.setDeptCode("222");
        user.setCreateTime(new Date());

        Map<String,Object> m = trans2Map(user);
        System.out.println(m.get("realName"));
    }
}
