package com.piedra.platease.shiro.listener;

import com.piedra.platease.utils.SessionHelper;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * Shiro Session监听器
 *
 * @author webinglin
 * @since 2017-04-22
 */
public class ShiroSessionListener implements SessionListener {

    @Override
    public void onStart(Session session) {
        System.out.println(1);
    }

    @Override
    public void onStop(Session session) {
    }

    @Override
    public void onExpiration(Session session) {
        SessionHelper.removeCurrentSession(session);
    }
}
