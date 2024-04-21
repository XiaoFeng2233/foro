package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @TableName t_topic
 */
@TableName(value ="t_topic")
@Data
public class TopicPO extends BasePO {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分类标签ID
     */
    @TableField(value = "tag_id")
    private Long tagId;

    /**
     * 发布者用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 纯文字内容
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 详细内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 图片URl集合 json格式
     */
    @TableField(value = "image_list")
    private String imageList;

    /**
     * 是否被加精 0否1是
     */
    @TableField(value = "recommend")
    private Integer recommend;

    /**
     * 加精时间
     */
    @TableField(value = "recommend_time")
    private Date recommendTime;

    /**
     * 是否置顶 0否1是
     */
    @TableField(value = "sticky")
    private Integer sticky;

    /**
     * 置顶时间
     */
    @TableField(value = "sticky_time")
    private Date stickyTime;

    /**
     * 查看数
     */
    @TableField(value = "view_count")
    private Long viewCount;

    /**
     * 收藏数
     */
    @TableField(value = "collect_count")
    private Long collectCount;

    /**
     * 评论数
     */
    @TableField(value = "comment_count")
    private Long commentCount;

    /**
     * 点赞数
     */
    @TableField(value = "like_count")
    private Long likeCount;

    /**
     * 状态 0不可见 1可见
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 最后评论时间
     */
    @TableField(value = "last_comment_time")
    private Date lastCommentTime;

    /**
     * 最后评论的用户ID
     */
    @TableField(value = "last_comment_user_id")
    private Long lastCommentUserId;

    /**
     * 发布者用户IP
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 发布者用户IP定位
     */
    @TableField(value = "ip_location")
    private String ipLocation;

    /**
     * 额外数据
     */
    @TableField(value = "extra_data")
    private String extraData;

    /**
     * 帖子是否被锁定 0否1是
     */
    @TableField(value = "is_lock",exist = true)
    private Integer isLock;

    /**
     * 帖子锁定时间
     */
    @TableField(value = "lock_time")
    private Date lockTime;

    /**
     * 查看帖子积分要求
     */
    @TableField(value = "require_score")
    private Integer requireScore;

    @TableField(exist = false)
    private UserPO user;

    @TableField(exist = false)
    private TagPO tag;

}