package com.sjxy.bbs.config;

import cn.dev33.satoken.stp.StpInterface;
import com.alibaba.fastjson2.JSONObject;
import com.sjxy.bbs.entity.bo.UserTokenBO;
import com.sjxy.bbs.entity.constants.RedisConstants;
import com.sjxy.bbs.entity.po.PermissionPO;
import com.sjxy.bbs.entity.po.RolePO;
import com.sjxy.bbs.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String result = (String) redisUtil.hGet(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(loginId));
        UserTokenBO userTokenBO = JSONObject.parseObject(result, UserTokenBO.class);
        return userTokenBO.getPermissions().stream().map(PermissionPO::getName).collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String result = (String) redisUtil.hGet(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(loginId));
        UserTokenBO userTokenBO = JSONObject.parseObject(result, UserTokenBO.class);
        return userTokenBO.getRoles().stream().map(RolePO::getName).collect(Collectors.toList());
    }
}
