package com.sjxy.bbs.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class TopicPublishDTO {
    private String title;
    private Long tagId;
    private String content;
    private String summary;
    private List<String> imageFileIds;
    private Integer lock;
    private Integer scoreRequire;
}
