package com.sjxy.bbs.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.UserBO;
import com.sjxy.bbs.entity.bo.UserFollowRelateBO;
import com.sjxy.bbs.entity.mq.NotifyNewFanMessage;
import com.sjxy.bbs.entity.mq.NotifyReduceFanMessage;
import com.sjxy.bbs.entity.po.UserFollowRelatePO;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.query.UserFollowRelateQuery;
import com.sjxy.bbs.mapper.UserFollowRelateMapper;
import com.sjxy.bbs.mapper.UserMapper;
import com.sjxy.bbs.service.BaseService;
import com.sjxy.bbs.service.UserFollowRelateService;
import com.sjxy.bbs.util.MQUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserFollowRelateServiceImpl extends BaseService implements UserFollowRelateService {

    @Autowired
    private UserFollowRelateMapper userFollowRelateMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MQUtil mqUtil;


    @Override
    public UserFollowRelatePO get(UserFollowRelateQuery record) {
        return userFollowRelateMapper.get(record);
    }

    @Override
    public Page<UserFollowRelatePO> query(Integer pageNum, Integer pageSize, UserFollowRelateQuery record) {
        Page<UserFollowRelatePO> page = new Page<>(pageNum, pageSize);
        return userFollowRelateMapper.query(page, record);
    }

    @Override
    public void followUser(Long targetUserId) {
        Assert.isTrue(!targetUserId.equals(getUserId()), "不能关注自己哦");
        UserPO userPO = userMapper.get(targetUserId);
        Assert.isTrue(userPO != null, "用户不存在");
        UserFollowRelateQuery userFollowRelateQuery = new UserFollowRelateQuery();
        userFollowRelateQuery.setFollowUserId(getUserId());
        userFollowRelateQuery.setFollowedUserId(targetUserId);
        List<UserFollowRelatePO> userFollowRelatePOList = list(userFollowRelateQuery);
        Assert.isTrue(userFollowRelatePOList.isEmpty(), "您已经关注过该用户");

        UserFollowRelatePO po = new UserFollowRelatePO();
        po.setFollowedUserId(targetUserId);
        po.setFollowUserId(getUserId());
        po.setCreateBy(getUserId());
        po.setCreateUsername(getUsername());
        po.setCreateTime(new Date());
        userFollowRelateMapper.insert(po);

        //让rabbitmq发送关注通知
        NotifyNewFanMessage notifyNewFanMessage = new NotifyNewFanMessage();
        notifyNewFanMessage.setFollowedUserId(targetUserId);
        notifyNewFanMessage.setFollowUserId(getUserId());
        mqUtil.send("NoticeExchange.direct", "notify.user.new.fan", notifyNewFanMessage);
    }


    @Override
    public void unFollowUser(Long targetUserId) {
        Assert.isTrue(!targetUserId.equals(getUserId()), "不能取关自己哦");

        UserFollowRelateQuery userFollowRelateQuery = new UserFollowRelateQuery();
        userFollowRelateQuery.setFollowUserId(getUserId());
        userFollowRelateQuery.setFollowedUserId(targetUserId);

        List<UserFollowRelatePO> list = list(userFollowRelateQuery);
        Assert.isTrue(!list.isEmpty(), "取关失败，您还未关注该用户");

        QueryWrapper<UserFollowRelatePO> wrapper = new QueryWrapper<>();
        wrapper.eq("follow_user_id", getUserId());
        wrapper.eq("followed_user_id", targetUserId);
        userFollowRelateMapper.delete(wrapper);

        //让rabbitmq发送取消关注通知
        NotifyReduceFanMessage notifyReduceFanMessage = new NotifyReduceFanMessage();
        notifyReduceFanMessage.setFollowedUserId(targetUserId);
        notifyReduceFanMessage.setFollowUserId(getUserId());
        mqUtil.send("NoticeExchange.direct", "notify.user.reduce.fan", notifyReduceFanMessage);

    }

    @Override
    public Page<UserFollowRelateBO> queryUserFollowList(Integer pageNum, Integer pageSize) {
        UserFollowRelateQuery record = new UserFollowRelateQuery();
        record.setFollowUserId(getUserId());
        Page<UserFollowRelatePO> userFollowRelatePOPage = query(pageNum, pageSize, record);
        Page<UserFollowRelateBO> userFollowRelateBOPage = new Page<>();
        BeanUtils.copyProperties(userFollowRelatePOPage, userFollowRelateBOPage);
        Long loginUserId = getUserId();

        List<UserFollowRelateBO> list = userFollowRelatePOPage.getRecords().stream().map(userFollowRelatePO -> {
            UserFollowRelateBO userFollowRelateBO = new UserFollowRelateBO();
            BeanUtils.copyProperties(userFollowRelatePO, userFollowRelateBO);
            if (userFollowRelatePO.getFollowedUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userFollowRelatePO.getFollowedUser(), userBO);
                userFollowRelateBO.setFollowedUser(userBO);
            }

            if (userFollowRelatePO.getFollowUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userFollowRelatePO.getFollowUser(), userBO);
                userFollowRelateBO.setFollowUser(userBO);
            }

            //根据查出来的被关注者ID 判断当前登录用户是否关注过这个用户
            UserFollowRelateQuery query = new UserFollowRelateQuery();
            query.setFollowedUserId(userFollowRelatePO.getFollowedUserId());
            query.setFollowUserId(loginUserId);
            UserFollowRelatePO FollowRelatePO = userFollowRelateMapper.get(query);
            if (FollowRelatePO != null) {
                userFollowRelateBO.setFollowed(true);
            } else {
                userFollowRelateBO.setFollowed(false);
            }

            return userFollowRelateBO;
        }).toList();
        userFollowRelateBOPage.setRecords(list);
        return userFollowRelateBOPage;
    }

    @Override
    public Page<UserFollowRelateBO> queryUserFollowListByUserId(Integer pageNum, Integer pageSize, Long userId) {
        UserFollowRelateQuery record = new UserFollowRelateQuery();
        record.setFollowUserId(userId);
        Page<UserFollowRelatePO> userFollowRelatePOPage = query(pageNum, pageSize, record);
        Page<UserFollowRelateBO> userFollowRelateBOPage = new Page<>();
        BeanUtils.copyProperties(userFollowRelatePOPage, userFollowRelateBOPage);
        Long loginUserId = getUserId();
        List<UserFollowRelateBO> list = userFollowRelatePOPage.getRecords().stream().map(userFollowRelatePO -> {
            UserFollowRelateBO userFollowRelateBO = new UserFollowRelateBO();
            BeanUtils.copyProperties(userFollowRelatePO, userFollowRelateBO);
            if (userFollowRelatePO.getFollowedUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userFollowRelatePO.getFollowedUser(), userBO);
                userFollowRelateBO.setFollowedUser(userBO);
            }

            if (userFollowRelatePO.getFollowUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userFollowRelatePO.getFollowUser(), userBO);
                userFollowRelateBO.setFollowUser(userBO);
            }

            //根据查出来的被关注者ID 判断当前登录用户是否关注过这个用户
            UserFollowRelateQuery query = new UserFollowRelateQuery();
            query.setFollowedUserId(userFollowRelatePO.getFollowedUserId());
            query.setFollowUserId(loginUserId);
            UserFollowRelatePO FollowRelatePO = userFollowRelateMapper.get(query);
            if (FollowRelatePO != null) {
                userFollowRelateBO.setFollowed(true);
            } else {
                userFollowRelateBO.setFollowed(false);
            }
            return userFollowRelateBO;

        }).toList();
        userFollowRelateBOPage.setRecords(list);
        return userFollowRelateBOPage;
    }

    @Override
    public Page<UserFollowRelateBO> queryUserFansList(Integer pageNum, Integer pageSize) {
        UserFollowRelateQuery record = new UserFollowRelateQuery();
        record.setFollowedUserId(getUserId());
        Page<UserFollowRelatePO> userFollowRelatePOPage = query(pageNum, pageSize, record);
        Page<UserFollowRelateBO> userFollowRelateBOPage = new Page<>();
        BeanUtils.copyProperties(userFollowRelatePOPage, userFollowRelateBOPage);
        Long loginUserId = getUserId();

        List<UserFollowRelateBO> list = userFollowRelatePOPage.getRecords().stream().map(userFollowRelatePO -> {
            UserFollowRelateBO userFollowRelateBO = new UserFollowRelateBO();
            BeanUtils.copyProperties(userFollowRelatePO, userFollowRelateBO);
            if (userFollowRelatePO.getFollowedUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userFollowRelatePO.getFollowedUser(), userBO);
                userFollowRelateBO.setFollowedUser(userBO);
            }

            if (userFollowRelatePO.getFollowUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userFollowRelatePO.getFollowUser(), userBO);
                userFollowRelateBO.setFollowUser(userBO);
            }

            //根据查出来的被关注者ID 判断当前登录用户是否关注过这个用户
            UserFollowRelateQuery query = new UserFollowRelateQuery();
            query.setFollowedUserId(userFollowRelatePO.getFollowUserId());
            query.setFollowUserId(loginUserId);
            UserFollowRelatePO FollowRelatePO = userFollowRelateMapper.get(query);
            if (FollowRelatePO != null) {
                userFollowRelateBO.setFollowed(true);
            } else {
                userFollowRelateBO.setFollowed(false);
            }

            return userFollowRelateBO;
        }).toList();
        userFollowRelateBOPage.setRecords(list);
        return userFollowRelateBOPage;
    }

    @Override
    public Page<UserFollowRelateBO> queryUserFansListByUserId(Integer pageNum, Integer pageSize, Long userId) {
        UserFollowRelateQuery record = new UserFollowRelateQuery();
        record.setFollowedUserId(userId);
        Page<UserFollowRelatePO> userFollowRelatePOPage = query(pageNum, pageSize, record);
        Page<UserFollowRelateBO> userFollowRelateBOPage = new Page<>();
        BeanUtils.copyProperties(userFollowRelatePOPage, userFollowRelateBOPage);
        Long loginUserId = getUserId();
        List<UserFollowRelateBO> list = userFollowRelatePOPage.getRecords().stream().map(userFollowRelatePO -> {
            UserFollowRelateBO userFollowRelateBO = new UserFollowRelateBO();
            BeanUtils.copyProperties(userFollowRelatePO, userFollowRelateBO);
            if (userFollowRelatePO.getFollowedUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userFollowRelatePO.getFollowedUser(), userBO);
                userFollowRelateBO.setFollowedUser(userBO);
            }

            if (userFollowRelatePO.getFollowUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userFollowRelatePO.getFollowUser(), userBO);
                userFollowRelateBO.setFollowUser(userBO);
            }

            //根据查出来的被关注者ID 判断当前登录用户是否关注过这个用户
            UserFollowRelateQuery query = new UserFollowRelateQuery();
            query.setFollowedUserId(userFollowRelatePO.getFollowUserId());
            query.setFollowUserId(loginUserId);
            UserFollowRelatePO FollowRelatePO = userFollowRelateMapper.get(query);
            if (FollowRelatePO != null) {
                userFollowRelateBO.setFollowed(true);
            } else {
                userFollowRelateBO.setFollowed(false);
            }
            return userFollowRelateBO;
        }).toList();
        userFollowRelateBOPage.setRecords(list);
        return userFollowRelateBOPage;
    }

    @Override
    public List<UserFollowRelatePO> list(UserFollowRelateQuery record) {
        return userFollowRelateMapper.list(record);
    }

    @Override
    public void deleteByUserId(Long userId) {
        QueryWrapper<UserFollowRelatePO> wrapper = new QueryWrapper<>();
        wrapper.eq("follow_user_id", userId);
        wrapper.eq("followed_user_id", userId);
        userFollowRelateMapper.delete(wrapper);
    }
}
