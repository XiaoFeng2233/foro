package com.sjxy.bbs.service;

import com.sjxy.bbs.entity.po.PermissionRoleRelatePO;
import com.sjxy.bbs.entity.query.PermissionRoleRelateQuery;

import java.util.List;

public interface PermissionRoleRelateService {
    List<PermissionRoleRelatePO> list(PermissionRoleRelateQuery record);
}
