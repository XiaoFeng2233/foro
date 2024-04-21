package com.sjxy.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.TopicBO;
import com.sjxy.bbs.entity.bo.UserBO;
import com.sjxy.bbs.entity.bo.UserCollectRelateBO;
import com.sjxy.bbs.entity.po.UserCollectRelatePO;
import com.sjxy.bbs.entity.query.UserCollectRelateQuery;
import com.sjxy.bbs.mapper.UserCollectRelateMapper;
import com.sjxy.bbs.service.BaseService;
import com.sjxy.bbs.service.UserCollectRelateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCollectRelateServiceImpl extends BaseService implements UserCollectRelateService {
    @Autowired
    private UserCollectRelateMapper userCollectRelateMapper;


    @Override
    public Page<UserCollectRelatePO> query(Integer pageNum, Integer pageSize, UserCollectRelateQuery record) {
        Page<UserCollectRelatePO> page = new Page<>(pageNum, pageSize);
        return userCollectRelateMapper.query(page, record);
    }

    @Override
    public Page<UserCollectRelateBO> queryUserCollectList(Integer pageNum, Integer pageSize) {
        UserCollectRelateQuery record = new UserCollectRelateQuery();
        record.setUserId(getUserId());
        Page<UserCollectRelatePO> userCollectRelatePOPage = query(pageNum, pageSize, record);
        Page<UserCollectRelateBO> userCollectRelateBOPage = new Page<>();
        BeanUtils.copyProperties(userCollectRelatePOPage, userCollectRelateBOPage);
        List<UserCollectRelateBO> list = userCollectRelatePOPage.getRecords().stream().map(userCollectRelatePO -> {
            UserCollectRelateBO userCollectRelateBO = new UserCollectRelateBO();
            BeanUtils.copyProperties(userCollectRelatePO, userCollectRelateBO);

            if (userCollectRelatePO.getUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userCollectRelatePO.getUser(), userBO);
                userCollectRelateBO.setUser(userBO);
            }

            if (userCollectRelatePO.getTopic() != null) {
                TopicBO topicBO = new TopicBO();
                BeanUtils.copyProperties(userCollectRelatePO.getTopic(), topicBO);
                if(userCollectRelatePO.getTopic().getUser() != null){
                    UserBO userBO = new UserBO();
                    BeanUtils.copyProperties(userCollectRelatePO.getTopic().getUser(),userBO);
                    topicBO.setUser(userBO);
                }
                userCollectRelateBO.setTopic(topicBO);
            }

            return userCollectRelateBO;
        }).toList();
        userCollectRelateBOPage.setRecords(list);
        return userCollectRelateBOPage;
    }

    @Override
    public Page<UserCollectRelateBO> queryUserCollectListByUserId(Integer pageNum, Integer pageSize, Long userId) {
        UserCollectRelateQuery record = new UserCollectRelateQuery();
        record.setUserId(userId);
        Page<UserCollectRelatePO> userCollectRelatePOPage = query(pageNum, pageSize, record);
        Page<UserCollectRelateBO> userCollectRelateBOPage = new Page<>();
        BeanUtils.copyProperties(userCollectRelatePOPage, userCollectRelateBOPage);
        List<UserCollectRelateBO> list = userCollectRelatePOPage.getRecords().stream().map(userCollectRelatePO -> {
            UserCollectRelateBO userCollectRelateBO = new UserCollectRelateBO();
            BeanUtils.copyProperties(userCollectRelatePO, userCollectRelateBO);

            if (userCollectRelatePO.getUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userCollectRelatePO.getUser(), userBO);
                userCollectRelateBO.setUser(userBO);
            }

            if (userCollectRelatePO.getTopic() != null) {
                TopicBO topicBO = new TopicBO();
                BeanUtils.copyProperties(userCollectRelatePO.getTopic(), topicBO);
                if(userCollectRelatePO.getTopic().getUser() != null){
                    UserBO userBO = new UserBO();
                    BeanUtils.copyProperties(userCollectRelatePO.getTopic().getUser(),userBO);
                    topicBO.setUser(userBO);
                }
                userCollectRelateBO.setTopic(topicBO);
            }

            return userCollectRelateBO;
        }).toList();
        userCollectRelateBOPage.setRecords(list);
        return userCollectRelateBOPage;
    }

    @Override
    public List<UserCollectRelatePO> list(UserCollectRelateQuery record) {
        return userCollectRelateMapper.list(record);
    }

    @Override
    public void add(UserCollectRelatePO record) {
        userCollectRelateMapper.insert(record);
    }

    @Override
    public UserCollectRelatePO get(UserCollectRelateQuery record) {
        return userCollectRelateMapper.get(record);
    }

    @Override
    public void delete(Long id) {
        userCollectRelateMapper.deleteById(id);
    }

    @Override
    public void deleteByUserId(Long userId) {
        QueryWrapper<UserCollectRelatePO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        userCollectRelateMapper.delete(wrapper);
    }

    @Override
    public void deleteByTopicId(Long topicId) {
        QueryWrapper<UserCollectRelatePO> wrapper = new QueryWrapper<>();
        wrapper.eq("topic_id", topicId);
        userCollectRelateMapper.delete(wrapper);
    }
}
