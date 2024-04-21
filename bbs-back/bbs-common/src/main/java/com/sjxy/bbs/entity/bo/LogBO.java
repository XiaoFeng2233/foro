package com.sjxy.bbs.entity.bo;

import lombok.Data;

import java.util.Date;

@Data
public class LogBO {
    private Long id;

    private Long userId;

    private Integer type;

    private String ip;

    private String location;

    private Date createTime;
}
