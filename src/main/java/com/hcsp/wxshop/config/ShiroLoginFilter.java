package com.hcsp.wxshop.config;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@Component
public class ShiroLoginFilter extends FormAuthenticationFilter {
    //访问前是否登录，返回401,true继续往前走，false结束过滤
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse=(HttpServletResponse)response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        return false;
    }
}
