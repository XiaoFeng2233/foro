package com.sjxy.bbs.entity.bo;

import lombok.Data;

import java.util.List;

@Data
public class LoginBO {
    private Long id;
    private String token;
    private String username;
    private List<String> roles;
    private List<String> permissions;
    private String avatar;
    private List<Long> mangedTagIds;
}
