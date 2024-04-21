package com.sjxy.bbs.entity.mq;

import lombok.Data;

@Data
public class NotifyReduceFanMessage extends BaseMessage{
    private Long followUserId;
    private Long followedUserId;
}
