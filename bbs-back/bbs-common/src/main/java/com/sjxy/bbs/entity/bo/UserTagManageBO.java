package com.sjxy.bbs.entity.bo;

import lombok.Data;

@Data
public class UserTagManageBO {

    private Long id;

    private Long userId;

    private Long tagId;

    private UserBO user;

    private TagBO tag;
}
