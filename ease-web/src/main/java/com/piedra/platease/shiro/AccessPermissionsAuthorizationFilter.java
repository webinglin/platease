package com.piedra.platease.shiro;

import com.piedra.platease.constants.WebConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 资源访问拦截器
 *
 * @since 2017-04-20 by webinglin
 */
@Component("accessPerms")
public class AccessPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
    private static Logger logger = LoggerFactory.getLogger(AccessPermissionsAuthorizationFilter.class);

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        Subject subject = getSubject(request, response);

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        int i = uri.indexOf(contextPath);
        if (i > -1) {
            uri = uri.substring(i + contextPath.length());
        }
        if (StringUtils.isBlank(uri)) {
            uri = WebConstants.CONTEXT_PATH;
        }
        boolean permitted;
        if (WebConstants.CONTEXT_PATH.equals(uri)) {
            permitted = true;
        } else {
            permitted = subject.isPermitted(uri);
        }

        return permitted;
    }
}
