package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@TableName(value = "t_user")
@Data
public class UserPO extends BasePO {

    @TableField("username")
    private String username;

    @TableField("email")
    private String email;

    @TableField("email_verified")
    private Integer emailVerified;

    @TableField("nickname")
    private String nickname;

    @TableField("avatar")
    private String avatar;

    @TableField("gender")
    private Integer gender;

    @TableField("birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    @TableField("background_image")
    private String backgroundImage;

    @TableField("password")
    private String password;

    @TableField("description")
    private String description;

    @TableField("score")
    private Integer score;

    @TableField("status")
    private Integer status;

    @TableField("topic_count")
    private Integer topicCount;

    @TableField("comment_count")
    private Integer commentCount;

    @TableField("follow_count")
    private Integer followCount;

    @TableField("collect_count")
    private Integer collectCount;

    @TableField("fans_count")
    private Integer fansCount;

    @TableField("forbidden_end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forbiddenEndTime;

    @TableField("mute_end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date muteEndTime;

    @TableField(exist = false)
    private List<RolePO> roles;

    @TableField(exist = false)
    private List<PermissionPO> permissions;
}