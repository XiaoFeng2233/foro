package com.sjxy.bbs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sjxy.bbs.entity.po.PermissionPO;
import com.sjxy.bbs.entity.po.PermissionRoleRelatePO;
import com.sjxy.bbs.entity.query.PermissionRoleRelateQuery;
import com.sjxy.bbs.mapper.PermissionMapper;
import com.sjxy.bbs.service.PermissionRoleRelateService;
import com.sjxy.bbs.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private PermissionRoleRelateService permissionRoleRelateService;

    @Override
    public List<PermissionPO> getPermissionsByRoleId(Long roleId) {
        PermissionRoleRelateQuery query = new PermissionRoleRelateQuery();
        query.setRoleId(roleId);
        List<PermissionRoleRelatePO> PermissionRoleRelateList = permissionRoleRelateService.list(query);
        if (CollUtil.isEmpty(PermissionRoleRelateList)) {
            return new ArrayList<>();
        }

        List<Long> permissionIdList = PermissionRoleRelateList.stream().map(PermissionRoleRelatePO::getPermissionId).toList();

        if (CollUtil.isEmpty(permissionIdList)) {
            return new ArrayList<>();
        }

        QueryWrapper<PermissionPO> wrapper = new QueryWrapper<>();
        wrapper.in("id", permissionIdList);
        return permissionMapper.selectList(wrapper);
    }
}
