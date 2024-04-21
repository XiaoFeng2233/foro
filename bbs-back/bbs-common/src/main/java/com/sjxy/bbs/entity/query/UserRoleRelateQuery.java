package com.sjxy.bbs.entity.query;

import lombok.Data;

@Data
public class UserRoleRelateQuery {
    private Long userId;
    private Long roleId;

    private String code;

}
