package com.sjxy.bbs.entity.dto;

import lombok.Data;

@Data
public class EmailConfigUpdateDTO {
    private String host;
    private String port;
    private String username;
    private String password;
    private String address;
    private Short useSSL;
}
