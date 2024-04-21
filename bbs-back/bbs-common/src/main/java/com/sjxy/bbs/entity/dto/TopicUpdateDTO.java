package com.sjxy.bbs.entity.dto;

import lombok.Data;

@Data
public class TopicUpdateDTO {
    private Long id;
    private String title;
    private String content;
    private Integer sticky;
    private Integer recommend;
    private Integer isLock;
}
