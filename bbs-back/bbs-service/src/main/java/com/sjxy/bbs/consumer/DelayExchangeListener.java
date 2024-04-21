package com.sjxy.bbs.consumer;

import com.sjxy.bbs.entity.constants.UserConstants;
import com.sjxy.bbs.entity.enums.YesOrNoEnum;
import com.sjxy.bbs.entity.mq.DeleteRegisterUserMessage;
import com.sjxy.bbs.entity.mq.UpdateUserStatusMessage;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.query.UserQuery;
import com.sjxy.bbs.service.UserService;
import com.sjxy.bbs.service.UserVerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class DelayExchangeListener {

    @Autowired
    private UserService userService;


    @RabbitListener(queues = {"delete.user.queue"})
    public void deleteUserQueue(DeleteRegisterUserMessage message){
        log.info("接收到的消息：" + message.toString());
        UserQuery userQuery = new UserQuery();
        userQuery.setId(message.getUserId());
        UserPO userPO = userService.get(userQuery);

        if(userPO != null){
            //判断用户当前是否已经验证过邮箱,如果没有验证则直接删除账号
            if (!userPO.getEmailVerified().equals(YesOrNoEnum.YES.getCode())) {
                userService.delete(userPO.getId());
            }
        }
    }

    @RabbitListener(queues = {"update.user.status.queue"})
    public void cancelUserMute(UpdateUserStatusMessage message){
        log.info("接收到的消息：" + message.toString());
        UserQuery userQuery = new UserQuery();
        userQuery.setId(message.getUserId());
        UserPO userPO = userService.get(userQuery);
        if(userPO != null){
            if(userPO.getStatus().equals(message.getOldStatus())){
                userPO.setStatus(message.getNewStatus());
                userPO.setUpdateUsername(UserConstants.SYSTEM_USER_NAME);
                userPO.setUpdateTime(new Date());
                userPO.setUpdateBy(UserConstants.SYSTEM_USER_ID);
                userService.update(userPO);
            }
        }

    }
}
