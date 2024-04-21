package com.sjxy.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sjxy.bbs.entity.po.PermissionRoleRelatePO;
import com.sjxy.bbs.entity.query.PermissionRoleRelateQuery;
import com.sjxy.bbs.mapper.PermissionRoleRelateMapper;
import com.sjxy.bbs.service.PermissionRoleRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionRoleRelateServiceImpl implements PermissionRoleRelateService {
    @Autowired
    private PermissionRoleRelateMapper permissionRoleRelateMapper;

    @Override
    public List<PermissionRoleRelatePO> list(PermissionRoleRelateQuery record) {
        QueryWrapper<PermissionRoleRelatePO> wrapper = new QueryWrapper<>();
        wrapper.eq(record.getRoleId() != null, "role_id", record.getRoleId());
        wrapper.eq(record.getPermissionId() != null, "permission_id", record.getPermissionId());
        return permissionRoleRelateMapper.selectList(wrapper);
    }
}
