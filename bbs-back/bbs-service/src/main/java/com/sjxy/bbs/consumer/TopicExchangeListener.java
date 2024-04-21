package com.sjxy.bbs.consumer;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSONObject;
import com.sjxy.bbs.entity.constants.TopicConstants;
import com.sjxy.bbs.entity.mq.*;
import com.sjxy.bbs.entity.po.TopicPO;
import com.sjxy.bbs.service.MeiliSearchService;
import com.sjxy.bbs.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TopicExchangeListener {

    @Autowired
    private MeiliSearchService meiliSearchService;
    @Autowired
    private TopicService topicService;


    @RabbitListener(queues = {"sync.topic.meilisearch.queue"})
    public void syncTopicToMeilisearch(SyncTopicToMeilisearchMessage message) {
        log.info("接收到的消息：" + message.toString());

        String jsonString = JSONObject.toJSONString(message);

        //判断index是否存在,如果不存在就创建
        if (!meiliSearchService.indexExist(TopicConstants.MEILISEARCH_INDEX_NAME)) {
            meiliSearchService.createIndex(TopicConstants.MEILISEARCH_INDEX_NAME, "id");
        }

        meiliSearchService.addDocument(TopicConstants.MEILISEARCH_INDEX_NAME, jsonString);
    }

    @RabbitListener(queues = {"update.topic.comment.count.queue"})
    public void updateTopicCommentCount(UpdateTopicCommentCountMessage message) {
        log.info("接收到的消息：" + message.toString());
        Assert.isTrue(message.getCount() != null, "count 为空");
        Assert.isTrue(message.getTopicId() != null, "topicId 为空");
        TopicPO topicPO = topicService.get(message.getTopicId());
        topicPO.setCommentCount(topicPO.getCommentCount() + message.getCount());
        topicService.update(topicPO);
    }

    @RabbitListener(queues = {"update.topic.collect.count.queue"})
    public void updateTopicCollectCount(UpdateTopicCollectCountMessage message) {
        log.info("接收到的消息：" + message.toString());
        Assert.isTrue(message.getCount() != null, "count 为空");
        Assert.isTrue(message.getTopicId() != null, "topicId 为空");
        TopicPO topicPO = topicService.get(message.getTopicId());
        topicPO.setCollectCount(topicPO.getCollectCount() + message.getCount());
        topicService.update(topicPO);
    }

    @RabbitListener(queues = {"update.topic.like.count.queue"})
    public void updateTopicLikeCount(UpdateTopicLikeCountMessage message) {
        log.info("接收到的消息：" + message.toString());
        Assert.isTrue(message.getCount() != null, "count 为空");
        Assert.isTrue(message.getTopicId() != null, "topicId 为空");
        TopicPO topicPO = topicService.get(message.getTopicId());
        topicPO.setLikeCount(topicPO.getLikeCount() + message.getCount());
        topicService.update(topicPO);
    }

}
