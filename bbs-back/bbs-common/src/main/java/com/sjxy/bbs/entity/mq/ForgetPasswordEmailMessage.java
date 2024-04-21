package com.sjxy.bbs.entity.mq;

import lombok.Data;

@Data
public class ForgetPasswordEmailMessage extends BaseMessage{
    private String destEmail;
    private String username;
    private String token;
    private String code;
}
