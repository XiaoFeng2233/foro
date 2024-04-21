package com.sjxy.bbs.entity.dto;

import lombok.Data;

@Data
public class ForgetPasswordDTO {
    private String email;
    private String captcha;
    private String captchaId;
}
