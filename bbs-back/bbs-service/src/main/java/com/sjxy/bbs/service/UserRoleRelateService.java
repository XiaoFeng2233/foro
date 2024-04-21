package com.sjxy.bbs.service;

import com.sjxy.bbs.entity.dto.UserRoleRelateAddDTO;
import com.sjxy.bbs.entity.po.UserRoleRelatePO;
import com.sjxy.bbs.entity.query.UserRoleRelateQuery;

import java.util.List;

public interface UserRoleRelateService {
    List<UserRoleRelatePO> list(UserRoleRelateQuery record);

    void add(UserRoleRelateAddDTO record);

    void deleteByUserId(Long userId);

    void deleteUserRoleRelate(Long userId, Long roleId);
}
