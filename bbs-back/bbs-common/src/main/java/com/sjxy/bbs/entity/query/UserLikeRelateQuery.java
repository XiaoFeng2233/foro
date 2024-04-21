package com.sjxy.bbs.entity.query;

import lombok.Data;

@Data
public class UserLikeRelateQuery {
    private Long userId;
    private Long topicId;
}
