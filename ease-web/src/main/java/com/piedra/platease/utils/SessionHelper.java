package com.piedra.platease.utils;

import com.piedra.platease.constants.SessionConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Session管理帮助类
 * @author webinglin
 * @since 2017-04-22
 */
public class SessionHelper {
    private static Logger logger = LoggerFactory.getLogger(SessionHelper.class);

    public static void removeCurrentSession(Session session){
        // 清空自定以的session域的缓存
        try {
            Field[] fields = SessionConstants.class.getDeclaredFields();
            for (Field field : fields) {
                String sessionKey = (String) field.get(null);
                if (StringUtils.isNotBlank(sessionKey)) {
                    session.removeAttribute(sessionKey);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            logger.error("SessionContext中常量配置出错.");
        } catch (Exception e) {
            logger.error("SessionContext清除session中的值失败.");
        }
    }
}
