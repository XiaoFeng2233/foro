package com.sjxy.bbs.entity.bo;

import lombok.Data;

@Data
public class EmailConfigBO {
    private String host;
    private String port;
    private String username;
    private String password;
    private String address;
    private Boolean useSSL;
    private String registerTemplate;
    private String forgetTemplate;
}
