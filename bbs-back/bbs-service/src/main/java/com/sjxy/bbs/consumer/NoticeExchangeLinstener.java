package com.sjxy.bbs.consumer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.sjxy.bbs.entity.constants.NoticeConstants;
import com.sjxy.bbs.entity.constants.UserConstants;
import com.sjxy.bbs.entity.mq.*;
import com.sjxy.bbs.entity.po.NoticePO;
import com.sjxy.bbs.entity.po.TopicPO;
import com.sjxy.bbs.entity.po.UserFollowRelatePO;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.query.UserFollowRelateQuery;
import com.sjxy.bbs.entity.query.UserQuery;
import com.sjxy.bbs.service.NoticeService;
import com.sjxy.bbs.service.TopicService;
import com.sjxy.bbs.service.UserFollowRelateService;
import com.sjxy.bbs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class NoticeExchangeLinstener {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private UserFollowRelateService userFollowRelateService;
    @Autowired
    private UserService userService;
    @Autowired
    private TopicService topicService;

    @RabbitListener(queues = {"notify.fans.new.topic.queue"})
    public void notifyFansNewTopic(NotifyFansNewTopicMessage message) {
        log.info("接收到的消息：" + message.toString());
        UserQuery userQuery = new UserQuery();
        userQuery.setId(message.getCreatorUserId());
        UserPO userPO = userService.get(userQuery);


        UserFollowRelateQuery userFollowRelateQuery = new UserFollowRelateQuery();
        userFollowRelateQuery.setFollowedUserId(message.getCreatorUserId());
        List<UserFollowRelatePO> list = userFollowRelateService.list(userFollowRelateQuery);

        if (CollUtil.isNotEmpty(list)) {
            List<NoticePO> noticePOList = new ArrayList<>();

            list.forEach(userFollowRelatePO -> {
                TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());
                Template template = engine.getTemplate(NoticeConstants.NOTIFY_FANS_NEW_TOPIC_NOTICE);
                String render = template.render(Dict.create()
                        .set("userId", message.getCreatorUserId())
                        .set("topicId", message.getTopicId())
                        .set("nickName", userPO.getNickname()));

                NoticePO noticePO = new NoticePO();
                noticePO.setContent(render);
                noticePO.setImage("/image/notice.png");
                noticePO.setUserId(userFollowRelatePO.getFollowUserId());
                noticePO.setCreateTime(new Date());
                noticePO.setCreateBy(UserConstants.SYSTEM_USER_ID);
                noticePO.setCreateUsername(UserConstants.SYSTEM_USER_NAME);
                noticePOList.add(noticePO);
            });

            noticeService.addBatch(noticePOList);
        }

    }

    @RabbitListener(queues = "notify.user.new.fan.queue")
    public void notifyUserNewFan(NotifyNewFanMessage message) {
        log.info("接收到的消息：" + message.toString());
        Assert.isTrue(message.getFollowedUserId() != null, "被关注用户ID不能为空");
        Assert.isTrue(message.getFollowUserId() != null, "发起关注用户ID不能为空");
        UserQuery userQuery = new UserQuery();
        userQuery.setId(message.getFollowUserId());
        UserPO userPO = userService.get(userQuery);
        if (userPO != null) {

            TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());
            Template template = engine.getTemplate(NoticeConstants.NEW_FOLLOWER_NOTICE);
            String render = template.render(Dict.create()
                    .set("userId", message.getFollowUserId())
                    .set("nickName", userPO.getNickname()));

            NoticePO noticePO = new NoticePO();
            noticePO.setCreateUsername(UserConstants.SYSTEM_USER_NAME);
            noticePO.setCreateTime(new Date());
            noticePO.setCreateBy(UserConstants.SYSTEM_USER_ID);
            noticePO.setUserId(message.getFollowedUserId());
            noticePO.setImage(userPO.getAvatar());
            noticePO.setContent(render);

            noticeService.add(noticePO);
        }
    }

    @RabbitListener(queues = "notify.user.reduce.fan.queue")
    public void notifyUserReduceFan(NotifyReduceFanMessage message) {
        log.info("接收到的消息：" + message.toString());
        Assert.isTrue(message.getFollowedUserId() != null, "被关注用户ID不能为空");
        Assert.isTrue(message.getFollowUserId() != null, "发起关注用户ID不能为空");
        UserQuery userQuery = new UserQuery();
        userQuery.setId(message.getFollowUserId());
        UserPO userPO = userService.get(userQuery);
        if (userPO != null) {

            TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());
            Template template = engine.getTemplate(NoticeConstants.REDUCE_FOLLOWER_NOTICE);
            String render = template.render(Dict.create()
                    .set("userId", message.getFollowUserId())
                    .set("nickName", userPO.getNickname()));

            NoticePO noticePO = new NoticePO();
            noticePO.setCreateUsername(UserConstants.SYSTEM_USER_NAME);
            noticePO.setCreateTime(new Date());
            noticePO.setCreateBy(UserConstants.SYSTEM_USER_ID);
            noticePO.setUserId(message.getFollowedUserId());
            noticePO.setImage(userPO.getAvatar());
            noticePO.setContent(render);

            noticeService.add(noticePO);
        }
    }


    @RabbitListener(queues = {"notify.topic.like.queue"})
    public void notifyTopicLike(NotifyLikeTopicMessage message) {
        log.info("接收到的消息：" + message.toString());
        Assert.isTrue(message.getTopicId() != null, "topicId 不能为空");
        Assert.isTrue(message.getUserId() != null, "userId 不能为空");

        TopicPO topicPO = topicService.get(message.getTopicId());
        if (topicPO != null) {
            NoticePO noticePO = new NoticePO();
            noticePO.setCreateUsername(UserConstants.SYSTEM_USER_NAME);
            noticePO.setCreateTime(new Date());
            noticePO.setCreateBy(UserConstants.SYSTEM_USER_ID);
            noticePO.setUserId(topicPO.getUserId());

            UserQuery userQuery = new UserQuery();
            userQuery.setId(message.getUserId());
            UserPO userPO = userService.get(userQuery);
            if (userPO != null) {
                noticePO.setImage(userPO.getAvatar());
                TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());
                Template template = engine.getTemplate(NoticeConstants.TOPIC_LIKE_NOTICE);
                String render = template.render(Dict.create()
                        .set("userId", message.getUserId())
                        .set("topicId", message.getTopicId())
                        .set("nickName",userPO.getNickname()));
                noticePO.setContent(render);
            }

            noticeService.add(noticePO);
        }

    }


    @RabbitListener(queues = {"notify.topic.unlike.queue"})
    public void notifyTopicUnLike(NotifyUnLikeTopicMessage message) {
        log.info("接收到的消息：" + message.toString());
        Assert.isTrue(message.getTopicId() != null, "topicId 不能为空");
        Assert.isTrue(message.getUserId() != null, "userId 不能为空");

        TopicPO topicPO = topicService.get(message.getTopicId());
        if (topicPO != null) {

            NoticePO noticePO = new NoticePO();
            noticePO.setCreateUsername(UserConstants.SYSTEM_USER_NAME);
            noticePO.setCreateTime(new Date());
            noticePO.setCreateBy(UserConstants.SYSTEM_USER_ID);
            noticePO.setUserId(topicPO.getUserId());

            UserQuery userQuery = new UserQuery();
            userQuery.setId(message.getUserId());
            UserPO userPO = userService.get(userQuery);
            if (userPO != null) {
                noticePO.setImage(userPO.getAvatar());

                TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());
                Template template = engine.getTemplate(NoticeConstants.TOPIC_UNLIKE_NOTICE);
                String render = template.render(Dict.create()
                        .set("userId", message.getUserId())
                        .set("topicId", message.getTopicId())
                        .set("nickName",userPO.getNickname()));

                noticePO.setContent(render);
            }

            noticeService.add(noticePO);
        }
    }

    @RabbitListener(queues = {"notify.topic.collect.queue"})
    public void notifyTopicCollect(NotifyCollectTopicMessage message) {
        log.info("接收到的消息：" + message.toString());
        Assert.isTrue(message.getTopicId() != null, "topicId 不能为空");
        Assert.isTrue(message.getUserId() != null, "userId 不能为空");

        TopicPO topicPO = topicService.get(message.getTopicId());
        if (topicPO != null) {
            UserQuery userQuery = new UserQuery();
            userQuery.setId(message.getUserId());
            UserPO userPO = userService.get(userQuery);
            if(userPO != null){
                NoticePO noticePO = new NoticePO();
                noticePO.setCreateUsername(UserConstants.SYSTEM_USER_NAME);
                noticePO.setCreateTime(new Date());
                noticePO.setCreateBy(UserConstants.SYSTEM_USER_ID);
                noticePO.setUserId(topicPO.getUserId());
                noticePO.setImage(userPO.getAvatar());
                TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());
                Template template = engine.getTemplate(NoticeConstants.TOPIC_COLLECT_NOTICE);
                String render = template.render(Dict.create()
                        .set("userId", message.getUserId())
                        .set("topicId", message.getTopicId())
                        .set("nickName",userPO.getNickname()));
                noticePO.setContent(render);
                noticeService.add(noticePO);
            }
        }
    }

    @RabbitListener(queues = {"notify.topic.uncollect.queue"})
    public void notifyTopicUnCollect(NotifyUnCollectTopicMessage message) {
        log.info("接收到的消息：" + message.toString());
        Assert.isTrue(message.getTopicId() != null, "topicId 不能为空");
        Assert.isTrue(message.getUserId() != null, "userId 不能为空");
        TopicPO topicPO = topicService.get(message.getTopicId());
        if (topicPO != null) {
            UserQuery userQuery = new UserQuery();
            userQuery.setId(message.getUserId());
            UserPO userPO = userService.get(userQuery);
            if(userPO != null){
                NoticePO noticePO = new NoticePO();
                noticePO.setCreateUsername(UserConstants.SYSTEM_USER_NAME);
                noticePO.setCreateTime(new Date());
                noticePO.setCreateBy(UserConstants.SYSTEM_USER_ID);
                noticePO.setUserId(topicPO.getUserId());
                noticePO.setImage(userPO.getAvatar());
                TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());
                Template template = engine.getTemplate(NoticeConstants.TOPIC_UNCOLLECT_NOTICE);
                String render = template.render(Dict.create()
                        .set("userId", message.getUserId())
                        .set("topicId", message.getTopicId())
                        .set("nickName",userPO.getNickname()));
                noticePO.setContent(render);
                noticeService.add(noticePO);
            }
        }
    }


}
