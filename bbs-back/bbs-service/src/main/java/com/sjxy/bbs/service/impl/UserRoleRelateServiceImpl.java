package com.sjxy.bbs.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sjxy.bbs.entity.constants.UserConstants;
import com.sjxy.bbs.entity.dto.UserRoleRelateAddDTO;
import com.sjxy.bbs.entity.po.UserRoleRelatePO;
import com.sjxy.bbs.entity.query.UserRoleRelateQuery;
import com.sjxy.bbs.mapper.UserRoleRelateMapper;
import com.sjxy.bbs.service.BaseService;
import com.sjxy.bbs.service.UserRoleRelateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserRoleRelateServiceImpl extends BaseService implements UserRoleRelateService {
    @Autowired
    private UserRoleRelateMapper userRoleRelateMapper;

    @Override
    public List<UserRoleRelatePO> list(UserRoleRelateQuery record) {
        QueryWrapper<UserRoleRelatePO> wrapper = new QueryWrapper<>();
        wrapper.eq(record.getUserId() != null,"user_id",record.getUserId());
        wrapper.eq(record.getRoleId() != null , "role_id",record.getRoleId());
        return userRoleRelateMapper.selectList(wrapper);
    }

    @Override
    public void add(UserRoleRelateAddDTO record) {
        UserRoleRelatePO po = new UserRoleRelatePO();
        BeanUtils.copyProperties(record,po);
        po.setCreateBy(UserConstants.SYSTEM_USER_ID);
        po.setCreateTime(new Date());
        po.setCreateUsername(UserConstants.SYSTEM_USER_NAME);
        userRoleRelateMapper.insert(po);
    }

    @Override
    public void deleteByUserId(Long userId) {
        Assert.isTrue(userId != null, "userId不能为空");
        QueryWrapper<UserRoleRelatePO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        userRoleRelateMapper.delete(wrapper);
    }

    @Override
    public void deleteUserRoleRelate(Long userId, Long roleId) {
        QueryWrapper<UserRoleRelatePO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("role_id",roleId);
        userRoleRelateMapper.delete(wrapper);
    }


}
