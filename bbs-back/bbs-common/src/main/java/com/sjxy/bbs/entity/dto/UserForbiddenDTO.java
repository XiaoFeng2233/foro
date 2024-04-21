package com.sjxy.bbs.entity.dto;

import lombok.Data;

@Data
public class UserForbiddenDTO {
    private String endTime;
    private Long userId;
    private String reason;
}
