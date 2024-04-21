package com.sjxy.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.UserCollectRelateBO;
import com.sjxy.bbs.entity.po.UserCollectRelatePO;
import com.sjxy.bbs.entity.query.UserCollectRelateQuery;

import java.util.List;

public interface UserCollectRelateService {

    Page<UserCollectRelatePO> query(Integer pageNum, Integer pageSize, UserCollectRelateQuery record);

    Page<UserCollectRelateBO> queryUserCollectList(Integer pageNum, Integer pageSize);

    Page<UserCollectRelateBO> queryUserCollectListByUserId(Integer pageNum, Integer pageSize, Long userId);

    List<UserCollectRelatePO> list(UserCollectRelateQuery record);

    void add(UserCollectRelatePO record);

    UserCollectRelatePO get(UserCollectRelateQuery record);

    void delete(Long id);

    void deleteByUserId(Long userId);
    void deleteByTopicId(Long topicId);
}
