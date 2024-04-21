package generator.domain;

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
public class CommentPO implements Serializable {
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

    /**
     * 
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