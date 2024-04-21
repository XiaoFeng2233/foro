package com.sjxy.bbs.entity.mq;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
public class SyncTopicToMeilisearchMessage extends BaseMessage{
    private Long id;
    private String title;
    private String content;
    private String summary;
    private String publisherUserNickName;
    private Long publisherUserId;
    private Date publishTime;
}
