package com.sjxy.bbs.entity.mq;

import lombok.Data;

@Data
public class NotifyLikeTopicMessage extends BaseMessage{
    private Long userId;
    private Long topicId;
}
