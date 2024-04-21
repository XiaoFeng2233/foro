package com.sjxy.bbs.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.LogBO;
import com.sjxy.bbs.entity.constants.UserConstants;
import com.sjxy.bbs.entity.dto.LogDTO;
import com.sjxy.bbs.entity.enums.LogTypeEnum;
import com.sjxy.bbs.entity.po.LogPO;
import com.sjxy.bbs.mapper.LogMapper;
import com.sjxy.bbs.service.BaseService;
import com.sjxy.bbs.service.LogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl extends BaseService implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public void add(LogDTO record) {
        verifyAddParam(record);
        LogPO logPO = new LogPO();
        BeanUtils.copyProperties(record, logPO);
        logPO.setCreateBy(-1L);
        logPO.setCreateTime(new Date());
        logPO.setCreateUsername("SYSTEM");
        logMapper.insert(logPO);
    }

    @Override
    public void add(LogPO logPO) {
        logPO.setCreateBy(UserConstants.SYSTEM_USER_ID);
        logPO.setCreateUsername(UserConstants.SYSTEM_USER_NAME);
        logMapper.insert(logPO);
    }

    @Override
    public Page<LogBO> getUserLoginLog(Integer pageNum, Integer pageSize) {
        Page<LogPO> page = new Page<>(pageNum, pageSize);
        Long userId = getUserId();
        QueryWrapper<LogPO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("type", LogTypeEnum.LOGIN.getCode());
        wrapper.orderByDesc("create_time");
        Page<LogPO> logPOPage = logMapper.selectPage(page,wrapper);

        Page<LogBO> logBOPage = new Page<>();

        BeanUtils.copyProperties(logPOPage,logBOPage);

        List<LogPO> logpoList = logPOPage.getRecords();
        List<LogBO> list = logpoList.stream().map(logPO -> {
            LogBO logBO = new LogBO();
            BeanUtils.copyProperties(logPO, logBO);
            logBO.setIp(DesensitizedUtil.ipv4(logPO.getIp()));
            return logBO;
        }).toList();
        logBOPage.setRecords(list);

        return logBOPage;
    }

    @Override
    public LogBO getLastLog(Long userId, LogTypeEnum logTypeEnum) {
        QueryWrapper<LogPO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("type",logTypeEnum.getCode());
        wrapper.orderByDesc("create_time");
        wrapper.last("limit 1");
        LogPO logPO = logMapper.selectOne(wrapper);
        if(logPO != null){
            LogBO logBO = new LogBO();
            BeanUtils.copyProperties(logPO,logBO);
            logBO.setIp(DesensitizedUtil.ipv4(logPO.getIp()));
            return logBO;
        }
        return null;
    }

    private void verifyAddParam(LogDTO record) {
        Assert.isTrue(StrUtil.isNotBlank(record.getLocation()), "位置地址不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getIp()), "IP不能为空");
        Assert.isTrue(record.getType() != null, "类型不能为空");
        Assert.isTrue(record.getUserId() != null, "用户ID不能为空");
    }
}
