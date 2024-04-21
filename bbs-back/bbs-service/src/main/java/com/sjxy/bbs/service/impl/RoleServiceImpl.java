package com.sjxy.bbs.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sjxy.bbs.entity.po.RolePO;
import com.sjxy.bbs.entity.po.UserRoleRelatePO;
import com.sjxy.bbs.entity.query.UserRoleRelateQuery;
import com.sjxy.bbs.mapper.RoleMapper;
import com.sjxy.bbs.service.RoleService;
import com.sjxy.bbs.service.UserRoleRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleRelateService userRoleRelateService;

    @Override
    public List<RolePO> getRolesByUserId(Long userId) {

        //根据UserID查询角色和用户关系表
        UserRoleRelateQuery query = new UserRoleRelateQuery();
        query.setUserId(userId);
        List<UserRoleRelatePO> userRoleRelateList = userRoleRelateService.list(query);

        //判断获取的关系集合是否为空
        if (CollUtil.isEmpty(userRoleRelateList)) {
            return ListUtil.empty();
        }

        List<Long> roleIds = userRoleRelateList.stream().map(UserRoleRelatePO::getRoleId).toList();

        QueryWrapper<RolePO> wrapper = new QueryWrapper<>();
        wrapper.in("id", roleIds);
        return roleMapper.selectList(wrapper);
    }

    @Override
    public void deleteByUserId(Long userId) {
        Assert.isTrue(userId != null, "userId不能为空");
        userRoleRelateService.deleteByUserId(userId);
    }

}
