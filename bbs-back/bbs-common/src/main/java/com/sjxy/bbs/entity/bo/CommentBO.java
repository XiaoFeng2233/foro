package com.sjxy.bbs.entity.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sjxy.bbs.entity.po.CommentPO;
import lombok.Data;

import java.util.Date;

@Data
public class CommentBO {
    private Long id;

    private Long userId;

    private Long topicId;

    private String content;

    private Long parentId;

    private String ip;

    private String ipLocation;

    private CommentBO parent;

    private UserBO user;

    private Date createTime;
}
