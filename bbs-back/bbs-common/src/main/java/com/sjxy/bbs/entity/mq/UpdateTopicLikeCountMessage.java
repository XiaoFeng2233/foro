package com.sjxy.bbs.entity.mq;

import lombok.Data;

@Data
public class UpdateTopicLikeCountMessage extends BaseMessage{
    private Long topicId;
    private Integer count;
}
