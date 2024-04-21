package com.sjxy.bbs.entity.mq;

import lombok.Data;

@Data
public class NotifyUnCollectTopicMessage extends BaseMessage{
    private Long userId;
    private Long topicId;
}
