package com.sjxy.bbs.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.sjxy.bbs.entity.mq.BaseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MQUtil {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String exchange, String key, BaseMessage message) {
        message.setSendTime(new Date());
        message.setKey(IdUtil.simpleUUID());
        rabbitTemplate.convertAndSend(exchange, key, message);
        log.info("发送消息：" + message.toString());
    }

    public void sendDelay(String exchange, String key, Integer mills, BaseMessage message) {
        message.setSendTime(new Date());
        message.setKey(IdUtil.simpleUUID());
        rabbitTemplate.convertAndSend(exchange, key, message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay(mills);
                return message;
            }
        });

        log.info("发送消息：" + message.toString());
    }
}
