package com.sjxy.bbs.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.sjxy.bbs.context.WebContext;
import com.sjxy.bbs.entity.bo.LoginBO;
import com.sjxy.bbs.entity.bo.UserTagManageBO;
import com.sjxy.bbs.entity.bo.UserTokenBO;
import com.sjxy.bbs.entity.constants.RedisConstants;
import com.sjxy.bbs.entity.constants.TokenConstants;
import com.sjxy.bbs.entity.dto.LoginDTO;
import com.sjxy.bbs.entity.enums.UserStatusEnum;
import com.sjxy.bbs.entity.enums.YesOrNoEnum;
import com.sjxy.bbs.entity.po.PermissionPO;
import com.sjxy.bbs.entity.po.RolePO;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.po.UserTagManagePO;
import com.sjxy.bbs.entity.query.UserQuery;
import com.sjxy.bbs.entity.query.UserTagManageQuery;
import com.sjxy.bbs.service.*;
import com.sjxy.bbs.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserTagManageService userTagManageService;

    @Override
    public LoginBO login(LoginDTO record) {
        //校验传参
        verifyLoginParam(record);

        //校验验证码是否有误
        verifyLoginCaptcha(record);

        //验证登录失败次数
        verifyLoginFailTime();


        UserQuery userQuery = new UserQuery();
        userQuery.setUsername(record.getUsername());
        UserPO userPO = userService.get(userQuery);

        //判断是否找到了该用户名的用户
        Assert.isTrue(userPO != null, "用户名或密码输出错误");

        //判断密码是否正确
        boolean checkpw = BCrypt.checkpw(record.getPassword(), userPO.getPassword());
        Assert.isTrue(checkpw, "用户名或密码输出错误");

        //判断用户是否封禁
        if (userPO.getStatus().equals(UserStatusEnum.FORBIDDEN.getCode())) {
            Date forbiddenEndTime = userPO.getForbiddenEndTime();
            boolean isForbidden = new Date().getTime() < forbiddenEndTime.getTime();

            Assert.isTrue(!isForbidden, "用户已被封禁，解封时间：" + DateUtil.format(forbiddenEndTime, "yyyy-MM-dd HH:mm:ss"));
        }

        //判断用户是否激活邮箱
        Assert.isTrue(userPO.getEmailVerified().equals(YesOrNoEnum.YES.getCode()), "请激活邮箱后再登录");

        //清除用户登录失败次数
        clearLoginFailTime();

        //查询用户的所有角色
        List<RolePO> roleList = roleService.getRolesByUserId(userPO.getId());

        //查询用户的所有权限
        List<PermissionPO> permissionList = new ArrayList<>();
        roleList.forEach(role -> {
            List<PermissionPO> permissions = permissionService.getPermissionsByRoleId(role.getId());
            permissionList.addAll(permissions);
        });

        //查询用户管理的板块
        UserTagManageQuery userTagManageQuery = new UserTagManageQuery();
        userTagManageQuery.setUserId(userPO.getId());
        List<UserTagManageBO> userTagManageList = userTagManageService.list(userTagManageQuery);
        List<Long> tagManageList = userTagManageList.stream().map(UserTagManageBO::getTagId).toList();



        //封装token对象
        UserTokenBO userTokenBO = new UserTokenBO();
        BeanUtils.copyProperties(userPO, userTokenBO);
        userTokenBO.setRoles(roleList);
        userTokenBO.setPermissions(permissionList);
        userTokenBO.setMangedTagIds(tagManageList);

        //添加用户Token信息到redis中
        redisUtil.hPut(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(userPO.getId()), JSONObject.toJSONString(userTokenBO));

        StpUtil.login(userPO.getId());

        LoginBO loginBO = new LoginBO();
        BeanUtils.copyProperties(userPO, loginBO);
        loginBO.setToken(StpUtil.getTokenInfo().getTokenValue());
        loginBO.setRoles(roleList.stream().map(RolePO::getName).collect(Collectors.toList()));
        loginBO.setPermissions(permissionList.stream().map(PermissionPO::getName).collect(Collectors.toList()));
        loginBO.setAvatar(userPO.getAvatar());
        loginBO.setId(userPO.getId());
        loginBO.setMangedTagIds(tagManageList);
        return loginBO;
    }


    private void verifyLoginParam(LoginDTO record) {
        Assert.isTrue(StringUtils.isNotBlank(record.getUsername()), "用户名不能为空");
        Assert.isTrue(StringUtils.isNotBlank(record.getPassword()), "密码不能为空");
        Assert.isTrue(StringUtils.isNotBlank(record.getCaptcha()), "验证码不能为空");
        Assert.isTrue(StringUtils.isNotBlank(record.getCaptchaId()), "验证码不能为空");
    }

    private void verifyLoginCaptcha(LoginDTO record) {
        String captcha = redisUtil.get(String.format(RedisConstants.CAPTCHA_NAME, record.getCaptchaId()));
        Assert.isTrue(captcha != null && captcha.equalsIgnoreCase(record.getCaptcha()), "验证码输入错误");
        redisUtil.delete(String.format(RedisConstants.CAPTCHA_NAME, record.getCaptchaId()));
    }


    public void verifyLoginFailTime() {
        String value = redisUtil.get(String.format(RedisConstants.IP_LOGIN_FAIL_TIMES, WebContext.getIp()));
        if (StrUtil.isNotBlank(value)) {
            int failTimes = Integer.parseInt(value);
            Assert.isTrue(failTimes <= TokenConstants.LOGIN_FAIL_FORBIDDEN_TIMES, "登录失败次数过多，请过半小时后再试");
        }
    }

    private void increaseLoginFailTime() {
        String value = redisUtil.get(String.format(RedisConstants.IP_LOGIN_FAIL_TIMES, WebContext.getIp()));
        if (StrUtil.isNotBlank(value)) {
            int failTimes = Integer.parseInt(value);
            failTimes++;
            redisUtil.setEx(String.format(RedisConstants.IP_LOGIN_FAIL_TIMES, WebContext.getIp()), String.valueOf(failTimes), TokenConstants.LOGIN_FAIL_FORBIDDEN_DURATION, TimeUnit.MINUTES);

        } else {
            int failTimes = 1;
            redisUtil.setEx(String.format(RedisConstants.IP_LOGIN_FAIL_TIMES, WebContext.getIp()), String.valueOf(failTimes), TokenConstants.LOGIN_FAIL_FORBIDDEN_DURATION, TimeUnit.MINUTES);
        }
    }

    private void clearLoginFailTime() {
        redisUtil.delete(String.format(RedisConstants.IP_LOGIN_FAIL_TIMES, WebContext.getIp()));
    }

}
