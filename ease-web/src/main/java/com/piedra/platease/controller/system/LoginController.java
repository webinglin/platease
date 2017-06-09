package com.piedra.platease.controller.system;

import com.piedra.platease.constants.Constants;
import com.piedra.platease.constants.SessionConstants;
import com.piedra.platease.constants.WebConstants;
import com.piedra.platease.dto.UserDTO;
import com.piedra.platease.utils.Md5Util;
import com.piedra.platease.utils.RSAUtil;
import com.piedra.platease.utils.SessionHelper;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Map;

/**
 * 登录页面
 * @author webinglin
 * @since 2017-04-19
 */
@Controller
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 跳转登录页面
     * @param  modelMap ModelMap对象
     * @return  返回登录页面
     */
    @RequestMapping(value="/")
    public String loginPage(ModelMap modelMap){

        try {
            // 每一次登录之后，这个过程传输加密信息都用同一个密钥对
            Map<String,String> keyPairMap = RSAUtil.generateKeyPair();
            SecurityUtils.getSubject().getSession().setAttribute(SessionConstants.KEY_PRIVATE, keyPairMap.get(Constants.PRIVATE_KEY));
            SecurityUtils.getSubject().getSession().setAttribute(SessionConstants.KEY_PUBLIC, keyPairMap.get(Constants.PUBLIC_KEY));
            modelMap.put(Constants.PUBLIC_KEY,keyPairMap.get(Constants.PUBLIC_KEY));
        } catch(Exception e){
            logger.error("生成密钥对出错", e);
            return "login/500";
        }

        // 可能会读取一堆的配置信息
        return "login/login";
    }

    /**
     * 系统登录
     * @return  返回登录之后的页面
     */
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(UserDTO user){
        String resultURL = WebConstants.CONTEXT_PATH ;

        Session session = SecurityUtils.getSubject().getSession();
        String userName = user.getUserName(), password = user.getPassword();
        if(StringUtils.isBlank(userName) || userName.length()<4){
            session.setAttribute(SessionConstants.ERROR_MSG, "用户名不能少于4位");
            return InternalResourceViewResolver.REDIRECT_URL_PREFIX + resultURL;
        }
        if(StringUtils.isBlank(password)){
            session.setAttribute(SessionConstants.ERROR_MSG, "密码不能为空");
            return InternalResourceViewResolver.REDIRECT_URL_PREFIX  + resultURL;
        }

        /* 利用session中保存的密钥进行解密 */
        try {
            String privateKey = session.getAttribute(SessionConstants.KEY_PRIVATE).toString();
            password = RSAUtil.decryptByPrivate(password,privateKey);
        } catch (Exception e) {
            logger.error("利用私钥对密码解密出错,密码设置为空", e);
            password = StringUtils.EMPTY;
        }
        if(password.length()<6){
            session.setAttribute(SessionConstants.ERROR_MSG, "密码不能少于6位");
            return InternalResourceViewResolver.REDIRECT_URL_PREFIX  + resultURL;
        }

        UsernamePasswordToken token = new UsernamePasswordToken(userName, Md5Util.getMd5(password));
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
//            resultURL = "/desktop";
            resultURL = "/system/manage";

            /* 登录成功后，移除session中的错误信息 */
            session.removeAttribute(SessionConstants.ERROR_MSG);
        }catch(UnknownAccountException uae){
            logger.error("对用户[{}]进行登录验证..验证未通过,未知账户", userName);
            session.setAttribute(SessionConstants.ERROR_MSG, "未知账户");
        }catch(IncorrectCredentialsException ice){
            logger.error("对用户[{}]进行登录验证..验证未通过,错误的凭证",userName);
            session.setAttribute(SessionConstants.ERROR_MSG, "密码不正确");
        }
        if(!currentUser.isAuthenticated()){
            token.clear();
        }
        return InternalResourceViewResolver.REDIRECT_URL_PREFIX + resultURL;
    }

    /**
     * 桌面
     */
    @RequestMapping(value="/desktop")
    public String desktop(){
        // TODO 获取一系列的个性化菜单配置   Request域即可
        return "/desktop/desktop";
    }

    /**
     * 退出系统
     */
    @RequestMapping(value="/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            SessionHelper.removeCurrentSession(subject.getSession());
            subject.logout();
        }
        return InternalResourceViewResolver.REDIRECT_URL_PREFIX + WebConstants.CONTEXT_PATH;
    }
}
