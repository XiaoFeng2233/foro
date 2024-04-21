package com.sjxy.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.NoticeBO;
import com.sjxy.bbs.entity.po.NoticePO;
import com.sjxy.bbs.entity.query.NoticeQuery;

import java.util.List;

public interface NoticeService {
    Page<NoticePO> query(Integer pageNum, Integer pageSize, NoticeQuery record);

    Page<NoticeBO> queryUserNotice(Integer pageNum, Integer pageSize);

    void add(NoticePO noticePO);

    void addBatch(List<NoticePO> list);
}
