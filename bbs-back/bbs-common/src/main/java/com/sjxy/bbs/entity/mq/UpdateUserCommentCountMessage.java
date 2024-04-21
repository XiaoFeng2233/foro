package com.sjxy.bbs.entity.mq;

import lombok.Data;

@Data
public class UpdateUserCommentCountMessage extends BaseMessage{
    private Long userId;
    private Integer count;
}
