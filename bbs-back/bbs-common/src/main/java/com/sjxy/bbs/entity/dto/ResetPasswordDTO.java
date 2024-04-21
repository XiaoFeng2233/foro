package com.sjxy.bbs.entity.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String token;
    private String code;
    private String password;
}
