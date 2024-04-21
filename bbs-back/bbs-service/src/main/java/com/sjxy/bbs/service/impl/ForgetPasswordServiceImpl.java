package com.sjxy.bbs.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.sjxy.bbs.entity.bo.ForgetPasswordInfoBO;
import com.sjxy.bbs.entity.constants.RedisConstants;
import com.sjxy.bbs.entity.constants.UserConstants;
import com.sjxy.bbs.entity.dto.ForgetPasswordDTO;
import com.sjxy.bbs.entity.dto.ResetPasswordDTO;
import com.sjxy.bbs.entity.dto.UserVerifyCodeAddDTO;
import com.sjxy.bbs.entity.enums.UserVerifyCodeEnum;
import com.sjxy.bbs.entity.mq.ForgetPasswordEmailMessage;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.po.UserVerifyCodePO;
import com.sjxy.bbs.entity.query.UserQuery;
import com.sjxy.bbs.entity.query.UserVerifyCodeQuery;
import com.sjxy.bbs.service.BaseService;
import com.sjxy.bbs.service.ForgetPasswordService;
import com.sjxy.bbs.service.UserService;
import com.sjxy.bbs.service.UserVerifyCodeService;
import com.sjxy.bbs.util.MQUtil;
import com.sjxy.bbs.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ForgetPasswordServiceImpl extends BaseService implements ForgetPasswordService {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserVerifyCodeService userVerifyCodeService;
    @Autowired
    private MQUtil mqUtil;


    @Override
    public void forgetPassword(ForgetPasswordDTO record) {
        //验证传参
        verifyParam(record);

        //验证验证码
        verifyCaptcha(record);

        UserQuery userQuery = new UserQuery();
        userQuery.setEmail(record.getEmail());
        UserPO userPO = userService.get(userQuery);
        Assert.isTrue(userPO != null, "邮箱输入有误");

        //向数据表中插入该验证信息
        UserVerifyCodeAddDTO userVerify = new UserVerifyCodeAddDTO();
        userVerify.setType(UserVerifyCodeEnum.FORGET_PASSWORD.getCode());
        userVerify.setUserId(userPO.getId());
        userVerify.setToken(IdUtil.simpleUUID());
        userVerify.setCode(RandomUtil.randomString(128));
        userVerify.setExpireTime(DateUtil.offsetMinute(new Date(), UserConstants.USER_EMAIL_ACTIVE_TIME));
        userVerifyCodeService.add(userVerify);

        //让Rabbitmq发送邮箱信息

        ForgetPasswordEmailMessage message = new ForgetPasswordEmailMessage();
        message.setDestEmail(userPO.getEmail());
        message.setUsername(userPO.getUsername());
        message.setCode(userVerify.getCode());
        message.setToken(userVerify.getToken());
        mqUtil.send("EmailExchange.direct","send.forget-password.email",message);


    }

    @Override
    public ForgetPasswordInfoBO getForgetPasswordInfo(String token, String code) {
        Assert.isTrue(StrUtil.isNotBlank(token), "token不能为空");
        Assert.isTrue(StrUtil.isNotBlank(code), "code不能为空");

        UserVerifyCodeQuery query = new UserVerifyCodeQuery();
        query.setToken(token);
        query.setCode(code);
        query.setType(UserVerifyCodeEnum.FORGET_PASSWORD.getCode());
        UserVerifyCodePO po = userVerifyCodeService.get(query);
        Assert.isTrue(po != null, "非法请求");

        Assert.isTrue(po.getExpireTime().getTime() > new Date().getTime(),"非法请求");

        UserPO user = po.getUser();
        ForgetPasswordInfoBO infoBO = new ForgetPasswordInfoBO();
        infoBO.setUsername(user.getUsername());
        infoBO.setEmail(user.getEmail());
        infoBO.setUserId(user.getId());
        return infoBO;
    }

    @Override
    public void resetPassword(ResetPasswordDTO record) {
        Assert.isTrue(record != null, "参数不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getToken()), "token不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getCode()), "code不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getPassword()), "密码不能为空");

        UserVerifyCodeQuery query = new UserVerifyCodeQuery();
        query.setToken(record.getToken());
        query.setCode(record.getCode());
        query.setType(UserVerifyCodeEnum.FORGET_PASSWORD.getCode());
        UserVerifyCodePO po = userVerifyCodeService.get(query);
        Assert.isTrue(po != null, "非法请求");
        Assert.isTrue(po.getExpireTime().getTime() > new Date().getTime(),"非法请求");

        UserQuery userQuery = new UserQuery();
        userQuery.setId(po.getUserId());
        UserPO userPO = userService.get(userQuery);
        Assert.isTrue(userPO != null, "该用户不存在");

        String encodedPassword = BCrypt.hashpw(record.getPassword(), BCrypt.gensalt(UserConstants.BCRYPT_STRENGTH));
        userPO.setPassword(encodedPassword);
        userService.update(userPO);

        StpUtil.kickout(userPO.getId());

    }


    public void verifyParam(ForgetPasswordDTO record) {
        Assert.isTrue(record != null, "参数不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getEmail()), "邮箱不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getCaptchaId()), "验证码ID不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getCaptcha()), "验证码不能为空");
    }

    public void verifyCaptcha(ForgetPasswordDTO record) {
        String captcha = redisUtil.get(String.format(RedisConstants.CAPTCHA_NAME, record.getCaptchaId()));
        Assert.isTrue(captcha != null && captcha.equalsIgnoreCase(record.getCaptcha()), "验证码输入错误");
        redisUtil.delete(String.format(RedisConstants.CAPTCHA_NAME, record.getCaptchaId()));
    }
}
