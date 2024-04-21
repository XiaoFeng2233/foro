package com.sjxy.bbs.entity.mq;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@ToString
public class BaseMessage {
    private String key;
    private Date sendTime;
}
