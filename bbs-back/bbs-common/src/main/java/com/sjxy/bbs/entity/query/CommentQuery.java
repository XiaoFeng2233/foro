package com.sjxy.bbs.entity.query;

import lombok.Data;

import java.util.List;

@Data
public class CommentQuery {
    private Long userId;
    private Long topicId;
    private List<Long> commentIds;
    private List<Long> topicIds;
    private List<Long> userIds;
}
