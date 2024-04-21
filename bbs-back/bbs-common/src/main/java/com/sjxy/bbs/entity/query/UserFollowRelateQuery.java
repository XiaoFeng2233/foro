package com.sjxy.bbs.entity.query;

import lombok.Data;

@Data
public class UserFollowRelateQuery {
    private Long followUserId;
    private Long followedUserId;
    private Long thirdUserId;
}
