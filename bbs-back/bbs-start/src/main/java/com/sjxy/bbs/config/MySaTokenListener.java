package com.sjxy.bbs.config;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import com.sjxy.bbs.context.WebContext;
import com.sjxy.bbs.entity.dto.LogDTO;
import com.sjxy.bbs.entity.enums.LogTypeEnum;
import com.sjxy.bbs.service.LogService;
import com.sjxy.bbs.service.UserService;
import com.sjxy.bbs.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MySaTokenListener implements SaTokenListener {
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;
    @Autowired
    private IpUtil ipUtil;


    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        log.info("账号 {} 登录成功 (loginType={}), 会话凭证 token={}", loginId, loginType, tokenValue);
        //添加登录日志
        LogDTO logDTO = new LogDTO();
        logDTO.setIp(WebContext.getIp());
        logDTO.setUserId((Long)loginId);
        logDTO.setType(LogTypeEnum.LOGIN.getCode());
        logDTO.setLocation(ipUtil.getIpLocation(WebContext.getIp()));
        logService.add(logDTO);
    }

    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        log.info("账号 {} 注销登录 (loginType={}), 会话凭证 token={}", loginId, loginType, tokenValue);
        //添加退出日志
        LogDTO logDTO = new LogDTO();
        logDTO.setIp(WebContext.getIp());
        String id = (String)loginId;
        logDTO.setUserId(Long.parseLong(id));
        logDTO.setType(LogTypeEnum.LOGOUT.getCode());
        logDTO.setLocation(ipUtil.getIpLocation(WebContext.getIp()));
        logService.add(logDTO);
    }

    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        log.info("账号 {} 被踢下线 (loginType={}), 会话凭证 token={}", loginId, loginType, tokenValue);
    }

    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {

    }

    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {

    }

    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {

    }

    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {

    }

    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {

    }

    @Override
    public void doCreateSession(String id) {

    }

    @Override
    public void doLogoutSession(String id) {

    }

    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {

    }
}
