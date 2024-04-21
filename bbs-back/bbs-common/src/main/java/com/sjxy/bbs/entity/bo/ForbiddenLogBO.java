package com.sjxy.bbs.entity.bo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ForbiddenLogBO {

    private Long id;


    private Long userId;


    private Date startTime;


    private Date finishTime;


    private String reason;

    private Date createTime;

    private String createUsername;
}
