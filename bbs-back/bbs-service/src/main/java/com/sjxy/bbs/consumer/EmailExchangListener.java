package com.sjxy.bbs.consumer;

import com.sjxy.bbs.entity.mq.ForgetPasswordEmailMessage;
import com.sjxy.bbs.entity.mq.RegisterEmailMessage;
import com.sjxy.bbs.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailExchangListener {
    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = {"send.register.email.queue"})
    public void sendRegisterEmail(RegisterEmailMessage message){
        log.info("接收到的消息：" + message.toString());
        emailService.sendRegisterMail(message.getDestEmail(), message.getUsername(), message.getToken(), message.getCode());
    }

    @RabbitListener(queues = {"send.forget-password.email.queue"})
    public void sendForgetPasswordEmail(ForgetPasswordEmailMessage message){
        log.info("接收到的消息：" + message.toString());
        emailService.sendForgetPasswordMail(message.getDestEmail(), message.getUsername(), message.getToken(), message.getCode());
    }

}
