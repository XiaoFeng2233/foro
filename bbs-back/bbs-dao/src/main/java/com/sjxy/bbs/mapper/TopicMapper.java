package com.sjxy.bbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.po.TopicPO;
import com.sjxy.bbs.entity.po.TopicPublishStatisticPO;
import com.sjxy.bbs.entity.query.TopicQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicMapper extends BaseMapper<TopicPO> {
    TopicPO get(@Param("id") Long id);

    Page<TopicPO> query(Page<TopicPO> page, @Param("record") TopicQuery record);

    Page<TopicPO> queryLatestTopic(Page<TopicPO> page, @Param("tagId") Long tagId);

    Page<TopicPO> queryHotTopic(Page<TopicPO> page, @Param("tagId") Long tagId);

    Page<TopicPO> queryRecommendTopic(Page<TopicPO> page, @Param("tagId") Long tagId);

    List<TopicPublishStatisticPO> getAllTopicPublishStatistic();

}




