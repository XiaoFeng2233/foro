package com.sjxy.bbs.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.sjxy.bbs.aop.RegisterAnnotation;
import com.sjxy.bbs.entity.bo.UserConfigBO;
import com.sjxy.bbs.entity.constants.MQConstants;
import com.sjxy.bbs.entity.constants.RedisConstants;
import com.sjxy.bbs.entity.constants.UserConstants;
import com.sjxy.bbs.entity.dto.RegisterDTO;
import com.sjxy.bbs.entity.dto.UserRoleRelateAddDTO;
import com.sjxy.bbs.entity.dto.UserVerifyCodeAddDTO;
import com.sjxy.bbs.entity.enums.UserStatusEnum;
import com.sjxy.bbs.entity.enums.UserVerifyCodeEnum;
import com.sjxy.bbs.entity.enums.YesOrNoEnum;
import com.sjxy.bbs.entity.mq.DeleteRegisterUserMessage;
import com.sjxy.bbs.entity.mq.RegisterEmailMessage;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.po.UserVerifyCodePO;
import com.sjxy.bbs.entity.query.UserQuery;
import com.sjxy.bbs.entity.query.UserVerifyCodeQuery;
import com.sjxy.bbs.service.*;
import com.sjxy.bbs.util.MQUtil;
import com.sjxy.bbs.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ConfigService configService;
    @Autowired
    private UserVerifyCodeService userVerifyCodeService;
    @Autowired
    private MQUtil mqUtil;
    @Autowired
    private UserRoleRelateService userRoleRelateService;

    @Override
    @Transactional
    @RegisterAnnotation(checkOpenRegister = true)
    public Long register(RegisterDTO record) {

        //校验传参
        verifyRegisterParam(record);

        //校验验证码是否有误
        verifyRegisterCaptcha(record);

        //根据用户名和邮箱查找用户,判断是否已存在
        UserQuery userQuery = new UserQuery();
        userQuery.setEmail(record.getEmail());
        userQuery.setUsername(record.getUsername());
        UserPO existUser = userService.get(userQuery);
        Assert.isTrue(existUser == null, "用户名或邮箱已注册");

        //禁止将用户名设置为SYSTEM
        Assert.isTrue(!record.getUsername().equals(UserConstants.SYSTEM_USER_NAME), "非法操作");

        UserConfigBO userConfig = configService.getUserConfig();

        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(record, userPO);

        //将用户传来的密码进行加密
        String encodedPassword = BCrypt.hashpw(record.getPassword(), BCrypt.gensalt(UserConstants.BCRYPT_STRENGTH));
        userPO.setPassword(encodedPassword);
        userPO.setScore(UserConstants.DEFAULT_SCORE);
        userPO.setCommentCount(UserConstants.DEFAULT_COMMENT_COUNT);
        userPO.setTopicCount(UserConstants.DEFAULT_TOPIC_COUNT);
        userPO.setFansCount(UserConstants.DEFAULT_FANS_COUNT);
        userPO.setFollowCount(UserConstants.DEFAULT_FOLLOW_COUNT);
        userPO.setCollectCount(UserConstants.DEFAULT_COLLECT_COUNT);
        userPO.setForbiddenEndTime(new Date());
        userPO.setNickname(record.getUsername());
        userPO.setBackgroundImage(userConfig.getDefaultBackgroundImage());
        userPO.setAvatar(userConfig.getDefaultAvatar());
        userPO.setStatus(UserStatusEnum.FORBIDDEN.getCode());
        userPO.setEmailVerified(YesOrNoEnum.NO.getCode());
        userPO.setCreateTime(new Date());
        userPO.setCreateBy(UserConstants.SYSTEM_USER_ID);
        userPO.setCreateUsername(UserConstants.SYSTEM_USER_NAME);
        Integer result = userService.add(userPO);
        Assert.isTrue(result != null && result > 0, "用户注册失败,请联系网站管理员");

        //根据用户名从数据库查出刚刚创建的用户数据
        userQuery = new UserQuery();
        userQuery.setUsername(record.getUsername());
        userPO = userService.get(userQuery);
        Assert.isTrue(userPO != null, "用户注册失败,请联系网站管理员");


        //创建用户激活验证码
        UserVerifyCodeAddDTO userVerify = new UserVerifyCodeAddDTO();
        userVerify.setType(UserVerifyCodeEnum.REGISTER.getCode());
        userVerify.setUserId(userPO.getId());
        userVerify.setToken(IdUtil.simpleUUID());
        userVerify.setCode(RandomUtil.randomString(128));
        userVerify.setExpireTime(DateUtil.offsetMinute(new Date(), UserConstants.USER_EMAIL_ACTIVE_TIME));
        userVerifyCodeService.add(userVerify);

        //让MQ发送验证码给客户
        RegisterEmailMessage message = new RegisterEmailMessage();
        message.setUsername(userPO.getUsername());
        message.setDestEmail(userPO.getEmail());
        message.setToken(userVerify.getToken());
        message.setCode(userVerify.getCode());
        mqUtil.send("EmailExchange.direct", "send.register.email", message);

        //让MQ 15分钟后检测用户是否激活账号,如果没激活直接删掉账号
        DeleteRegisterUserMessage deleteRegisterUserMessage = new DeleteRegisterUserMessage();
        deleteRegisterUserMessage.setEmail(userPO.getEmail());
        deleteRegisterUserMessage.setExpireTime(DateUtil.offsetMinute(userPO.getCreateTime(), UserConstants.USER_EMAIL_ACTIVE_TIME));
        deleteRegisterUserMessage.setUserId(userPO.getId());
        deleteRegisterUserMessage.setUsername(userPO.getUsername());
        mqUtil.sendDelay("DelayExchange.direct", "delete.user", UserConstants.USER_EMAIL_ACTIVE_TIME * 60 * 1000, deleteRegisterUserMessage);

        return userPO.getId();
    }

    @Override
    @RegisterAnnotation(checkOpenRegister = true)
    @Transactional
    public Boolean active(String token, String code) {
        Assert.isTrue(StrUtil.isNotBlank(token), "token不得为空");
        Assert.isTrue(StrUtil.isNotBlank(code), "code不得为空");
        UserVerifyCodeQuery query = new UserVerifyCodeQuery();
        query.setCode(code);
        query.setToken(token);

        UserVerifyCodePO userVerifyCodePO = userVerifyCodeService.get(query);
        if (userVerifyCodePO == null) {
            return false;
        }

        if (!(userVerifyCodePO.getExpireTime().getTime() > new Date().getTime())) {
            return false;
        }

        UserQuery userQuery = new UserQuery();
        userQuery.setId(userVerifyCodePO.getUserId());
        UserPO userPO = userService.get(userQuery);

        if (userPO == null) {
            return false;
        }

        userPO.setEmailVerified(YesOrNoEnum.YES.getCode());
        userPO.setStatus(UserStatusEnum.OK.getCode());
        userService.update(userPO);

        userVerifyCodeService.delete(userVerifyCodePO.getId());
        userVerifyCodePO.setChecked(YesOrNoEnum.YES.getCode());
        userVerifyCodeService.update(userVerifyCodePO);

        UserRoleRelateAddDTO relateAddDTO = new UserRoleRelateAddDTO();
        relateAddDTO.setRoleId(3L);
        relateAddDTO.setUserId(userPO.getId());
        userRoleRelateService.add(relateAddDTO);

        return true;
    }

    private void verifyRegisterParam(RegisterDTO record) {
        Assert.isTrue(record != null, "参数不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getEmail()), "邮箱不得为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getUsername()), "用户名不得为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getPassword()), "密码不得为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getCaptcha()), "验证码不得为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getCaptchaId()), "验证码不得为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getCaptchaId()), "验证码不得为空");
        Assert.isTrue(Validator.isEmail(record.getEmail()), "邮箱格式有误");
    }

    private void verifyRegisterCaptcha(RegisterDTO record) {
        String captcha = redisUtil.get(String.format(RedisConstants.CAPTCHA_NAME, record.getCaptchaId()));
        Assert.isTrue(captcha != null && captcha.equalsIgnoreCase(record.getCaptcha()), "验证码输入错误");
        redisUtil.delete(String.format(RedisConstants.CAPTCHA_NAME, record.getCaptchaId()));
    }
}
