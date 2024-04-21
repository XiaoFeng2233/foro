package com.sjxy.bbs.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSONObject;
import com.sjxy.bbs.entity.bo.SearchTopicBO;
import com.sjxy.bbs.entity.bo.TopicBO;
import com.sjxy.bbs.entity.bo.UserTokenBO;
import com.sjxy.bbs.entity.constants.RedisConstants;
import com.sjxy.bbs.entity.constants.TopicConstants;
import com.sjxy.bbs.entity.dto.TopicPublishDTO;
import com.sjxy.bbs.entity.po.TopicPO;
import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.entity.result.PageResult;
import com.sjxy.bbs.entity.result.SearchResult;
import com.sjxy.bbs.service.MeiliSearchService;
import com.sjxy.bbs.service.TopicService;
import com.sjxy.bbs.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private MeiliSearchService meiliSearchService;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/search")
    public SearchResult<SearchTopicBO> search(@RequestParam("param")String param,@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        return meiliSearchService.search(TopicConstants.MEILISEARCH_INDEX_NAME,param,pageNum,pageSize);
    }

    @PostMapping("/publish")
    @SaCheckLogin
    public ObjectResult<Long> publishTopic(@RequestBody TopicPublishDTO record) {
        return ObjectResult.ok("主题发布成功", topicService.publishTopic(record));
    }

    @GetMapping("/view")
    public ObjectResult<TopicBO> view(@RequestParam("id") Long id) {
        return ObjectResult.ok(topicService.view(id));
    }

    @GetMapping("/like")
    public ObjectResult<String> like(@RequestParam("topicId") Long topicId) {
        topicService.like(topicId);
        return ObjectResult.ok("点赞成功");
    }

    @GetMapping("/collect")
    public ObjectResult<String> collect(@RequestParam("topicId") Long topicId) {
        topicService.collect(topicId);
        return ObjectResult.ok("收藏成功");
    }

    @GetMapping("/unLike")
    public ObjectResult<String> unLike(@RequestParam("topicId") Long topicId) {
        topicService.unLike(topicId);
        return ObjectResult.ok("取消点赞成功");
    }

    @GetMapping("/unCollect")
    public ObjectResult<String> unCollect(@RequestParam("topicId") Long topicId) {
        topicService.unCollect(topicId);
        return ObjectResult.ok("取消点赞成功");
    }

    @GetMapping("/query-latest-topic")
    public PageResult<TopicBO> queryLatestTopic(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(topicService.queryLatestTopic(pageNum, pageSize, null));
    }

    @GetMapping("/query-recommend-topic")
    public PageResult<TopicBO> queryRecommendTopic(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(topicService.queryRecommendTopic(pageNum, pageSize, null));
    }

    @GetMapping("/query-hot-topic")
    public PageResult<TopicBO> queryHotTopic(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(topicService.queryHotTopic(pageNum, pageSize, null));
    }

    @GetMapping("/query-latest-topic-by-tag-id")
    public PageResult<TopicBO> queryLatestTopic(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("tagId") Long tagId) {
        return PageResult.ok(topicService.queryLatestTopic(pageNum, pageSize, tagId));
    }

    @GetMapping("/query-recommend-topic-by-tag-id")
    public PageResult<TopicBO> queryRecommendTopic(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("tagId") Long tagId) {
        return PageResult.ok(topicService.queryRecommendTopic(pageNum, pageSize, tagId));
    }

    @GetMapping("/query-hot-topic-by-tag-id")
    public PageResult<TopicBO> queryHotTopic(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("tagId") Long tagId) {
        return PageResult.ok(topicService.queryHotTopic(pageNum, pageSize, tagId));
    }

    @GetMapping("/delete")
    @SaCheckLogin
    @SaCheckRole(value = {"admin", "topic_manager"}, mode = SaMode.OR)
    public ObjectResult<String> delete(@RequestParam("id")Long id){
        long userId = StpUtil.getLoginIdAsLong();
        String result = (String) redisUtil.hGet(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(userId));
        UserTokenBO userTokenBO = JSONObject.parseObject(result, UserTokenBO.class);
        TopicPO topicPO = topicService.get(id);
        Assert.isTrue(topicPO != null, "帖子不存在");


        if (userTokenBO.getMangedTagIds().contains(topicPO.getTagId())) {
            topicService.delete(id);
            return ObjectResult.ok("删除成功");
        } else if (userTokenBO.getRoles().stream().anyMatch(role -> role.getName().equals("admin"))) {
            topicService.delete(id);
            return ObjectResult.ok("删除成功");
        } else {
            StpUtil.kickout(userId);
            return ObjectResult.forbidden("无权访问");
        }
    }


    //TODO 置顶帖子
    @GetMapping("/stick")
    @SaCheckLogin
    @SaCheckRole(value = {"admin", "topic_manager"}, mode = SaMode.OR)
    public ObjectResult<String> stick(@RequestParam("id")Long id){
        topicService.stick(id);
        return ObjectResult.ok("置顶成功");
    }

    //TODO 取消置顶帖子
    @GetMapping("/unstick")
    @SaCheckLogin
    @SaCheckRole(value = {"admin", "topic_manager"}, mode = SaMode.OR)
    public ObjectResult<String> unStick(@RequestParam("id")Long id){
        topicService.unStick(id);
        return ObjectResult.ok("取消置顶成功");
    }

    //TODO 锁定帖子
    @GetMapping("/lock")
    @SaCheckLogin
    @SaCheckRole(value = {"admin", "topic_manager"}, mode = SaMode.OR)
    public ObjectResult<String> lock(@RequestParam("id")Long id){
        topicService.lock(id);
        return ObjectResult.ok("锁定成功");
    }


    //TODO 取消锁定帖子
    @GetMapping("/unlock")
    @SaCheckLogin
    @SaCheckRole(value = {"admin", "topic_manager"}, mode = SaMode.OR)
    public ObjectResult<String> unLock(@RequestParam("id")Long id){
        topicService.unLock(id);
        return ObjectResult.ok("取消锁定成功");
    }

    //TODO 推荐帖子
    @GetMapping("/recommend")
    @SaCheckLogin
    @SaCheckRole(value = {"admin", "topic_manager"}, mode = SaMode.OR)
    public ObjectResult<String> recommend(@RequestParam("id")Long id){
        topicService.recommend(id);
        return ObjectResult.ok("推荐成功");
    }

    //TODO 取消推荐帖子
    @GetMapping("/unrecommend")
    @SaCheckLogin
    @SaCheckRole(value = {"admin", "topic_manager"}, mode = SaMode.OR)
    public ObjectResult<String> unRecommend(@RequestParam("id")Long id){
        topicService.unRecommend(id);
        return ObjectResult.ok("取消推荐成功");
    }


}
