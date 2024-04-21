package com.sjxy.bbs.service;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.sjxy.bbs.context.WebContext;
import com.sjxy.bbs.entity.bo.UserTokenBO;
import com.sjxy.bbs.entity.constants.RedisConstants;
import com.sjxy.bbs.util.IpUtil;
import com.sjxy.bbs.util.RedisUtil;
import com.sjxy.bbs.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IpUtil ipUtil;

    public Long getUserId() {
        return StpUtil.getLoginIdAsLong();
    }

    public String getUsername() {
        long id = StpUtil.getLoginIdAsLong();
        String result = (String)redisUtil.hGet(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(id));
        UserTokenBO userTokenBO = JSONObject.parseObject(result, UserTokenBO.class);
        return userTokenBO.getUsername();
    }

    public String getNickname(){
        long id = StpUtil.getLoginIdAsLong();
        String result = (String)redisUtil.hGet(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(id));
        UserTokenBO userTokenBO = JSONObject.parseObject(result, UserTokenBO.class);
        return userTokenBO.getNickname();
    }

    public String getIp(){
        return WebContext.getIp();
    }

    public String getIpLocation(){
        return ipUtil.getIpLocation(getIp());
    }
}
