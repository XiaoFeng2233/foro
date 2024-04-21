package com.sjxy.bbs.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface CaptchaService {
    String generateCaptcha();
    String getCaptcha(String id);
    void showCaptcha(HttpServletResponse response,String id) throws IOException;
}
