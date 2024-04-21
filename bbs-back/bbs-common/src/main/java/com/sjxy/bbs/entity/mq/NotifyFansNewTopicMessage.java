package com.sjxy.bbs.entity.mq;

import lombok.Data;

@Data
public class NotifyFansNewTopicMessage extends BaseMessage{
    private Long creatorUserId;
    private Long topicId;
}
