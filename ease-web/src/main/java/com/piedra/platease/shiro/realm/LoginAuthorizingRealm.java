package com.piedra.platease.shiro.realm;

import com.piedra.platease.constants.SessionConstants;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;
import com.piedra.platease.service.system.UserService;
import com.piedra.platease.utils.PasswordUtil;
import com.piedra.platease.utils.SessionHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 默认登录的Realm，处理身份验证和授权
 * @author webinglin
 * @since 2017-04-19
 */
public class LoginAuthorizingRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(LoginAuthorizingRealm.class);

    private static final String REALM_NAME = "loginRealm";
    private static final String CACHE_NAME = "plateaseCache";

    @Autowired
    private UserService userService;


    public LoginAuthorizingRealm(){
        // 认证不需要缓存
        super.setAuthenticationCachingEnabled(false);
        // 授权需要缓存
        super.setAuthorizationCacheName(CACHE_NAME);
    }

    /**
     * 授权操作
     * 该方法要被执行到，需要在spring-mvc中启用aop拦截器： <aop:config proxy-target-class="true" />
     * @param principals    当前用户信息
     * @return  返回授权信息
     */
    @Override
    @SuppressWarnings("unchecked")
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()(可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        Subject subject = SecurityUtils.getSubject();
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            SessionHelper.removeCurrentSession(subject.getSession());
            SecurityUtils.getSubject().logout();
            return null;
        }

        logger.info("用户授权开始------------");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        try {
            User user = (User) principals.getPrimaryPrincipal();
            String userId = user.getId();

            List<Role> roles = userService.queryUserRoles(userId);
            Set<String> roleDescs = new HashSet<>();
            roles.forEach(role -> roleDescs.add(role.getId()));
            info.setRoles(roleDescs);

            List<Function> functions = userService.queryUserPermissions(userId);
            Set<String> funcUrlSet = new HashSet<>();
            functions.forEach(function -> funcUrlSet.add(function.getFuncUrl()));
            info.setStringPermissions(funcUrlSet);

            subject.getSession().setAttribute(SessionConstants.USER_PERMISSIONS,functions);

        } catch (Exception e){
            logger.error("授权出错", e);
        }
        return info;
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
        upToken.setPassword(PasswordUtil.encryptMd5Password(new String(upToken.getPassword()), user.getSalt()).toCharArray());

        //TODO 处理密码错误缓存 错误多少次 不让登录

        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), REALM_NAME);
    }
}
