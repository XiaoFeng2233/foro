package com.sjxy.bbs.entity.bo;

import lombok.Data;

@Data
public class UserCollectRelateBO {

    private Long id;

    private Long userId;

    private Long topicId;

    private UserBO user;

    private TopicBO topic;
}
