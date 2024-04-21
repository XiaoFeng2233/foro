package com.sjxy.bbs.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.NoticeBO;
import com.sjxy.bbs.entity.po.NoticePO;
import com.sjxy.bbs.entity.query.NoticeQuery;
import com.sjxy.bbs.mapper.NoticeMapper;
import com.sjxy.bbs.service.BaseService;
import com.sjxy.bbs.service.NoticeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class NoticeServiceImpl extends BaseService implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;


    @Override
    public Page<NoticePO> query(Integer pageNum, Integer pageSize, NoticeQuery record) {
        Page<NoticePO> page = new Page<>(pageNum, pageSize);
        return noticeMapper.query(page, record);
    }

    @Override
    public Page<NoticeBO> queryUserNotice(Integer pageNum, Integer pageSize) {
        NoticeQuery noticeQuery = new NoticeQuery();
        noticeQuery.setUserId(getUserId());
        Page<NoticePO> noticePOPage = query(pageNum, pageSize, noticeQuery);
        Page<NoticeBO> noticeBOPage = new Page<>();
        BeanUtils.copyProperties(noticePOPage, noticeBOPage);
        List<NoticeBO> list = noticePOPage.getRecords().stream().map(noticePO -> {
            NoticeBO noticeBO = new NoticeBO();
            BeanUtils.copyProperties(noticePO, noticeBO);
            return noticeBO;
        }).toList();
        noticeBOPage.setRecords(list);
        return noticeBOPage;
    }

    @Override
    public void add(NoticePO noticePO) {
        Assert.isTrue(StrUtil.isNotBlank(noticePO.getImage()), "消息图片不能为空");
        Assert.isTrue(noticePO.getUserId() != null, "用户ID不能为空");
        Assert.isTrue(StrUtil.isNotBlank(noticePO.getContent()), "消息内容不能为空");
        noticeMapper.insert(noticePO);
    }

    @Override
    public void addBatch(List<NoticePO> list) {
        list.forEach(noticePO -> {
            Assert.isTrue(StrUtil.isNotBlank(noticePO.getImage()), "消息图片不能为空");
            Assert.isTrue(noticePO.getUserId() != null, "用户ID不能为空");
            Assert.isTrue(StrUtil.isNotBlank(noticePO.getContent()), "消息内容不能为空");
            noticePO.setVersion(1);
            noticePO.setDeleted(0);
        });

        noticeMapper.insertBatch(list);
    }
}
