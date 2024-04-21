package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 消息通知表
 * @TableName t_notice
 */
@TableName(value ="t_notice")
@Data
public class NoticePO extends BasePO {


    /**
     * 图标地址
     */
    @TableField(value = "image")
    private String image;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 消息内容
     */
    @TableField(value = "content")
    private String content;

    @TableField(exist = false)
    private UserPO user;
}