package com.sjxy.bbs.consumer;

import cn.hutool.core.lang.Assert;
import com.sjxy.bbs.entity.mq.UpdateUserCollectCountMessage;
import com.sjxy.bbs.entity.mq.UpdateUserTopicCountMessage;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.query.UserQuery;
import com.sjxy.bbs.service.NoticeService;
import com.sjxy.bbs.service.UserFollowRelateService;
import com.sjxy.bbs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserExchangeListener {
    @Autowired
    private UserService userService;
    @Autowired
    private UserFollowRelateService userFollowRelateService;
    @Autowired
    private NoticeService noticeService;


    @RabbitListener(queues = {"update.user.topic.count.queue"})
    public void updateUserTopicCount(UpdateUserTopicCountMessage message) {
        log.info("接收到的消息：" + message.toString());
        Assert.isTrue(message.getCount() != null, "count 为空");
        Assert.isTrue(message.getUserId() != null, "userId 为空");
        UserQuery userQuery = new UserQuery();
        userQuery.setId(message.getUserId());
        UserPO userPO = userService.get(userQuery);
        userPO.setTopicCount(userPO.getTopicCount() + message.getCount());
        userService.update(userPO);
    }

    @RabbitListener(queues = {"update.user.comment.count.queue"})
    public void updateUserCommentCount(UpdateUserTopicCountMessage message) {
        log.info("接收到的消息：" + message.toString());
        Assert.isTrue(message.getCount() != null, "count 为空");
        Assert.isTrue(message.getUserId() != null, "userId 为空");
        UserQuery userQuery = new UserQuery();
        userQuery.setId(message.getUserId());
        UserPO userPO = userService.get(userQuery);
        userPO.setCommentCount(userPO.getCommentCount() + message.getCount());
        userService.update(userPO);
    }

    @RabbitListener(queues = {"update.user.collect.count.queue"})
    public void updateUserCollectCount(UpdateUserCollectCountMessage message) {
        log.info("接收到的消息：" + message.toString());
        Assert.isTrue(message.getCount() != null, "count 为空");
        Assert.isTrue(message.getUserId() != null, "userId 为空");
        UserQuery userQuery = new UserQuery();
        userQuery.setId(message.getUserId());
        UserPO userPO = userService.get(userQuery);
        userPO.setCollectCount(userPO.getCollectCount() + message.getCount());
        userService.update(userPO);
    }

}
