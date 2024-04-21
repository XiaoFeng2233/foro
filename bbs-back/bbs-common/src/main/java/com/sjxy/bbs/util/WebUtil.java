package com.sjxy.bbs.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebUtil {
    @Autowired
    private RedisUtil redisUtil;

    public static String getIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Real-IP");
        Assert.isTrue(StrUtil.isNotBlank(ipAddress), "ip地址获取出错");
        return ipAddress;
    }

}
