package com.piedra.platease.shiro.realm;

import com.piedra.platease.model.system.User;
import com.piedra.platease.service.system.UserService;
import com.piedra.platease.utils.PasswordUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 默认登录的Realm，处理身份验证和授权
 * @author webinglin
 * @since 2017-04-19
 */
public class DefaultLoginRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(DefaultLoginRealm.class);

    private static final String REALM_NAME = "loginRealm";


    @Autowired
    private UserService userService;

    /**
     * 授权操作
     * 该方法要被执行到，需要在spring-mvc中启用aop拦截器： <aop:config proxy-target-class="true" />
     * @param principals    当前用户信息
     * @return  返回授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("用户授权开始------------");

        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principals);
            SecurityUtils.getSubject().logout();
            return null;
        }

        // TODO 加缓存     在进行isPermit之前，都会调用该方法获取权限和角色
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        try {
            User user = (User) principals.getPrimaryPrincipal();
            String userId = user.getId();

            //TODO 根据userId查询相应的资源权限

            logger.info("用户授权成功------------");

        } catch (Exception e){
            logger.error("授权出错", e);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     * @param token 当前登录令牌(登录用户信息）
     * @return  返回认证消息
     * @throws AuthenticationException  认证出错的异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("开始用户身份认证------------");
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        User user ;
        try {
            user = userService.getByUsername(username);
        } catch (Exception e){
            logger.error("根据用户名查询用户信息出错", e);
            throw new UnknownAccountException("查询用户报错");
        }

        if(null == user){
            throw new UnknownAccountException("未知的用户名");
        }

        // 用户存在之后，获取用户的salt值，并进行salt加密，重新设置到token中
        upToken.setPassword(PasswordUtil.encryptPassword(new String(upToken.getPassword()), user.getSalt()).toCharArray());

        //TODO 处理密码错误缓存 错误多少次 不让登录

        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), REALM_NAME);
    }
}
