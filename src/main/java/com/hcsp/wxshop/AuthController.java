package com.hcsp.wxshop;

import com.hcsp.wxshop.entity.LoginResponse;
import com.hcsp.wxshop.generate.User;
import com.hcsp.wxshop.service.AuthService;
import com.hcsp.wxshop.service.UserContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/code")
    public void code(@RequestBody TelAndCode telAndCode) {
        authService.sendVerificationCode(telAndCode.getTel());

    }

    @PostMapping("/login")
    public void login(@RequestBody TelAndCode telAndCode) {
        UsernamePasswordToken token = new UsernamePasswordToken(telAndCode.getTel(), telAndCode.getCode());
        SecurityUtils.getSubject().login(token);
        //cookies存储
        token.setRememberMe(true);
    }

    @PostMapping("/logout")
    public void logout() {

        SecurityUtils.getSubject().logout();

    }


    @GetMapping("/status")
    public Object loginStatus(@RequestBody TelAndCode telAndCode) {
        if (UserContext.getCurrentUser() == null) {
            return LoginResponse.notLogin();
        } else {
            return LoginResponse.login(UserContext.getCurrentUser());
        }

    }

    public static class TelAndCode {
        private String tel;
        private String code;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
