package com.sjxy.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.UserFollowRelateBO;
import com.sjxy.bbs.entity.po.UserFollowRelatePO;
import com.sjxy.bbs.entity.query.UserFollowRelateQuery;
import org.apache.catalina.LifecycleState;

import java.util.List;

public interface UserFollowRelateService {
    UserFollowRelatePO get(UserFollowRelateQuery record);

    Page<UserFollowRelatePO> query(Integer pageNum, Integer pageSize, UserFollowRelateQuery record);

    void followUser(Long targetUserId);

    void unFollowUser(Long targetUserId);

    Page<UserFollowRelateBO> queryUserFollowList(Integer pageNum, Integer pageSize);

    Page<UserFollowRelateBO> queryUserFollowListByUserId(Integer pageNum, Integer pageSize, Long userId);

    Page<UserFollowRelateBO> queryUserFansList(Integer pageNum, Integer pageSize);

    Page<UserFollowRelateBO> queryUserFansListByUserId(Integer pageNum, Integer pageSize, Long userId);

    List<UserFollowRelatePO> list(UserFollowRelateQuery record);

    void deleteByUserId(Long userId);
}
