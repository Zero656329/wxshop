package com.hcsp.wxshop.service;

public interface SmsCodeService {
    //向手机发送验证码，返回正确答案
    String sendSmsCode(String tel);
}
