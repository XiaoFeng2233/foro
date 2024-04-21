package com.sjxy.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.po.UserLikeRelatePO;
import com.sjxy.bbs.entity.query.UserLikeRelateQuery;
import com.sjxy.bbs.mapper.UserLikeRelateMapper;
import com.sjxy.bbs.service.UserLikeRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLikeRelateServiceImpl implements UserLikeRelateService {
    @Autowired
    private UserLikeRelateMapper userLikeRelateMapper;


    @Override
    public Page<UserLikeRelatePO> query(Integer pageNum, Integer pageSize, UserLikeRelateQuery record) {
        Page<UserLikeRelatePO> page = new Page<>(pageNum, pageSize);
        return userLikeRelateMapper.query(page,record);
    }

    @Override
    public List<UserLikeRelatePO> list(UserLikeRelateQuery record) {
        return userLikeRelateMapper.list(record);
    }

    @Override
    public void add(UserLikeRelatePO record) {
        userLikeRelateMapper.insert(record);
    }

    @Override
    public UserLikeRelatePO get(UserLikeRelateQuery record) {
        return userLikeRelateMapper.get(record);
    }

    @Override
    public void deleteByUserId(Long userId) {
        QueryWrapper<UserLikeRelatePO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        userLikeRelateMapper.delete(wrapper);
    }

    @Override
    public void delete(Long id) {
        userLikeRelateMapper.deleteById(id);
    }

    @Override
    public void deleteByTopicId(Long topicId) {
        QueryWrapper<UserLikeRelatePO> wrapper = new QueryWrapper<>();
        wrapper.eq("topic_id",topicId);
        userLikeRelateMapper.delete(wrapper);
    }
}
