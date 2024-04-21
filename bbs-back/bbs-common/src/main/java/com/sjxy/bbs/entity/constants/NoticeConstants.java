package com.sjxy.bbs.entity.constants;

public class NoticeConstants {

    /**
     * 关注的用户发布新的帖子通知
     */
    public final static String NOTIFY_FANS_NEW_TOPIC_NOTICE = "您关注的用户 <a href='/users/${userId}'>${nickName}</a> 发布了新的 <a href='/topic/${topicId}'>帖子</a>";

    /**
     * 获得新的粉丝通知
     */
    public final static String NEW_FOLLOWER_NOTICE = "<a href='/users/${userId}'>${nickName}</a> 关注了您";

    /**
     * 粉丝取消关注通知
     */
    public final static String REDUCE_FOLLOWER_NOTICE = "<a href='/users/${userId}'>${nickName}</a> 取消了对您的关注";

    /**
     * 帖子被点赞通知
     */
    public final static String TOPIC_LIKE_NOTICE = "<a href='/users/${userId}'>${nickName}</a> 对您的<a href='/topic/${topicId}'> 帖子 </a>进行了点赞";

    /**
     * 帖子被取消点赞通知
     */
    public final static String TOPIC_UNLIKE_NOTICE = "<a href='/users/${userId}'>${nickName}</a> 对您的<a href='/topic/${topicId}'> 帖子 </a>取消了点赞";


    /**
     * 帖子被收藏通知
     */
    public final static String TOPIC_COLLECT_NOTICE = "<a href='/users/${userId}'>${nickName}</a> 对您的<a href='/topic/${topicId}'> 帖子 </a>进行了收藏";

    /**
     * 帖子被取消收藏通知
     */
    public final static String TOPIC_UNCOLLECT_NOTICE = "<a href='/users/${userId}'>${nickName}</a> 对您的<a href='/topic/${topicId}'> 帖子 </a>取消了收藏";
}
