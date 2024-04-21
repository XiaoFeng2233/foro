package com.sjxy.bbs.controller;

import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.service.CaptchaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CaptchaController {
    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/login-captcha")
    public ObjectResult<String> loginCaptcha() {
        return ObjectResult.ok(captchaService.generateCaptcha());
    }

    @GetMapping("/login-captcha/{id}")
    public void showLoginCaptcha(@PathVariable("id")String id, HttpServletResponse response){
        try {
            captchaService.showCaptcha(response,id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/register-captcha")
    public ObjectResult<String> registerCaptcha() {
        return ObjectResult.ok(captchaService.generateCaptcha());
    }

    @GetMapping("/register-captcha/{id}")
    public void showRegisterCaptcha(@PathVariable("id")String id, HttpServletResponse response){
        try {
            captchaService.showCaptcha(response,id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/forget-password-captcha")
    public ObjectResult<String> forgetPasswordCaptcha(){
        return ObjectResult.ok(captchaService.generateCaptcha());
    }

    @GetMapping("/forget-password-captcha/{id}")
    public void showForgetPasswordCaptcha(@PathVariable("id")String id, HttpServletResponse response){
        try {
            captchaService.showCaptcha(response,id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
