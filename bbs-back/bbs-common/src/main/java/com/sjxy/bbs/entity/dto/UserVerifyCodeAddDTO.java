package com.sjxy.bbs.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserVerifyCodeAddDTO {
    private Long userId;

    private String token;

    private String code;

    private Date expireTime;

    private Integer type;
}
