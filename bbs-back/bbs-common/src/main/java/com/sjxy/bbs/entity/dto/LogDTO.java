package com.sjxy.bbs.entity.dto;

import lombok.Data;

@Data
public class LogDTO {
    private Long userId;
    private Integer type;
    private String ip;
    private String location;
}
