package com.piedra.platease.controller.system;

import com.piedra.platease.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;

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
        return "login";
    }

    /**
     * 系统登录
     * @return  返回登录之后的页面
     */
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(HttpServletRequest request){
        String resultURL = Constants.CONTEXT_PATH ;
        String username = request.getParameter("username");
        String password = request.getParameter("password");

//        // 获取HttpSession中的验证码
//        String verifyCode = (String)request.getSession().getAttribute("verifyCode");
//        // 获取用户请求表单中输入的验证码
//        String submitCode = WebUtils.getCleanParam(request, "verifyCode");
//        System.out.println("用户[" + username + "]登录时输入的验证码为[" + submitCode + "],HttpSession中的验证码为[" + verifyCode + "]");
//        if (StringUtils.isEmpty(submitCode) || !StringUtils.equals(verifyCode, submitCode.toLowerCase())){
//            request.setAttribute("message_login", "验证码不正确");
//            return resultPageURL;
//        }

        username = "test01";
        password = "f379eaf3c831b04de153469d1bec345e";
//        password = "17b984ed10044d28597d73be336cf613";

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            resultURL = "/home";
        }catch(UnknownAccountException uae){
            logger.error("对用户[{}]进行登录验证..验证未通过,未知账户", username);
            request.setAttribute("error_message", "未知账户");
        }catch(IncorrectCredentialsException ice){
            logger.error("对用户[{}]进行登录验证..验证未通过,错误的凭证",username);
            request.setAttribute("error_message", "密码不正确");
        }
        if(!currentUser.isAuthenticated()){
            token.clear();
        }
        return InternalResourceViewResolver.REDIRECT_URL_PREFIX + resultURL;
    }

    /**
     * 退出系统
     */
    @RequestMapping(value="/logout", method=RequestMethod.POST)
    public void logout(){

    }

    /**
     * 系统首页面
     */
    @RequestMapping(value="/home")
    public String home(){
        return "/system/home";
    }


    /**
     * 未授权
     */
    @RequestMapping(value="/unAuth")
    public String unAuth(){
        return "system/unAuth";
    }


}
