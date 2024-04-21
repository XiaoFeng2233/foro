package com.sjxy.bbs.entity.dto;

import lombok.Data;

@Data
public class CommentPublishDTO {
    private Long topicId;
    private Long parentId;
    private String content;
}
