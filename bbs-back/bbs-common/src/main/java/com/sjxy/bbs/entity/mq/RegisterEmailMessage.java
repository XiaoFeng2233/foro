package com.sjxy.bbs.entity.mq;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class RegisterEmailMessage extends BaseMessage {
    private String destEmail;
    private String username;
    private String token;
    private String code;
}
