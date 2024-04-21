package com.sjxy.bbs.entity.query;

import lombok.Data;

@Data
public class UserQuery {
    private Long id;
    private String username;
    private String email;
    private Integer status;
}
