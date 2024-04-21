package com.sjxy.bbs.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.ForbiddenLogBO;
import com.sjxy.bbs.entity.dto.ForbiddenLogAddDTO;
import com.sjxy.bbs.entity.po.ForbiddenLogPO;
import com.sjxy.bbs.entity.query.ForbiddenLogQuery;
import com.sjxy.bbs.mapper.ForbiddenLogMapper;
import com.sjxy.bbs.service.BaseService;
import com.sjxy.bbs.service.ForbiddenLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ForbiddenLogServiceImpl extends BaseService implements ForbiddenLogService {
    @Autowired
    private ForbiddenLogMapper mapper;

    @Override
    public void add(ForbiddenLogAddDTO record) {
        Assert.isTrue(record.getUserId() != null, "userId不能为空");
        Assert.isTrue(record.getStartTime() != null, "startTime不能为空");
        Assert.isTrue(record.getFinishTime() != null, "finishTime不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getReason()), "reason不能为空");

        ForbiddenLogPO po = new ForbiddenLogPO();
        BeanUtils.copyProperties(record, po);
        po.setCreateBy(getUserId());
        po.setCreateTime(new Date());
        po.setCreateUsername(getUsername());

        mapper.insert(po);
    }

    @Override
    public Page<ForbiddenLogPO> query(Integer pageNum, Integer pageSize, ForbiddenLogQuery record) {
        QueryWrapper<ForbiddenLogPO> wrapper = new QueryWrapper<>();
        Page<ForbiddenLogPO> page = new Page<>(pageNum, pageSize);
        if (record != null) {
            wrapper.eq(record.getUserId() != null, "user_id", record.getUserId());
            wrapper.orderByDesc("id");
        }
        return mapper.selectPage(page, wrapper);
    }

    @Override
    public Page<ForbiddenLogBO> queryUserForbiddenLog(Integer pageNum, Integer pageSize) {
        ForbiddenLogQuery record = new ForbiddenLogQuery();
        record.setUserId(getUserId());
        Page<ForbiddenLogPO> forbiddenLogPOPage = query(pageNum, pageSize, record);
        Page<ForbiddenLogBO> forbiddenLogBOPage = new Page<>();
        BeanUtils.copyProperties(forbiddenLogPOPage, forbiddenLogBOPage);
        List<ForbiddenLogBO> list = forbiddenLogPOPage.getRecords().stream().map(forbiddenLogPO -> {
            ForbiddenLogBO forbiddenLogBO = new ForbiddenLogBO();
            BeanUtils.copyProperties(forbiddenLogPO, forbiddenLogBO);
            return forbiddenLogBO;
        }).toList();

        forbiddenLogBOPage.setRecords(list);
        return forbiddenLogBOPage;
    }

    @Override
    public Page<ForbiddenLogBO> queryUserForbiddenLogByUserId(Integer pageNum, Integer pageSize, Long userId) {
        ForbiddenLogQuery record = new ForbiddenLogQuery();
        record.setUserId(userId);
        Page<ForbiddenLogPO> forbiddenLogPOPage = query(pageNum, pageSize, record);
        Page<ForbiddenLogBO> forbiddenLogBOPage = new Page<>();
        BeanUtils.copyProperties(forbiddenLogPOPage, forbiddenLogBOPage);
        List<ForbiddenLogBO> list = forbiddenLogPOPage.getRecords().stream().map(forbiddenLogPO -> {
            ForbiddenLogBO forbiddenLogBO = new ForbiddenLogBO();
            BeanUtils.copyProperties(forbiddenLogPO, forbiddenLogBO);
            return forbiddenLogBO;
        }).toList();

        forbiddenLogBOPage.setRecords(list);
        return forbiddenLogBOPage;
    }
}
