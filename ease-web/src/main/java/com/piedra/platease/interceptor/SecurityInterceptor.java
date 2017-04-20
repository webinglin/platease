package com.piedra.platease.interceptor;

import com.piedra.platease.constants.SessionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 安全验证拦截器
 * 如果用户尚未登录就像翻墙进入我们强悍的系统，我们的安全拦截器会将其拒之门外，使其不得不 按规则来登录
 * @author linwb
 * @since 2014-9-30
 */
// FIXME 改用shiro来替代
@Deprecated
public class SecurityInterceptor implements HandlerInterceptor{
	private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
	
	/**
	 * 允许通过的URL地址
	 */
	private String[] safityURLArr ;
	
	
	@Override
	public void afterCompletion(HttpServletRequest req,HttpServletResponse resp, Object obj, Exception exception) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object obj, ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 请求之前就拦截掉
	 * 进行安全验证，此处是验证当前登录的用户是否存在session中，如果不存在，令其到登录页面去敲键盘或者使用PKI登录
	 */
	@SuppressWarnings("static-access")
    @Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object obj) throws Exception {
		//应用的首页路径
		String contextPath = req.getContextPath();
		//请求访问的URL地址
		String reqURL = req.getRequestURL().toString();		
		
		Object currentUser = req.getSession().getAttribute(SessionConstants.CURRENT_USER);

		if(null != currentUser){
		    return true ;
        }

        // 用户尚未登录,则默认此登录非法,但是如果请求的URL地址又在合法的URL链接列表中,那么就将此次请求设置为合法(即 true)
        boolean accessable = false;
        for(String safityURL : safityURLArr){
            // 如果当前用户还没有登录,并且请求的URL地址是合法的URL地址,那么就放行
            if(reqURL.matches("^.*"+contextPath+safityURL+"$")){
                accessable = true;
                break;
            }
        }

        if(!accessable) {
            logger.error("当前用户没有登录, 并且访问的地址:{} 不在安全登录链接中.", reqURL);
        }
        return accessable;
	}

	public String[] getSafityURLArr() {
		return safityURLArr;
	}

	public void setSafityURLArr(String[] safityURLArr) {
		this.safityURLArr = safityURLArr;
	}
}
