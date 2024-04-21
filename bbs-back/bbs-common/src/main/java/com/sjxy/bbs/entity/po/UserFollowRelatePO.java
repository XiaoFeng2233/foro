package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 关注表
 * @TableName t_user_follow_relate
 */
@TableName(value ="t_user_follow_relate")
@Data
public class UserFollowRelatePO extends BasePO {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发起关注者ID
     */
    @TableField(value = "follow_user_id")
    private Long followUserId;

    /**
     * 被关注者ID
     */
    @TableField(value = "followed_user_id")
    private Long followedUserId;


    @TableField(exist = false)
    private UserPO followUser;

    @TableField(exist = false)
    private UserPO followedUser;

}