package com.sjxy.bbs.entity.mq;

import lombok.Data;

@Data
public class NotifyCollectTopicMessage extends BaseMessage{
    private Long userId;
    private Long topicId;
}
