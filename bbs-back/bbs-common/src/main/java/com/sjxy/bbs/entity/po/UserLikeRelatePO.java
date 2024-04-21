package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName t_user_like_relate
 */
@TableName(value = "t_user_like_relate")
@Data
public class UserLikeRelatePO extends BasePO {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发起点赞的用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 被点赞的帖子ID
     */
    @TableField(value = "topic_id")
    private Long topicId;

    @TableField(exist = false)
    private UserPO user;

    @TableField(exist = false)
    private TopicPO topic;

}