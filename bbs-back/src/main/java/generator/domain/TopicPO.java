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
 * @TableName t_topic
 */
@TableName(value ="t_topic")
@Data
public class TopicPO implements Serializable {
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
    private LocalDateTime recommendTime;

    /**
     * 是否置顶 0否1是
     */
    @TableField(value = "sticky")
    private Integer sticky;

    /**
     * 置顶时间
     */
    @TableField(value = "sticky_time")
    private LocalDateTime stickyTime;

    /**
     * 查看数
     */
    @TableField(value = "view_count")
    private Long viewCount;

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
    private Long lastCommentTime;

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
    @TableField(value = "lock")
    private Integer lock;

    /**
     * 帖子锁定时间
     */
    @TableField(value = "lock_time")
    private Integer lockTime;

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