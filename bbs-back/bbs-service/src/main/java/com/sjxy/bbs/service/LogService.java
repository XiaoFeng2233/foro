package com.sjxy.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.LogBO;
import com.sjxy.bbs.entity.dto.LogDTO;
import com.sjxy.bbs.entity.enums.LogTypeEnum;
import com.sjxy.bbs.entity.po.LogPO;

public interface LogService {
    void add(LogDTO record);

    void add(LogPO logPO);

    /**
     * 获取用户的分页日志记录
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @return 分页日志记录
     */
    Page<LogBO> getUserLoginLog(Integer pageNum, Integer pageSize);

    /**
     * 获取用户的最近一条日志记录
     *
     * @param userId      用户ID
     * @param logTypeEnum 记录类型
     * @return 日志记录
     */
    LogBO getLastLog(Long userId, LogTypeEnum logTypeEnum);
}
