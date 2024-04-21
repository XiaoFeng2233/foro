package com.sjxy.bbs.entity.constants;

public class TokenConstants {

    //TOKEN有效期 ,单位天
    public static final Integer TOKEN_ACTIVE_DAYS = 7;

    //TOKEN的长度
    public static final Integer TOKEN_LENGTH = 280;

    //登录失败达到多少次数封禁
    public static final Integer LOGIN_FAIL_FORBIDDEN_TIMES = 4;


    //登录失败封禁时长 单位 分钟
    public static final Integer LOGIN_FAIL_FORBIDDEN_DURATION = 30;
}
