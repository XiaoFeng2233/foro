package com.sjxy.bbs.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.context.WebContext;
import com.sjxy.bbs.entity.bo.TagBO;
import com.sjxy.bbs.entity.bo.TopicBO;
import com.sjxy.bbs.entity.bo.UserBO;
import com.sjxy.bbs.entity.bo.UserTokenBO;
import com.sjxy.bbs.entity.constants.RedisConstants;
import com.sjxy.bbs.entity.constants.TopicConstants;
import com.sjxy.bbs.entity.dto.TopicPublishDTO;
import com.sjxy.bbs.entity.dto.TopicUpdateDTO;
import com.sjxy.bbs.entity.enums.YesOrNoEnum;
import com.sjxy.bbs.entity.mq.*;
import com.sjxy.bbs.entity.po.*;
import com.sjxy.bbs.entity.query.TopicQuery;
import com.sjxy.bbs.entity.query.UserCollectRelateQuery;
import com.sjxy.bbs.entity.query.UserLikeRelateQuery;
import com.sjxy.bbs.entity.query.UserQuery;
import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.mapper.TopicMapper;
import com.sjxy.bbs.service.*;
import com.sjxy.bbs.util.IpUtil;
import com.sjxy.bbs.util.MQUtil;
import com.sjxy.bbs.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TopicServiceImpl extends BaseService implements TopicService {

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IpUtil ipUtil;
    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private MQUtil mqUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private UserCollectRelateService userCollectRelateService;
    @Autowired
    private UserLikeRelateService userLikeRelateService;
    @Autowired
    private CommentService commentService;


    @Override
    public Long getAllTopicCount() {
        return topicMapper.selectCount(null);
    }

    @Override
    public Page<TopicPO> query(Integer pageNum, Integer pageSize, TopicQuery record) {
        Assert.notNull(pageNum, "pageNum不能为空");
        Assert.notNull(pageSize, "pageSize不能为空");
        Page<TopicPO> page = new Page<>(pageNum, pageSize);
        return topicMapper.query(page, record);
    }

    @Override
    public Page<TopicBO> queryBO(Integer pageNum, Integer pageSize, TopicQuery record) {
        Assert.notNull(pageNum, "pageNum不能为空");
        Assert.notNull(pageSize, "pageSize不能为空");
        Page<TopicPO> topicPOPage = query(pageNum, pageSize, record);
        Page<TopicBO> topicBOPage = new Page<>();
        BeanUtils.copyProperties(topicPOPage, topicBOPage);
        List<TopicBO> list = topicPOPage.getRecords().stream().map(topicPO -> {
            TopicBO topicBO = new TopicBO();
            BeanUtils.copyProperties(topicPO, topicBO);

            TagBO tagBO = new TagBO();
            BeanUtils.copyProperties(topicPO.getTag(), tagBO);
            topicBO.setTag(tagBO);
            topicBO.setIpA(topicPO.getIp());
            topicBO.setLocationA(topicPO.getIpLocation());

            UserBO userBO = new UserBO();
            BeanUtils.copyProperties(topicPO.getUser(), userBO);
            topicBO.setUser(userBO);
            return topicBO;
        }).toList();
        topicBOPage.setRecords(list);
        return topicBOPage;

    }

    @Override
    public Page<TopicBO> queryUserTopic(Integer pageNum, Integer pageSize) {
        Assert.notNull(pageNum, "pageNum不能为空");
        Assert.notNull(pageSize, "pageSize不能为空");
        TopicQuery topicQuery = new TopicQuery();
        topicQuery.setUserId(getUserId());
        Page<TopicPO> topicPOPage = query(pageNum, pageSize, topicQuery);

        Page<TopicBO> topicBOPage = new Page<>();

        BeanUtils.copyProperties(topicPOPage, topicBOPage);
        List<TopicBO> list = topicPOPage.getRecords().stream().map(topicPO -> {
            TopicBO topicBO = new TopicBO();
            BeanUtils.copyProperties(topicPO, topicBO);

            TagBO tagBO = new TagBO();
            BeanUtils.copyProperties(topicPO.getTag(), tagBO);
            topicBO.setTag(tagBO);

            UserBO userBO = new UserBO();
            BeanUtils.copyProperties(topicPO.getUser(), userBO);
            topicBO.setUser(userBO);
            return topicBO;
        }).toList();

        topicBOPage.setRecords(list);

        return topicBOPage;
    }

    @Override
    public Page<TopicBO> queryUserTopicByUserId(Integer pageNum, Integer pageSize, Long userId) {
        Assert.notNull(userId, "用户id不能为空");

        Assert.notNull(pageNum, "pageNum不能为空");
        Assert.notNull(pageSize, "pageSize不能为空");
        TopicQuery topicQuery = new TopicQuery();
        topicQuery.setUserId(userId);
        Page<TopicPO> topicPOPage = query(pageNum, pageSize, topicQuery);

        Page<TopicBO> topicBOPage = new Page<>();

        BeanUtils.copyProperties(topicPOPage, topicBOPage);
        List<TopicBO> list = topicPOPage.getRecords().stream().map(topicPO -> {
            TopicBO topicBO = new TopicBO();
            BeanUtils.copyProperties(topicPO, topicBO);

            TagBO tagBO = new TagBO();
            BeanUtils.copyProperties(topicPO.getTag(), tagBO);
            topicBO.setTag(tagBO);

            UserBO userBO = new UserBO();
            BeanUtils.copyProperties(topicPO.getUser(), userBO);
            topicBO.setUser(userBO);
            return topicBO;
        }).toList();

        topicBOPage.setRecords(list);

        return topicBOPage;
    }

    @Override
    public void updateTopicLastCommentInfo(Long topicId) {
        Assert.notNull(topicId, "帖子id不能为空");

        TopicPO topicPO = topicMapper.get(topicId);
        topicPO.setLastCommentUserId(getUserId());
        topicPO.setLastCommentTime(new Date());
        topicMapper.updateById(topicPO);
    }

    @Override
    public TopicPO get(Long id) {
        Assert.notNull(id, "帖子id不能为空");

        TopicPO topicPO = topicMapper.get(id);
        return topicPO;
    }

    @Override
    public Long publishTopic(TopicPublishDTO record) {

        //校验参数
        verifyPublishParam(record);

        //验证板块是否存在
        verifyTagExist(record.getTagId());

        TopicPO topicPO = new TopicPO();

        topicPO.setIp(WebContext.getIp());
        topicPO.setIpLocation(ipUtil.getIpLocation(WebContext.getIp()));

        topicPO.setContent(record.getContent());
        topicPO.setSummary(record.getSummary());
        topicPO.setTagId(record.getTagId());
        topicPO.setTitle(record.getTitle());

        topicPO.setCommentCount(TopicConstants.DEFAULT_COMMENT_COUNT);
        topicPO.setViewCount(TopicConstants.DEFAULT_VIEW_COUNT);
        topicPO.setLikeCount(TopicConstants.DEFAULT_LIKE_COUNT);
        topicPO.setCollectCount(TopicConstants.DEFAULT_COLLECT_COUNT);

        topicPO.setIsLock(record.getLock());
        topicPO.setLockTime(new Date());

        topicPO.setRequireScore(record.getScoreRequire());

        topicPO.setStatus(YesOrNoEnum.YES.getCode());
        topicPO.setRecommend(YesOrNoEnum.NO.getCode());
        topicPO.setRecommendTime(null);

        topicPO.setLastCommentTime(null);
        topicPO.setLastCommentUserId(null);
        topicPO.setStickyTime(null);
        topicPO.setSticky(YesOrNoEnum.NO.getCode());

        topicPO.setCreateBy(getUserId());
        topicPO.setCreateTime(new Date());
        topicPO.setCreateUsername(getUsername());
        topicPO.setExtraData(null);
        topicPO.setUserId(getUserId());

        if (CollUtil.isNotEmpty(record.getImageFileIds())) {
            List<UploadFilePO> uploadFileList = uploadFileService.listByFileIds(record.getImageFileIds());
            List<String> collect = uploadFileList.stream().map(UploadFilePO::getUrl).collect(Collectors.toList());
            String imageList = JSONObject.toJSONString(collect);
            topicPO.setImageList(imageList);
        }

        topicMapper.insert(topicPO);

        //让rabbitmq通知关注该用户的所有用户消息
        NotifyFansNewTopicMessage notifyMessage = new NotifyFansNewTopicMessage();
        notifyMessage.setTopicId(topicPO.getId());
        notifyMessage.setCreatorUserId(getUserId());
        mqUtil.send("NoticeExchange.direct", "notify.fans.new.topic", notifyMessage);


        //让rabbitmq通知增加用户表中的主题数量
        UpdateUserTopicCountMessage updateUserTopicCountMessage = new UpdateUserTopicCountMessage();
        updateUserTopicCountMessage.setCount(1);
        updateUserTopicCountMessage.setUserId(getUserId());
        mqUtil.send("UserExchange.direct", "update.user.topic.count", updateUserTopicCountMessage);

        //让rocketmq通知同步到Melisearch
        SyncTopicToMeilisearchMessage syncMessage = new SyncTopicToMeilisearchMessage();
        syncMessage.setContent(topicPO.getContent());
        syncMessage.setSummary(topicPO.getSummary());
        syncMessage.setId(topicPO.getId());
        syncMessage.setTitle(topicPO.getTitle());
        syncMessage.setPublishTime(new Date());
        syncMessage.setPublisherUserNickName(getNickname());
        syncMessage.setPublisherUserId(getUserId());
        mqUtil.send("TopicExchange.direct", "sync.topic.meilisearch", syncMessage);
        return topicPO.getId();
    }

    @Override
    public TopicBO view(Long id) {
        Assert.notNull(id, "帖子id不能为空");
        TopicPO topicPO = topicMapper.get(id);

        //判断帖子是否存在
        Assert.isTrue(topicPO != null, "帖子不存在");

        //每次请求都增加一次帖子的访问量
        redisUtil.hIncrBy(RedisConstants.INCREASE_TOPIC_VIEW_COUNT, String.valueOf(id), 1);

        //判断帖子是否可见
        Assert.isTrue(!topicPO.getStatus().equals(YesOrNoEnum.NO.getCode()), "帖子不可见");

        //判断是否需要积分要求才能查看
        if (topicPO.getRequireScore() != null && topicPO.getRequireScore() > 0) {

            //判断是否登录
            if (!StpUtil.isLogin()) {
                throw new RuntimeException("您还没有登录，无权查看");
            }

            UserQuery userQuery = new UserQuery();
            userQuery.setId(getUserId());
            UserPO userPO = userService.get(userQuery);
            if (userPO.getScore() < topicPO.getRequireScore()) {
                throw new RuntimeException("您的积分不足，无权查看");
            }


        }

        TopicBO topicBO = new TopicBO();
        topicBO.setUser(new UserBO());
        topicBO.setTag(new TagBO());
        topicBO.setLiked(false);
        topicBO.setCollected(false);

        BeanUtils.copyProperties(topicPO, topicBO);

        BeanUtils.copyProperties(topicPO.getUser(), topicBO.getUser());
        BeanUtils.copyProperties(topicPO.getTag(), topicBO.getTag());

        //判断当前登录的用户是否收藏和点赞
        if (StpUtil.isLogin()) {
            UserCollectRelateQuery userCollectRelateQuery = new UserCollectRelateQuery();
            userCollectRelateQuery.setUserId(getUserId());
            userCollectRelateQuery.setTopicId(id);
            List<UserCollectRelatePO> userCollectRelatePOList = userCollectRelateService.list(userCollectRelateQuery);
            if (CollUtil.isNotEmpty(userCollectRelatePOList)) topicBO.setCollected(true);

            UserLikeRelateQuery userLikeRelateQuery = new UserLikeRelateQuery();
            userLikeRelateQuery.setTopicId(id);
            userLikeRelateQuery.setUserId(getUserId());
            List<UserLikeRelatePO> userLikeRelatePOList = userLikeRelateService.list(userLikeRelateQuery);
            if (CollUtil.isNotEmpty(userLikeRelatePOList)) topicBO.setLiked(true);
        }

        return topicBO;
    }

    @Override
    public void update(TopicPO topicPO) {
        topicMapper.updateById(topicPO);
    }

    @Override
    public void update(TopicUpdateDTO record) {
        Assert.isTrue(record.getId() != null, "帖子id不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getTitle()), "帖子标题不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getContent()), "帖子内容不能为空");
        Assert.isTrue(record.getSticky() != null, "帖子置顶状态不能为空");
        Assert.isTrue(record.getRecommend() != null, "帖子推荐状态不能为空");
        Assert.isTrue(record.getIsLock() != null, "帖子是否锁定状态不能为空");

        TopicPO topicPO = topicMapper.get(record.getId());
        Assert.isTrue(topicPO != null, "帖子不存在");

        BeanUtils.copyProperties(record, topicPO);
        topicPO.setUpdateBy(getUserId());
        topicPO.setUpdateUsername(getUsername());
        topicPO.setUpdateTime(new Date());

        topicMapper.updateById(topicPO);
    }

    @Override
    public void like(Long topicId) {
        Assert.notNull(topicId, "帖子id不能为空");
        TopicPO topicPO = topicMapper.get(topicId);
        Assert.isTrue(topicPO != null, "帖子不存在");

        UserLikeRelateQuery userLikeRelateQuery = new UserLikeRelateQuery();
        userLikeRelateQuery.setUserId(getUserId());
        userLikeRelateQuery.setTopicId(topicId);
        UserLikeRelatePO emptyPO = userLikeRelateService.get(userLikeRelateQuery);
        Assert.isTrue(emptyPO == null, "您已经点过赞了");

        //让rabbitmq添加帖子点赞数
        UpdateTopicLikeCountMessage updateTopicLikeCountMessage = new UpdateTopicLikeCountMessage();
        updateTopicLikeCountMessage.setCount(1);
        updateTopicLikeCountMessage.setTopicId(topicId);
        mqUtil.send("TopicExchange.direct", "update.topic.like.count", updateTopicLikeCountMessage);

        UserLikeRelatePO userLikeRelatePO = new UserLikeRelatePO();
        userLikeRelatePO.setUserId(getUserId());
        userLikeRelatePO.setTopicId(topicId);
        userLikeRelatePO.setCreateBy(getUserId());
        userLikeRelatePO.setCreateTime(new Date());
        userLikeRelatePO.setCreateUsername(getUsername());
        userLikeRelateService.add(userLikeRelatePO);

        //让rabbitmq通知作者有人点赞

        NotifyLikeTopicMessage message = new NotifyLikeTopicMessage();
        message.setTopicId(topicId);
        message.setUserId(getUserId());
        mqUtil.send("NoticeExchange.direct", "notify.topic.like", message);


    }

    @Override
    public void collect(Long topicId) {
        Assert.notNull(topicId, "帖子id不能为空");
        TopicPO topicPO = topicMapper.get(topicId);
        Assert.isTrue(topicPO != null, "帖子不存在");

        UserCollectRelateQuery userCollectRelateQuery = new UserCollectRelateQuery();
        userCollectRelateQuery.setUserId(getUserId());
        userCollectRelateQuery.setTopicId(topicId);
        UserCollectRelatePO emptyPO = userCollectRelateService.get(userCollectRelateQuery);
        Assert.isTrue(emptyPO == null, "您已经收藏过了");

        //让rabbitmq添加帖子收藏数
        UpdateTopicCollectCountMessage updateTopicCollectCountMessage = new UpdateTopicCollectCountMessage();
        updateTopicCollectCountMessage.setCount(1);
        updateTopicCollectCountMessage.setTopicId(topicId);
        mqUtil.send("TopicExchange.direct", "update.topic.collect.count", updateTopicCollectCountMessage);

        //让rabbitmq添加用户收藏数
        UpdateUserCollectCountMessage updateUserCollectCountMessage = new UpdateUserCollectCountMessage();
        updateUserCollectCountMessage.setUserId(getUserId());
        updateUserCollectCountMessage.setCount(1);
        mqUtil.send("UserExchange.direct", "update.user.collect.count", updateUserCollectCountMessage);

        UserCollectRelatePO userCollectRelatePO = new UserCollectRelatePO();
        userCollectRelatePO.setUserId(getUserId());
        userCollectRelatePO.setTopicId(topicId);
        userCollectRelatePO.setCreateTime(new Date());
        userCollectRelatePO.setCreateBy(getUserId());
        userCollectRelatePO.setCreateUsername(getUsername());
        userCollectRelateService.add(userCollectRelatePO);

        //让rabbitmq通知作者有人收藏
        NotifyCollectTopicMessage message = new NotifyCollectTopicMessage();
        message.setTopicId(topicId);
        message.setUserId(getUserId());
        mqUtil.send("NoticeExchange.direct", "notify.topic.collect", message);

    }

    @Override
    public void unLike(Long topicId) {
        Assert.notNull(topicId, "帖子id不能为空");
        TopicPO topicPO = topicMapper.get(topicId);
        Assert.isTrue(topicPO != null, "帖子不存在");

        UserLikeRelateQuery userLikeRelateQuery = new UserLikeRelateQuery();
        userLikeRelateQuery.setUserId(getUserId());
        userLikeRelateQuery.setTopicId(topicId);
        UserLikeRelatePO userLikeRelatePO = userLikeRelateService.get(userLikeRelateQuery);
        Assert.isTrue(userLikeRelatePO != null, "您还没有收藏该帖子");

        //让rabbitmq减少帖子点赞数量
        UpdateTopicLikeCountMessage updateTopicLikeCountMessage = new UpdateTopicLikeCountMessage();
        updateTopicLikeCountMessage.setTopicId(topicId);
        updateTopicLikeCountMessage.setCount(-1);
        mqUtil.send("TopicExchange.direct", "update.topic.like.count", updateTopicLikeCountMessage);

        userLikeRelateService.delete(userLikeRelatePO.getId());

        //让rabbitmq通知作者有人取消点赞
        NotifyUnLikeTopicMessage message = new NotifyUnLikeTopicMessage();
        message.setTopicId(topicId);
        message.setUserId(getUserId());
        mqUtil.send("NoticeExchange.direct", "notify.topic.unlike", message);
    }

    @Override
    public void unCollect(Long topicId) {
        Assert.notNull(topicId, "帖子id不能为空");
        TopicPO topicPO = topicMapper.get(topicId);
        Assert.isTrue(topicPO != null, "帖子不存在");

        UserCollectRelateQuery userCollectRelateQuery = new UserCollectRelateQuery();
        userCollectRelateQuery.setUserId(getUserId());
        userCollectRelateQuery.setTopicId(topicId);
        UserCollectRelatePO userCollectRelatePO = userCollectRelateService.get(userCollectRelateQuery);
        Assert.isTrue(userCollectRelatePO != null, "您还没有收藏该帖子");

        //让rabbitmq减少帖子收藏数
        UpdateTopicCollectCountMessage updateTopicCollectCountMessage = new UpdateTopicCollectCountMessage();
        updateTopicCollectCountMessage.setCount(-1);
        updateTopicCollectCountMessage.setTopicId(topicId);
        mqUtil.send("TopicExchange.direct", "update.topic.collect.count", updateTopicCollectCountMessage);

        //让rabbitmq减少用户收藏数
        UpdateUserCollectCountMessage updateUserCollectCountMessage = new UpdateUserCollectCountMessage();
        updateUserCollectCountMessage.setUserId(getUserId());
        updateUserCollectCountMessage.setCount(-1);
        mqUtil.send("UserExchange.direct", "update.user.collect.count", updateUserCollectCountMessage);


        userCollectRelateService.delete(userCollectRelatePO.getId());

        //让rabbitmq通知作者有人取消收藏
        NotifyUnCollectTopicMessage message = new NotifyUnCollectTopicMessage();
        message.setTopicId(topicId);
        message.setUserId(getUserId());
        mqUtil.send("NoticeExchange.direct", "notify.topic.uncollect", message);
    }

    @Override
    public Page<TopicBO> queryLatestTopic(Integer pageNum, Integer pageSize, Long tagId) {
        Assert.notNull(pageNum, "pageNum不能为空");
        Assert.notNull(pageSize, "pageSize不能为空");
        Page<TopicPO> page = new Page<>(pageNum, pageSize);
        Page<TopicPO> topicPOPage = topicMapper.queryLatestTopic(page, tagId);
        Page<TopicBO> topicBOPage = new Page<>();
        BeanUtils.copyProperties(topicPOPage, topicBOPage);
        List<TopicBO> list = topicPOPage.getRecords().stream().map(topicPO -> {
            TopicBO topicBO = new TopicBO();
            BeanUtils.copyProperties(topicPO, topicBO);
            if (topicPO.getUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(topicPO.getUser(), userBO);
                topicBO.setUser(userBO);
            }
            if (topicPO.getTag() != null) {
                TagBO tagBO = new TagBO();
                BeanUtils.copyProperties(topicPO.getTag(), tagBO);
                topicBO.setTag(tagBO);
            }
            return topicBO;
        }).toList();
        topicBOPage.setRecords(list);
        return topicBOPage;
    }

    @Override
    public Page<TopicBO> queryHotTopic(Integer pageNum, Integer pageSize, Long tagId) {
        Assert.notNull(pageNum, "pageNum不能为空");
        Assert.notNull(pageSize, "pageSize不能为空");

        Page<TopicPO> page = new Page<>(pageNum, pageSize);
        Page<TopicPO> topicPOPage = topicMapper.queryHotTopic(page, tagId);
        Page<TopicBO> topicBOPage = new Page<>();
        BeanUtils.copyProperties(topicPOPage, topicBOPage);
        List<TopicBO> list = topicPOPage.getRecords().stream().map(topicPO -> {
            TopicBO topicBO = new TopicBO();
            BeanUtils.copyProperties(topicPO, topicBO);
            if (topicPO.getUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(topicPO.getUser(), userBO);
                topicBO.setUser(userBO);
            }
            if (topicPO.getTag() != null) {
                TagBO tagBO = new TagBO();
                BeanUtils.copyProperties(topicPO.getTag(), tagBO);
                topicBO.setTag(tagBO);
            }
            return topicBO;
        }).toList();
        topicBOPage.setRecords(list);
        return topicBOPage;
    }

    @Override
    public Page<TopicBO> queryRecommendTopic(Integer pageNum, Integer pageSize, Long tagId) {
        Assert.notNull(pageNum, "pageNum不能为空");
        Assert.notNull(pageSize, "pageSize不能为空");
        Page<TopicPO> page = new Page<>(pageNum, pageSize);
        Page<TopicPO> topicPOPage = topicMapper.queryRecommendTopic(page, tagId);
        Page<TopicBO> topicBOPage = new Page<>();
        BeanUtils.copyProperties(topicPOPage, topicBOPage);
        List<TopicBO> list = topicPOPage.getRecords().stream().map(topicPO -> {
            TopicBO topicBO = new TopicBO();
            BeanUtils.copyProperties(topicPO, topicBO);
            if (topicPO.getUser() != null) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(topicPO.getUser(), userBO);
                topicBO.setUser(userBO);
            }
            if (topicPO.getTag() != null) {
                TagBO tagBO = new TagBO();
                BeanUtils.copyProperties(topicPO.getTag(), tagBO);
                topicBO.setTag(tagBO);
            }
            return topicBO;
        }).toList();
        topicBOPage.setRecords(list);
        return topicBOPage;
    }

    @Override
    public void increaseTopicViewCount(Long topicId, Integer count) {
        Assert.isTrue(topicId != null, "帖子id不能为空");
        Assert.isTrue(count != null, "增加数量不能为空");

        UpdateWrapper<TopicPO> wrapper = new UpdateWrapper<>();
        wrapper.setSql("view_count = view_count + " + count);
        wrapper.eq("id", topicId);
        topicMapper.update(wrapper);
    }

    @Override
    public List<TopicPublishStatisticPO> getAllTopicPublishStatistic() {
        LocalDate today = LocalDate.now();
        List<String> dateList = IntStream.rangeClosed(1, 30)
                .mapToObj(today::minusDays).map(date -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    return date.format(formatter);
                }).toList();
        List<TopicPublishStatisticPO> statisticList = topicMapper.getAllTopicPublishStatistic();
        Map<String, TopicPublishStatisticPO> collect =
                statisticList.stream().collect(Collectors.toMap(TopicPublishStatisticPO::getTime, item -> item));

        dateList.forEach(date -> {
            if (!collect.containsKey(date)) {
                TopicPublishStatisticPO topicPublishStatisticPO = new TopicPublishStatisticPO();
                topicPublishStatisticPO.setTime(date);
                topicPublishStatisticPO.setCount(0);
                statisticList.add(topicPublishStatisticPO);
            }
        });

        List<TopicPublishStatisticPO> list = statisticList.stream().sorted(TopicPublishStatisticPO::compareTo).toList();
        return list;
    }

    @Override
    public void delete(Long id) {
        topicMapper.deleteById(id);
        commentService.deleteByTopicId(id);
        userLikeRelateService.deleteByTopicId(id);
        userCollectRelateService.deleteByTopicId(id);
    }


    @Override
    public List<TopicPO> list(TopicQuery record) {
        return null;
    }

    @Override
    public void stick(Long topicId) {
        Long userId = getUserId();
        String result = (String) redisUtil.hGet(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(userId));
        UserTokenBO userTokenBO = JSONObject.parseObject(result, UserTokenBO.class);
        TopicPO topicPO = get(topicId);
        Assert.isTrue(topicPO != null, "帖子不存在");

        Assert.isTrue(userTokenBO.getMangedTagIds().contains(topicPO.getTagId()) || userTokenBO.getRoles().stream().anyMatch(role -> role.getName().equals("admin")), "无权访问");

        topicPO.setSticky(YesOrNoEnum.YES.getCode());

        topicMapper.updateById(topicPO);
    }

    @Override
    public void unStick(Long topicId) {
        Long userId = getUserId();
        String result = (String) redisUtil.hGet(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(userId));
        UserTokenBO userTokenBO = JSONObject.parseObject(result, UserTokenBO.class);
        TopicPO topicPO = get(topicId);
        Assert.isTrue(topicPO != null, "帖子不存在");

        Assert.isTrue(userTokenBO.getMangedTagIds().contains(topicPO.getTagId()) || userTokenBO.getRoles().stream().anyMatch(role -> role.getName().equals("admin")), "无权访问");

        topicPO.setSticky(YesOrNoEnum.NO.getCode());

        topicMapper.updateById(topicPO);
    }

    @Override
    public void lock(Long topicId) {
        Long userId = getUserId();
        String result = (String) redisUtil.hGet(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(userId));
        UserTokenBO userTokenBO = JSONObject.parseObject(result, UserTokenBO.class);
        TopicPO topicPO = get(topicId);
        Assert.isTrue(topicPO != null, "帖子不存在");

        Assert.isTrue(userTokenBO.getMangedTagIds().contains(topicPO.getTagId()) || userTokenBO.getRoles().stream().anyMatch(role -> role.getName().equals("admin")), "无权访问");

        topicPO.setIsLock(YesOrNoEnum.YES.getCode());

        topicMapper.updateById(topicPO);
    }

    @Override
    public void unLock(Long topicId) {
        Long userId = getUserId();
        String result = (String) redisUtil.hGet(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(userId));
        UserTokenBO userTokenBO = JSONObject.parseObject(result, UserTokenBO.class);
        TopicPO topicPO = get(topicId);
        Assert.isTrue(topicPO != null, "帖子不存在");

        Assert.isTrue(userTokenBO.getMangedTagIds().contains(topicPO.getTagId()) || userTokenBO.getRoles().stream().anyMatch(role -> role.getName().equals("admin")), "无权访问");

        topicPO.setIsLock(YesOrNoEnum.NO.getCode());

        topicMapper.updateById(topicPO);
    }

    @Override
    public void recommend(Long topicId) {
        Long userId = getUserId();
        String result = (String) redisUtil.hGet(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(userId));
        UserTokenBO userTokenBO = JSONObject.parseObject(result, UserTokenBO.class);
        TopicPO topicPO = get(topicId);
        Assert.isTrue(topicPO != null, "帖子不存在");

        Assert.isTrue(userTokenBO.getMangedTagIds().contains(topicPO.getTagId()) || userTokenBO.getRoles().stream().anyMatch(role -> role.getName().equals("admin")), "无权访问");

        topicPO.setRecommend(YesOrNoEnum.YES.getCode());

        topicMapper.updateById(topicPO);
    }

    @Override
    public void unRecommend(Long topicId) {
        Long userId = getUserId();
        String result = (String) redisUtil.hGet(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(userId));
        UserTokenBO userTokenBO = JSONObject.parseObject(result, UserTokenBO.class);
        TopicPO topicPO = get(topicId);
        Assert.isTrue(topicPO != null, "帖子不存在");

        Assert.isTrue(userTokenBO.getMangedTagIds().contains(topicPO.getTagId()) || userTokenBO.getRoles().stream().anyMatch(role -> role.getName().equals("admin")), "无权访问");

        topicPO.setRecommend(YesOrNoEnum.NO.getCode());

        topicMapper.updateById(topicPO);
    }

    @Override
    public void deleteByUserId(Long userId) {
        Assert.isTrue(userId != null, "userId不能为空");
        QueryWrapper<TopicPO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        topicMapper.delete(wrapper);

    }

    private void verifyPublishParam(TopicPublishDTO record) {
        Assert.isTrue(record != null, "非法请求");
        Assert.isTrue(StrUtil.isNotBlank(record.getContent()), "内容不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getSummary()), "内容不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getTitle()), "标题不能为空");
        Assert.isTrue(record.getTitle().length() < TopicConstants.TOPIC_TITLE_LENGTH, "标题内容过长,不得超过50字符");
        Assert.isTrue(record.getTagId() != null, "板块不能为空");
        Assert.isTrue(record.getLock() != null, "是否锁定不能为空");
        Assert.isTrue(record.getScoreRequire() != null, "积分要求不能为空");
    }

    private void verifyTagExist(Long tagId) {
        TagPO tagPO = tagService.get(tagId);
        Assert.isTrue(tagPO != null, "论坛板块不存在");
    }
}
