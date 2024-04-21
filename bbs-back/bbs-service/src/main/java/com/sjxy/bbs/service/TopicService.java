package com.sjxy.bbs.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.TopicBO;
import com.sjxy.bbs.entity.dto.TopicPublishDTO;
import com.sjxy.bbs.entity.dto.TopicUpdateDTO;
import com.sjxy.bbs.entity.po.TopicPO;
import com.sjxy.bbs.entity.po.TopicPublishStatisticPO;
import com.sjxy.bbs.entity.query.TopicQuery;

import java.util.List;

public interface TopicService {
    Long getAllTopicCount();

    Page<TopicPO> query(Integer pageNum, Integer pageSize, TopicQuery record);

    Page<TopicBO> queryBO(Integer pageNum, Integer pageSize, TopicQuery record);

    Page<TopicBO> queryUserTopic(Integer pageNum, Integer pageSize);

    /**
     * 根据用户ID查询其发布的帖子
     *
     * @param pageNum  页码
     * @param pageSize 页大小
     * @param userId   用户ID
     * @return 用户发布的帖子的分页数据
     */
    Page<TopicBO> queryUserTopicByUserId(Integer pageNum, Integer pageSize, Long userId);

    /**
     * 更新帖子的最后一条评论数据
     *
     * @param topicId 帖子ID
     */
    void updateTopicLastCommentInfo(Long topicId);

    /**
     * 获取单条数据
     *
     * @param id 帖子ID
     * @return 帖子数据
     */

    TopicPO get(Long id);

    /**
     * 发布帖子
     *
     * @param record 前端传参
     * @return 返回新增帖子的主键ID
     */
    Long publishTopic(TopicPublishDTO record);


    /**
     * 获取向用户展示的帖子数据
     *
     * @param id 帖子主键ID
     * @return 帖子数据
     */
    TopicBO view(Long id);


    /**
     * 更新
     *
     * @param topicPO 更新数据
     */
    void update(TopicPO topicPO);

    void update(TopicUpdateDTO record);

    void like(Long topicId);

    void collect(Long topicId);

    void unLike(Long topicId);

    void unCollect(Long topicId);

    Page<TopicBO> queryLatestTopic(Integer pageNum, Integer pageSize, Long tagId);

    Page<TopicBO> queryHotTopic(Integer pageNum, Integer pageSize, Long tagId);

    Page<TopicBO> queryRecommendTopic(Integer pageNum, Integer pageSize, Long tagId);

    void increaseTopicViewCount(Long topicId,Integer count);

    List<TopicPublishStatisticPO> getAllTopicPublishStatistic();

    void delete(Long id);


    List<TopicPO> list(TopicQuery record);

    void stick(Long topicId);
    void unStick(Long topicId);

    void lock(Long topicId);
    void unLock(Long topicId);

    void recommend(Long topicId);
    void unRecommend(Long topicId);

    void deleteByUserId(Long userId);
}
