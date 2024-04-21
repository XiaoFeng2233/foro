package com.sjxy.bbs.service;

import com.sjxy.bbs.entity.po.PermissionPO;

import java.util.List;

public interface PermissionService {
    List<PermissionPO> getPermissionsByRoleId(Long roleId);

}
