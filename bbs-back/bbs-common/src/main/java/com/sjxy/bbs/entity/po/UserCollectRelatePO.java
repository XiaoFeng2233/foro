package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 用户收藏帖子关系表
 * @TableName t_user_collect_relate
 */
@TableName(value ="t_user_collect_relate")
@Data
public class UserCollectRelatePO extends BasePO {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发起收藏的用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 被收藏的帖子ID
     */
    @TableField(value = "topic_id")
    private Long topicId;

    @TableField(exist = false)
    private UserPO user;

    @TableField(exist = false)
    private TopicPO topic;
}