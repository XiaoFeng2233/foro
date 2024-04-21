package com.sjxy.bbs.entity.bo;

import lombok.Data;

@Data
public class ForgetPasswordInfoBO {
    private String username;
    private String email;
    private Long userId;
}
