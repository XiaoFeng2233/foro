package com.sjxy.bbs.entity.mq;


import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class UpdateUserTopicCountMessage extends BaseMessage{
    private Long userId;
    private Integer count;
}
