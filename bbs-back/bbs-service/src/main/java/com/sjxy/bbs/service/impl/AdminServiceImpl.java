package com.sjxy.bbs.service.impl;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.sjxy.bbs.entity.bo.StatisticBO;
import com.sjxy.bbs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TagService tagService;

    @Override
    public StatisticBO getStatistic() {
        StatisticBO statisticBO = new StatisticBO();
        statisticBO.setTopicCount(topicService.getAllTopicCount());
        statisticBO.setUserCount(userService.getAllUserCount());
        statisticBO.setCommentCount(commentService.getAllCommentCount());
        statisticBO.setTagCount(tagService.getAllTagCount());
        return statisticBO;
    }
}
