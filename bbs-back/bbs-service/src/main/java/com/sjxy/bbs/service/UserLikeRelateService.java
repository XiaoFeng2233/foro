package com.sjxy.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.po.UserLikeRelatePO;
import com.sjxy.bbs.entity.query.UserLikeRelateQuery;

import java.util.List;

public interface UserLikeRelateService {
    Page<UserLikeRelatePO> query(Integer pageNum, Integer pageSize, UserLikeRelateQuery record);
    List<UserLikeRelatePO> list(UserLikeRelateQuery record);
    void add(UserLikeRelatePO record);
    UserLikeRelatePO get(UserLikeRelateQuery record);
    void deleteByUserId(Long userId);
    void delete(Long id);
    void deleteByTopicId(Long topicId);
}
