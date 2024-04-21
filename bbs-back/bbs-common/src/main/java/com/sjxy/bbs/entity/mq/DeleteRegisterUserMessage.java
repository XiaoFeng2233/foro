package com.sjxy.bbs.entity.mq;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class DeleteRegisterUserMessage extends BaseMessage {
    private Long userId;
    private String username;
    private String email;
    private Date expireTime;
}
