package com.sjxy.bbs.entity.query;

import lombok.Data;

@Data
public class UserVerifyCodeQuery {
    private Long userId;
    private String token;
    private String code;
    private Integer type;
}
