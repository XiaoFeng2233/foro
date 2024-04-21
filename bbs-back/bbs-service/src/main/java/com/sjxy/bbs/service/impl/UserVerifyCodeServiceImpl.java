package com.sjxy.bbs.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sjxy.bbs.entity.constants.UserConstants;
import com.sjxy.bbs.entity.dto.UserVerifyCodeAddDTO;
import com.sjxy.bbs.entity.enums.YesOrNoEnum;
import com.sjxy.bbs.entity.po.UserVerifyCodePO;
import com.sjxy.bbs.entity.query.UserVerifyCodeQuery;
import com.sjxy.bbs.mapper.UserVerifyCodeMapper;
import com.sjxy.bbs.service.BaseService;
import com.sjxy.bbs.service.UserVerifyCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UserVerifyCodeServiceImpl extends BaseService implements UserVerifyCodeService {

    @Autowired
    private UserVerifyCodeMapper userVerifyCodeMapper;

    @Override
    public UserVerifyCodePO get(UserVerifyCodeQuery record) {
        return userVerifyCodeMapper.get(record);
    }

    @Override
    public void update(UserVerifyCodePO record) {
        userVerifyCodeMapper.updateById(record);
    }

    @Override
    public void add(UserVerifyCodeAddDTO record) {
        Assert.isTrue(record != null, "参数不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getCode()), "code不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getToken()), "token不能为空");
        Assert.isTrue(record.getUserId() != null, "userId不能为空");
        Assert.isTrue(record.getExpireTime() != null, "expireTime不能为空");
        Assert.isTrue(record.getType() != null, "type不能为空");
        UserVerifyCodePO po = new UserVerifyCodePO();
        BeanUtils.copyProperties(record, po);
        po.setChecked(YesOrNoEnum.NO.getCode());
        po.setCreateBy(UserConstants.SYSTEM_USER_ID);
        po.setCreateUsername(UserConstants.SYSTEM_USER_NAME);
        po.setCreateTime(new Date());
        userVerifyCodeMapper.insert(po);
    }

    @Override
    public void delete(Long id) {
        QueryWrapper<UserVerifyCodePO> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        userVerifyCodeMapper.delete(wrapper);
    }

    @Override
    public List<UserVerifyCodePO> queryAllExpireCode() {
        QueryWrapper<UserVerifyCodePO> wrapper = new QueryWrapper<>();
        wrapper.lt("expire_time", DateUtil.now());
        return userVerifyCodeMapper.selectList(wrapper);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        userVerifyCodeMapper.deleteBatchIds(ids);
    }


}
