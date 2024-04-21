package com.sjxy.bbs.entity.mq;

import lombok.Data;

@Data
public class UpdateTopicCommentCountMessage extends BaseMessage {
    private Long topicId;
    private Integer count;
}
