package com.sjxy.bbs.entity.mq;

import lombok.Data;

@Data
public class NotifyNewFanMessage extends BaseMessage{
    private Long followUserId;
    private Long followedUserId;
}
