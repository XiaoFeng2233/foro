package com.sjxy.bbs.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * 用户交换机 专门用于处理用户相关的消息
     */
    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange("UserExchange.direct");
    }

    /**
     * 帖子交换机 专门用于处理帖子相关的消息
     */
    @Bean
    public DirectExchange topicExchange() {
        return new DirectExchange("TopicExchange.direct");
    }

    /**
     * 邮件交换机 专门用于处理邮件相关的消息
     */
    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange("EmailExchange.direct");

    }

    /**
     * 通知交换机 专门用于处理通知相关的消息
     */
    @Bean
    public DirectExchange noticeExchange() {
        return new DirectExchange("NoticeExchange.direct");

    }

    /**
     * 延时交换机
     */
    @Bean
    public DirectExchange delayExchange() {
        return ExchangeBuilder
                .directExchange("DelayExchange.direct")
                .delayed()
                .durable(true)
                .build();
    }

    /**
     * 日志交换机 专门用于处理日志相关的消息
     */
    @Bean
    public DirectExchange logExchange() {
        return new DirectExchange("LogExchange.direct");
    }

    @Bean
    public Queue addLogQueue() {
        return new Queue("add.log.queue");
    }

    @Bean
    public Binding addLogQueueBinding() {
        return BindingBuilder.bind(addLogQueue()).to(logExchange()).with("add.log");
    }


    @Bean
    public Queue deleteUserQueue() {
        return new Queue("delete.user.queue");
    }

    @Bean
    public Binding deleteUserQueueBinding() {
        return BindingBuilder.bind(deleteUserQueue()).to(delayExchange()).with("delete.user");
    }

    @Bean
    public Queue updateUserStatusQueue() {
        return new Queue("update.user.status.queue");
    }

    @Bean
    public Binding updateUserStatusBinding() {
        return BindingBuilder.bind(updateUserStatusQueue()).to(delayExchange()).with("update.user.status");
    }

    @Bean
    public Queue sendRegisterEmailQueue() {
        return new Queue("send.register.email.queue");
    }

    @Bean
    public Binding sendRegisterEmailBinding() {
        return BindingBuilder.bind(sendRegisterEmailQueue()).to(emailExchange()).with("send.register.email");
    }

    @Bean
    public Queue sendForgetPasswordEmailQueue() {
        return new Queue("send.forget-password.email.queue");
    }

    @Bean
    public Binding sendForgetPasswordEmailBinding() {
        return BindingBuilder.bind(sendForgetPasswordEmailQueue()).to(emailExchange()).with("send.forget-password.email");
    }

    @Bean
    public Queue syncTopicMeilisearchQueue() {
        return new Queue("sync.topic.meilisearch.queue");
    }

    @Bean
    public Binding syncTopicMeilisearchBinding() {
        return BindingBuilder.bind(syncTopicMeilisearchQueue()).to(topicExchange()).with("sync.topic.meilisearch");
    }

    @Bean
    public Queue updateUserTopicCountQueue() {
        return new Queue("update.user.topic.count.queue");
    }

    @Bean
    public Binding updateUserTopicCountBinding() {
        return BindingBuilder.bind(updateUserTopicCountQueue()).to(userExchange()).with("update.user.topic.count");
    }

    @Bean
    public Queue notifyFansNewTopicQueue() {
        return new Queue("notify.fans.new.topic.queue");
    }

    @Bean
    public Binding notifyFansNewTopicBinding() {
        return BindingBuilder.bind(notifyFansNewTopicQueue()).to(noticeExchange()).with("notify.fans.new.topic");
    }

    @Bean
    public Queue updateUserCommentCountQueue() {
        return new Queue("update.user.comment.count.queue");
    }

    @Bean
    public Binding updateUserCommentCountBinding() {
        return BindingBuilder.bind(updateUserCommentCountQueue()).to(userExchange()).with("update.user.comment.count");
    }

    @Bean
    public Queue updateTopicCommentCountQueue() {
        return new Queue("update.topic.comment.count.queue");
    }

    @Bean
    public Binding updateTopicCommentCountBinding() {
        return BindingBuilder.bind(updateTopicCommentCountQueue()).to(topicExchange()).with("update.topic.comment.count");
    }

    @Bean
    public Queue updateUserCollectCountQueue() {
        return new Queue("update.user.collect.count.queue");
    }

    @Bean
    public Binding updateUserCollectCountBinding() {
        return BindingBuilder.bind(updateUserCollectCountQueue()).to(userExchange()).with("update.user.collect.count");
    }

    @Bean
    public Queue updateTopicCollectCountQueue() {
        return new Queue("update.topic.collect.count.queue");
    }

    @Bean
    public Binding updateTopicCollectCountBinding() {
        return BindingBuilder.bind(updateTopicCollectCountQueue()).to(topicExchange()).with("update.topic.collect.count");
    }

    @Bean
    public Queue updateTopicLikeCountQueue() {
        return new Queue("update.topic.like.count.queue");
    }

    @Bean
    public Binding updateTopicLikeCountBinding() {
        return BindingBuilder.bind(updateTopicLikeCountQueue()).to(topicExchange()).with("update.topic.like.count");
    }

    @Bean
    public Queue notifyUserNewFanQueue(){
        return new Queue("notify.user.new.fan.queue");
    }

    @Bean
    public Binding notifyUserNewFanBinding(){
        return BindingBuilder.bind(notifyUserNewFanQueue()).to(noticeExchange()).with("notify.user.new.fan");
    }

    @Bean
    public Queue notifyUserReduceFanQueue(){
        return new Queue("notify.user.reduce.fan.queue");
    }

    @Bean
    public Binding notifyUserReduceFanBinding(){
        return BindingBuilder.bind(notifyUserReduceFanQueue()).to(noticeExchange()).with("notify.user.reduce.fan");
    }

    @Bean
    public Queue notifyTopicLikeQueue(){
        return new Queue("notify.topic.like.queue");
    }

    @Bean
    public Binding notifyTopicLikeBinding(){
        return BindingBuilder.bind(notifyTopicLikeQueue()).to(noticeExchange()).with("notify.topic.like");
    }

    @Bean
    public Queue notifyTopicUnLikeQueue(){
        return new Queue("notify.topic.unlike.queue");
    }
    @Bean
    public Binding notifyTopicUnLikeBinding(){
        return BindingBuilder.bind(notifyTopicUnLikeQueue()).to(noticeExchange()).with("notify.topic.unlike");
    }

    @Bean
    public Queue notifyTopicCollectQueue(){
        return new Queue("notify.topic.collect.queue");
    }

    @Bean
    public Binding notifyTopicCollectBinding() {
        return BindingBuilder.bind(notifyTopicCollectQueue()).to(noticeExchange()).with("notify.topic.collect");
    }

    @Bean
    public Queue notifyTopicUnCollectQueue(){
        return new Queue("notify.topic.uncollect.queue");
    }

    @Bean
    public Binding notifyTopicUnCollectBinding() {
        return BindingBuilder.bind(notifyTopicUnCollectQueue()).to(noticeExchange()).with("notify.topic.uncollect");
    }
}
