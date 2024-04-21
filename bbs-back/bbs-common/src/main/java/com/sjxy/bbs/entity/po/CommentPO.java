package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName t_comment
 */
@TableName(value ="t_comment")
@Data
public class CommentPO extends BasePO {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论者ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 帖子ID
     */
    @TableField(value = "topic_id")
    private Long topicId;

    /**
     * 详细内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 回复的父评论ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * ip地址
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * ip的地理位置
     */
    @TableField(value = "ip_location")
    private String ipLocation;

    /**
     * 状态 0不可见 1可见
     */
    @TableField(value = "status")
    private Integer status;


    @TableField(exist = false)
    private CommentPO parent;

    @TableField(exist = false)
    private UserPO user;

    @TableField(exist = false)
    private TopicPO topic;

}