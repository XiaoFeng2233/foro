package com.sjxy.bbs.service;

import com.sjxy.bbs.entity.po.RolePO;

import java.util.List;

public interface RoleService {
    List<RolePO> getRolesByUserId(Long userId);

    void deleteByUserId(Long userId);
}
