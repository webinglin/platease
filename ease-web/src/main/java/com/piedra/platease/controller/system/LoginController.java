package com.piedra.platease.controller.system;

import com.piedra.platease.constants.SessionConstants;
import com.piedra.platease.constants.WebConstants;
import com.piedra.platease.dto.UserDTO;
import com.piedra.platease.utils.SessionHelper;
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
        if(StringUtils.isBlank(password) || userName.length()<6){
            session.setAttribute(SessionConstants.ERROR_MSG, "密码不能少于6位");
            return InternalResourceViewResolver.REDIRECT_URL_PREFIX  + resultURL;
        }

//        // 获取HttpSession中的验证码
//        String verifyCode = (String)request.getSession().getAttribute("verifyCode");
//        // 获取用户请求表单中输入的验证码
//        String submitCode = request.getParameter("verifyCode");
//        if (StringUtils.isEmpty(submitCode) || !StringUtils.equals(verifyCode, submitCode.toLowerCase())){
//            request.setAttribute("message_login", "验证码不正确");
//            return InternalResourceViewResolver.REDIRECT_URL_PREFIX + resultURL;
//        }

//        username = "platease";
//        password = "3068de31b70b1f2cf433ead658c144cb"; // md5加密

        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
//            resultURL = "/desktop";
            resultURL = "/system/manage";

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
