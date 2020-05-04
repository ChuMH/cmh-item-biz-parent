package com.cmh.item.biz.web.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author：
 * @data：
 * @description：
 */
@Slf4j
public class ProjectFilter implements Filter {

    /**
     * 容器加载完成调用
     */
    @Override
    public void init(FilterConfig filterConfig) {
        log.info("===>filter init()");
    }

    /**
     * 请求被拦截的时候调用
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("===>doFilter过滤器");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        filterChain.doFilter(request,response);

        //获取字段值
        //request.getParameter("user");

        //重定向
        //response.sendRedirect("/user/login");
    }

    /**
     * 容器被销毁的时候调用
     */
    @Override
    public void destroy() {
        log.info("===>filter destroy()");
    }
}
