package com.sjxy.bbs.entity.bo;


import lombok.Data;

import java.util.Date;

@Data
public class TopicBO {

    private Long id;

    private Long tagId;

    private Long userId;

    private String title;

    private String summary;

    private String content;

    private String imageList;

    private Integer recommend;

    private Integer sticky;

    private Long viewCount;

    private Long commentCount;

    private Long likeCount;

    private Long collectCount;

    private Integer isLock;

    private Date createTime;

    private UserBO user;

    private TagBO tag;

    //判断用户是否收藏了这个帖子
    private Boolean collected;

    //判断用户是否点赞了这个帖子
    private Boolean liked;

    private String ipA;

    private String locationA;

}
