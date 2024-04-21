package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 封禁记录表
 *
 * @TableName t_forbidden_log
 */
@TableName(value = "t_forbidden_log")
@Data
public class ForbiddenLogPO extends BasePO {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 被封禁的用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 封禁开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 封禁结束时间
     */
    @TableField(value = "finish_time")
    private Date finishTime;

    /**
     * 封禁理由
     */
    @TableField(value = "reason")
    private String reason;

}