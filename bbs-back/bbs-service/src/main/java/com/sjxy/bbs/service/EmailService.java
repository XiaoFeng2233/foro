package com.sjxy.bbs.service;

public interface EmailService {
    void sendRegisterMail(String destEmailAddress, String username, String token, String code);

    void sendForgetPasswordMail(String destEmailAddress, String username, String token, String code);
}
