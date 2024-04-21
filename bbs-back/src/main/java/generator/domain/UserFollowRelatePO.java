package generator.domain;

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
public class UserFollowRelatePO implements Serializable {
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

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 创建者用户ID
     */
    @TableField(value = "create_by")
    private Long createBy;

    /**
     * 创建者用户名
     */
    @TableField(value = "create_username")
    private String createUsername;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 更新者用户ID
     */
    @TableField(value = "update_by")
    private Long updateBy;

    /**
     * 更新者用户名
     */
    @TableField(value = "update_username")
    private String updateUsername;

    /**
     * 版本
     */
    @TableField(value = "version")
    private Integer version;

    /**
     * 是否删除 0 否 1 是
     */
    @TableField(value = "deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}