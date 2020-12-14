package com.hcsp.wxshop.service;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class VerificationCodeCheckService {
    private Map<String,String> telNumberToCorrectCode=new ConcurrentHashMap<>() ;


    public void addCode(String tel, String correctCode) {
        telNumberToCorrectCode.put(tel,correctCode);
    }

    public String  getCode(String tel) {
        return telNumberToCorrectCode.get(tel);
    }
}
