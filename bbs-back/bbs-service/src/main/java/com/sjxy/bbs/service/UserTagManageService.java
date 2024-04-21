package com.sjxy.bbs.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.UserTagManageBO;
import com.sjxy.bbs.entity.dto.UserTagManageAddDTO;
import com.sjxy.bbs.entity.po.UserTagManagePO;
import com.sjxy.bbs.entity.query.UserTagManageQuery;

import java.util.List;

public interface UserTagManageService {
    List<UserTagManageBO> list(UserTagManageQuery record);

    void add(UserTagManageAddDTO record);

    Page<UserTagManageBO> query(Integer pageNum, Integer pageSize, UserTagManageQuery record);

    void delete(Long id);

    void deleteByUserId(Long userId);
}
