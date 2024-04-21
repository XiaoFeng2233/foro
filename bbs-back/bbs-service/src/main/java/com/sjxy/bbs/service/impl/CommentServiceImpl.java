package com.sjxy.bbs.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.CommentBO;
import com.sjxy.bbs.entity.bo.UserBO;
import com.sjxy.bbs.entity.dto.CommentPublishDTO;
import com.sjxy.bbs.entity.enums.UserStatusEnum;
import com.sjxy.bbs.entity.enums.YesOrNoEnum;
import com.sjxy.bbs.entity.mq.UpdateTopicCommentCountMessage;
import com.sjxy.bbs.entity.mq.UpdateUserCommentCountMessage;
import com.sjxy.bbs.entity.po.CommentPO;
import com.sjxy.bbs.entity.po.TopicPO;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.query.CommentQuery;
import com.sjxy.bbs.entity.query.UserQuery;
import com.sjxy.bbs.mapper.CommentMapper;
import com.sjxy.bbs.service.BaseService;
import com.sjxy.bbs.service.CommentService;
import com.sjxy.bbs.service.TopicService;
import com.sjxy.bbs.service.UserService;
import com.sjxy.bbs.util.MQUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl extends BaseService implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private TopicService topicService;
    @Autowired
    private MQUtil mqUtil;
    @Autowired
    private UserService userService;


    @Override
    public Long getAllCommentCount() {
        return commentMapper.selectCount(null);
    }

    @Override
    public Page<CommentPO> query(Integer pageNum, Integer pageSize, CommentQuery commentQuery) {
        Page<CommentPO> page = new Page<>(pageNum, pageSize);
        return commentMapper.query(page, commentQuery);
    }

    @Override
    public Page<CommentBO> queryUserCommentList(Integer pageNum, Integer pageSize) {
        CommentQuery query = new CommentQuery();
        query.setUserId(getUserId());
        Page<CommentPO> commentPOPage = query(pageNum, pageSize, query);
        Page<CommentBO> commentBOPage = new Page<>();
        BeanUtils.copyProperties(commentPOPage, commentBOPage);
        List<CommentBO> list = commentPOPage.getRecords().stream().map(commentPO -> {
            CommentBO commentBO = new CommentBO();
            BeanUtils.copyProperties(commentPO, commentBO);

            if (commentPO.getUser() != null) {
                UserPO userPO = commentPO.getUser();
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userPO, userBO);
                commentBO.setUser(userBO);
            }

            if (commentPO.getStatus().equals(YesOrNoEnum.NO.getCode())) {
                commentBO.setContent("该评论不可见");
            }

            if (commentPO.getParent() != null) {
                CommentPO parentPO = commentPO.getParent();
                CommentBO parentBO = new CommentBO();
                BeanUtils.copyProperties(parentPO, parentBO);
                commentBO.setParent(parentBO);
            }

            if (commentPO.getStatus().equals(YesOrNoEnum.NO.getCode())) {
                commentBO.setContent("该评论不可见");
            }
            return commentBO;
        }).toList();
        commentBOPage.setRecords(list);
        return commentBOPage;
    }

    @Override
    public Page<CommentBO> queryUserCommentListByUserId(Integer pageNum, Integer pageSize, Long userId) {
        CommentQuery query = new CommentQuery();
        query.setUserId(userId);
        Page<CommentPO> commentPOPage = query(pageNum, pageSize, query);
        Page<CommentBO> commentBOPage = new Page<>();
        BeanUtils.copyProperties(commentPOPage, commentBOPage);
        List<CommentBO> list = commentPOPage.getRecords().stream().map(commentPO -> {
            CommentBO commentBO = new CommentBO();
            BeanUtils.copyProperties(commentPO, commentBO);

            if (commentPO.getUser() != null) {
                UserPO userPO = commentPO.getUser();
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userPO, userBO);
                commentBO.setUser(userBO);
            }

            if (commentPO.getStatus().equals(YesOrNoEnum.NO.getCode())) {
                commentBO.setContent("该评论不可见");
            }

            if (commentPO.getParent() != null) {
                CommentPO parentPO = commentPO.getParent();
                CommentBO parentBO = new CommentBO();
                BeanUtils.copyProperties(parentPO, parentBO);
                commentBO.setParent(parentBO);
            }

            if (commentPO.getStatus().equals(YesOrNoEnum.NO.getCode())) {
                commentBO.setContent("该评论不可见");
            }
            return commentBO;
        }).toList();
        commentBOPage.setRecords(list);
        return commentBOPage;
    }

    @Override
    public Page<CommentBO> queryTopicComment(Integer pageNum, Integer pageSize, Long topicId) {
        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setTopicId(topicId);
        Page<CommentPO> commentPOPage = query(pageNum, pageSize, commentQuery);
        Page<CommentBO> commentBOPage = new Page<>();
        BeanUtils.copyProperties(commentPOPage, commentBOPage);
        List<CommentBO> list = commentPOPage.getRecords().stream().map(commentPO -> {
            CommentBO commentBO = new CommentBO();
            BeanUtils.copyProperties(commentPO, commentBO);
            String s = DesensitizedUtil.ipv4(commentBO.getIp());
            commentBO.setIp(s);

            if (commentPO.getStatus().equals(YesOrNoEnum.NO.getCode())) {
                commentBO.setContent("该评论不可见");
            }

            if (commentPO.getParent() != null) {
                CommentBO parentCommentBO = new CommentBO();
                BeanUtils.copyProperties(commentPO.getParent(), parentCommentBO);

                if (commentPO.getParent().getStatus().equals(YesOrNoEnum.NO.getCode())) {
                    parentCommentBO.setContent("该评论不可见");
                }

                if (commentPO.getParent().getUser() != null) {
                    UserBO userBO = new UserBO();
                    BeanUtils.copyProperties(commentPO.getParent().getUser(), userBO);
                    parentCommentBO.setUser(userBO);
                }

                commentBO.setParent(parentCommentBO);
            }

            if (commentPO.getUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(commentPO.getUser(), userBO);
                commentBO.setUser(userBO);
            }

            return commentBO;
        }).toList();
        commentBOPage.setRecords(list);
        return commentBOPage;
    }

    @Override
    public void publishComment(CommentPublishDTO record) {
        TopicPO topicPO = topicService.get(record.getTopicId());
        Assert.isTrue(topicPO != null, "帖子不存在");

        Assert.isTrue(topicPO.getIsLock().equals(YesOrNoEnum.NO.getCode()), "帖子处于锁定状态,无法评论");

        UserQuery userQuery = new UserQuery();
        userQuery.setId(getUserId());
        UserPO userPO = userService.get(userQuery);

        //判断用户是否禁言
        Assert.isTrue(!userPO.getStatus().equals(UserStatusEnum.MUTE.getCode()),"当前处于禁言状态，无法发表评论");

        if (record.getParentId() != null) {
            CommentPO parentPO = commentMapper.get(record.getParentId());
            Assert.isTrue(parentPO != null, "父评论不存在");
        }

        CommentPO commentPO = new CommentPO();
        commentPO.setContent(record.getContent());
        commentPO.setIp(getIp());
        commentPO.setIpLocation(getIpLocation());
        commentPO.setUserId(getUserId());
        commentPO.setParentId(record.getParentId());
        commentPO.setStatus(YesOrNoEnum.YES.getCode());
        commentPO.setTopicId(record.getTopicId());
        commentPO.setCreateBy(getUserId());
        commentPO.setCreateTime(new Date());
        commentPO.setCreateUsername(getUsername());
        commentMapper.insert(commentPO);

        //修改帖子的最新评论日期和用户
        topicService.updateTopicLastCommentInfo(record.getTopicId());

        //让rabbitmq增加帖子的回复数量
        UpdateTopicCommentCountMessage updateTopicCommentCountMessage = new UpdateTopicCommentCountMessage();
        updateTopicCommentCountMessage.setCount(1);
        updateTopicCommentCountMessage.setTopicId(record.getTopicId());
        mqUtil.send("TopicExchange.direct", "update.topic.comment.count", updateTopicCommentCountMessage);

        //让rabbitmq增加用户的回复数量
        UpdateUserCommentCountMessage updateUserCommentCountMessage = new UpdateUserCommentCountMessage();
        updateUserCommentCountMessage.setCount(1);
        updateUserCommentCountMessage.setUserId(getUserId());
        mqUtil.send("UserExchange.direct", "update.user.comment.count", updateUserCommentCountMessage);
    }

    @Override
    public void delete(Long id) {
        commentMapper.deleteById(id);
    }

    @Override
    public CommentPO get(Long id) {
        Assert.isTrue(id != null, "id不能为空");
        return commentMapper.get(id);
    }

    @Override
    public void deleteByUserId(Long userId) {
        Assert.isTrue(userId != null, "userId不能为空");
        QueryWrapper<CommentPO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        commentMapper.delete(wrapper);
    }

    @Override
    public void deleteByTopicId(Long topicId) {
        QueryWrapper<CommentPO> wrapper = new QueryWrapper<>();
        wrapper.eq("topic_id",topicId);
        commentMapper.delete(wrapper);
    }

}
