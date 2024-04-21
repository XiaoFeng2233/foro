package com.sjxy.bbs.consumer;

import cn.hutool.core.lang.Assert;
import com.sjxy.bbs.entity.dto.LogDTO;
import com.sjxy.bbs.entity.mq.AddLogMessage;
import com.sjxy.bbs.entity.mq.UpdateUserTopicCountMessage;
import com.sjxy.bbs.entity.po.LogPO;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.query.UserQuery;
import com.sjxy.bbs.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogExchangeListener {
    @Autowired
    private LogService logService;

    @RabbitListener(queues = {"add.log.queue"})
    public void addLog(AddLogMessage message) {
        log.info("接收到的消息：" + message.toString());
        LogPO logPO = new LogPO();
        logPO.setCreateTime(message.getCreateTime());
        logPO.setUserId(message.getUserId());
        logPO.setType(message.getType());
        logPO.setIp(message.getIp());
        logPO.setLocation(message.getLocation());
        logService.add(logPO);
    }
}
