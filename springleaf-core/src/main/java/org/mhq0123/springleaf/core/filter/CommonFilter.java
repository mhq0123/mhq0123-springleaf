package org.mhq0123.springleaf.core.filter;

import org.mhq0123.springleaf.common.utils.ThreadLocalUtils;
import org.mhq0123.springleaf.core.SpringleafCoreConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * project: mhq0123-springleaf
 * author:  mhq0123
 * date:    2017/1/4.
 * desc:    常见拦截、设置日志追踪号、线程变量等
 */
@WebFilter
public class CommonFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CommonFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>RequestURI:{}", httpServletRequest.getRequestURI());
        // 设置ip
        ThreadLocalUtils.put(SpringleafCoreConstants.Request.IP, httpServletRequest.getRemoteAddr());

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        ThreadLocalUtils.removeAll();
    }
}
