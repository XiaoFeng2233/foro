package com.sjxy.bbs.entity.constants;

import lombok.Data;


public class UserConstants {

    //Bcrypt的加密次数
    public static Integer BCRYPT_STRENGTH = 10;


    //系统用户的用户名
    public static String SYSTEM_USER_NAME = "SYSTEM";

    //系统用户的ID
    public static Long SYSTEM_USER_ID = -1L;

    //新用户默认积分
    public static Integer DEFAULT_SCORE = 0;

    //新用户评论数量
    public static Integer DEFAULT_COMMENT_COUNT = 0;

    //新用户帖子数量
    public static Integer DEFAULT_TOPIC_COUNT = 0;

    //新用户粉丝数量
    public static Integer DEFAULT_FANS_COUNT = 0;

    //新用户关注数量
    public static Integer DEFAULT_FOLLOW_COUNT = 0;


    //新用户收藏帖子数量
    public static Integer DEFAULT_COLLECT_COUNT = 0;

    //用户注册后的邮件激活失效 单位:分钟
    public static Integer USER_EMAIL_ACTIVE_TIME = 15;

    //用户签到获得最小积分
    public static Integer USER_SIGNIN_MIN_SCORE = 1;

    //用户签到获得最大积分
    public static Integer USER_SIGNIN_MAX_SCORE = 10;


}
