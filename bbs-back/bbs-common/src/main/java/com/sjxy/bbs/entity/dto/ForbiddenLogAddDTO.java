package com.sjxy.bbs.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ForbiddenLogAddDTO {
    private Long userId;

    private Date startTime;

    private Date finishTime;

    private String reason;

}
