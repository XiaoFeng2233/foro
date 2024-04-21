package com.sjxy.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.ForbiddenLogBO;
import com.sjxy.bbs.entity.dto.ForbiddenLogAddDTO;
import com.sjxy.bbs.entity.po.ForbiddenLogPO;
import com.sjxy.bbs.entity.query.ForbiddenLogQuery;

public interface ForbiddenLogService {

    void add(ForbiddenLogAddDTO record);
    Page<ForbiddenLogPO> query(Integer pageNum, Integer pageSize, ForbiddenLogQuery record);

    Page<ForbiddenLogBO> queryUserForbiddenLog(Integer pageNum, Integer pageSize);


    Page<ForbiddenLogBO> queryUserForbiddenLogByUserId(Integer pageNum, Integer pageSize, Long userId);
}
