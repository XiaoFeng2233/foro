package com.sjxy.bbs.entity.mq;

import lombok.Data;

import java.util.Date;

@Data
public class AddLogMessage extends BaseMessage {
    private Long userId;
    private Integer type;
    private String ip;
    private String location;
    private Date createTime;
}
