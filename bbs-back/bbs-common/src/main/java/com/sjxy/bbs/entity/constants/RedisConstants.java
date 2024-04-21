package com.sjxy.bbs.entity.constants;

public class RedisConstants {
    public static final String CAPTCHA_NAME = "captcha:generate:%s";
    public static final String IP_LOGIN_FAIL_TIMES = "ip:login_fail_times:%s";


    //根据ID查询用户的token信息
    public static final String HASH_USER_TOKEN_INFO = "user:token_info";

    public static final String INCREASE_TOPIC_VIEW_COUNT = "topic:increase_view_count";

}
