package com.sjxy.bbs.entity.bo;

import lombok.Data;

import java.util.Date;

@Data
public class SearchTopicBO {
    private Long id;
    private Date publishTime;
    private Long publisherUserId;
    private String publisherUserNickName;
    private String title;
    private String formattedSummary;
}
