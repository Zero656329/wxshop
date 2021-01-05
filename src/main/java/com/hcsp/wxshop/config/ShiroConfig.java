package com.hcsp.wxshop.config;

import com.hcsp.wxshop.generate.User;
import com.hcsp.wxshop.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig implements WebMvcConfigurer {
    @Autowired
    UserService userService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor(userService));
    }




    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager,ShiroLoginFilter ShiroLoginFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> parttern = new HashMap<String, String>();
        parttern.put("/api/code", "anon");
        parttern.put("/api/login", "anon");
        parttern.put("/api/status", "anon");
        parttern.put("/api/logout", "anon");
        parttern.put("/**", "authc");
        Map<String, Filter> filterMap=new LinkedHashMap<>();
        filterMap.put("shiroLoginFilter",ShiroLoginFilter);
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(parttern);
        return shiroFilterFactoryBean;

    }

    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //MemoryConstrainedCacheManager内存受限制的缓存管理器
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        //某个区域验权
        securityManager.setRealm(shiroRealm);

//
        securityManager.setSessionManager(new DefaultWebSessionManager());
        return securityManager;
    }

    @Bean
    public ShiroRealm myshiroRealm(VerificationCodeCheckService verificationCodeCheckService) {
        return new ShiroRealm(verificationCodeCheckService);
    }

}
