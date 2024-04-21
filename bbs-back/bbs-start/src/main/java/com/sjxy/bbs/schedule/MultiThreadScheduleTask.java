package com.sjxy.bbs.schedule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.sjxy.bbs.entity.constants.RedisConstants;
import com.sjxy.bbs.entity.enums.UserStatusEnum;
import com.sjxy.bbs.entity.mq.UpdateUserStatusMessage;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.po.UserVerifyCodePO;
import com.sjxy.bbs.entity.query.UserQuery;
import com.sjxy.bbs.service.TopicService;
import com.sjxy.bbs.service.UserService;
import com.sjxy.bbs.service.UserVerifyCodeService;
import com.sjxy.bbs.util.MQUtil;
import com.sjxy.bbs.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
@EnableAsync
@Slf4j
public class MultiThreadScheduleTask {
    @Autowired
    private TopicService topicService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserVerifyCodeService userVerifyCodeService;
    @Autowired
    private UserService userService;
    @Autowired
    private MQUtil mqUtil;

    @Async
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void syncTopicViewCount() {
        //同步帖子查看数
        log.info("开始自动同步帖子查看数~~~~~~");
        Map<Object, Object> increaseMap = redisUtil.hGetAll(RedisConstants.INCREASE_TOPIC_VIEW_COUNT);
        increaseMap.keySet().stream().forEach(key -> {
            String topicIdStr = (String) key;
            Long topicId = Long.valueOf(topicIdStr);
            Object o = redisUtil.hGet(RedisConstants.INCREASE_TOPIC_VIEW_COUNT, topicIdStr);
            Integer count = 0;
            if (o != null) {
                String countStr = (String) o;
                count = Integer.valueOf(countStr);
            }

            topicService.increaseTopicViewCount(topicId, count);
            redisUtil.hDelete(RedisConstants.INCREASE_TOPIC_VIEW_COUNT, topicIdStr);
        });
        log.info("结束自动同步帖子查看数~~~~~~");
    }

    @Async
    @Scheduled(fixedDelay = 15 * 60 * 1000)
    void deleteExpireVerifyCode() {
        log.info("开始删除过期验证码~~~~~~");
        List<UserVerifyCodePO> userVerifyCodePOS = userVerifyCodeService.queryAllExpireCode();
        List<Long> list = userVerifyCodePOS.stream().map(UserVerifyCodePO::getId).toList();
        if (CollUtil.isNotEmpty(list)) {
            userVerifyCodeService.deleteBatch(list);
        }
        log.info("结束删除过期验证码~~~~~~");
    }

    @Async
    @Scheduled(cron = "0 0 0 * * ? ")
    void checkMute() {
        log.info("开始检测今天禁言到期的用户~~~~~~");
        UserQuery userQuery = new UserQuery();
        userQuery.setStatus(UserStatusEnum.MUTE.getCode());
        List<UserPO> list = userService.list(userQuery);
        if (CollUtil.isNotEmpty(list)) {
            list.stream().forEach(userPO -> {
                long between = DateUtil.between(new Date(), userPO.getMuteEndTime(), DateUnit.MS, false);

                //操作24小时内将要过期的用户
                if (between < 0) {

                    //让RabbitMQ通知修改用户状态
                    UpdateUserStatusMessage message = new UpdateUserStatusMessage();
                    message.setUserId(userPO.getId());
                    message.setOldStatus(userPO.getStatus());
                    message.setNewStatus(UserStatusEnum.OK.getCode());
                    mqUtil.sendDelay("DelayExchange.direct", "update.user.status", 1000, message);
                } else if (between < (24 * 60 * 60 * 1000)) {

                    //让RabbitMQ通知修改用户状态
                    UpdateUserStatusMessage message = new UpdateUserStatusMessage();
                    message.setUserId(userPO.getId());
                    message.setOldStatus(userPO.getStatus());
                    message.setNewStatus(UserStatusEnum.OK.getCode());
                    mqUtil.sendDelay("DelayExchange.direct", "update.user.status", (int) between, message);
                }
            });
        }
        log.info("结束检测今天禁言到期的用户~~~~~~");
    }

    @Async
    @Scheduled(cron = "0 0 0 * * ? ")
    void checkForbidden() {
        log.info("开始检测今天封禁到期的用户~~~~~~");
        UserQuery userQuery = new UserQuery();
        userQuery.setStatus(UserStatusEnum.FORBIDDEN.getCode());
        List<UserPO> list = userService.list(userQuery);
        if (CollUtil.isNotEmpty(list)) {
            list.stream().forEach(userPO -> {
                long between = DateUtil.between(new Date(), userPO.getMuteEndTime(), DateUnit.MS, false);

                //操作24小时内将要过期的用户
                if (between < 0) {

                    //让RabbitMQ通知修改用户状态
                    UpdateUserStatusMessage message = new UpdateUserStatusMessage();
                    message.setUserId(userPO.getId());
                    message.setOldStatus(userPO.getStatus());
                    message.setNewStatus(UserStatusEnum.OK.getCode());
                    mqUtil.sendDelay("DelayExchange.direct", "update.user.status", 1000, message);
                } else if (between < (24 * 60 * 60 * 1000)) {

                    //让RabbitMQ通知修改用户状态
                    UpdateUserStatusMessage message = new UpdateUserStatusMessage();
                    message.setUserId(userPO.getId());
                    message.setOldStatus(userPO.getStatus());
                    message.setNewStatus(UserStatusEnum.OK.getCode());
                    mqUtil.sendDelay("DelayExchange.direct", "update.user.status", (int) between, message);
                }
            });
        }
        log.info("结束检测今天封禁到期的用户~~~~~~");
    }
}
