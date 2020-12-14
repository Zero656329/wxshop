package com.hcsp.wxshop.service;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public   class ShiroRealm extends AuthorizingRealm {
  private final VerificationCodeCheckService verificationCodeCheckService;
@Autowired
    public ShiroRealm(VerificationCodeCheckService verificationCodeCheckService) {
        this.verificationCodeCheckService = verificationCodeCheckService;
    //token用户提交信息，info用户真正的信息，如何比较两者信息
    this.setCredentialsMatcher((token,  info) -> new String((char[])token.getCredentials()).equals(info.getCredentials()));
    }

    //你有没有权限访问这个资源
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
    //看看这个人是不是你说的这个 ，token用户提供的账号和密码，由shiro进行比较
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String tel=  (String) token.getPrincipal();
      String correctCode= verificationCodeCheckService.getCode(tel);
      //将正确的信息存储
        return new SimpleAuthenticationInfo(tel,correctCode,getName());
    }
}
