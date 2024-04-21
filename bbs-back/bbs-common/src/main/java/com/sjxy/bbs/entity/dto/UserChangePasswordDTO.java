package com.sjxy.bbs.entity.dto;

import lombok.Data;

@Data
public class UserChangePasswordDTO {
    private String oldPwd;
    private String newPwd;
}
