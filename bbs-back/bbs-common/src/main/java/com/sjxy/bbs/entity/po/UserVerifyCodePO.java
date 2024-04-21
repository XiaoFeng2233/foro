package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

@TableName(value ="t_user_verify_code")
@Data
public class UserVerifyCodePO extends BasePO {

    @TableField("user_id")
    private Long userId;

    @TableField("token")
    private String token;

    @TableField("code")
    private String code;

    @TableField("checked")
    private Integer checked;

    @TableField("expire_time")
    private Date expireTime;

    @TableField("type")
    private Integer type;

    @TableField(exist = false)
    private UserPO user;
}