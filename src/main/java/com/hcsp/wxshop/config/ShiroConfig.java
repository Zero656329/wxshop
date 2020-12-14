package com.hcsp.wxshop.config;

import com.hcsp.wxshop.service.ShiroRealm;
import com.hcsp.wxshop.service.VerificationCodeCheckService;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> parttern=new HashMap<String, String>();
        parttern.put("/api/code", "anon");
        parttern.put("/api/login", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(parttern);
        return shiroFilterFactoryBean;

    }
    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
       //MemoryConstrainedCacheManager内存受限制的缓存管理器
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        //某个区域验权
        securityManager.setRealm(shiroRealm);

//
        securityManager.setSessionManager(new DefaultSessionManager());
        return securityManager;
    }
    @Bean
    public  ShiroRealm myshiroRealm(VerificationCodeCheckService verificationCodeCheckService){
        return new ShiroRealm(verificationCodeCheckService);
    }

}
