package com.sjxy.bbs.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSONObject;
import com.sjxy.bbs.entity.bo.CommentBO;
import com.sjxy.bbs.entity.bo.UserTokenBO;
import com.sjxy.bbs.entity.constants.RedisConstants;
import com.sjxy.bbs.entity.dto.CommentPublishDTO;
import com.sjxy.bbs.entity.po.CommentPO;
import com.sjxy.bbs.entity.po.TopicPO;
import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.entity.result.PageResult;
import com.sjxy.bbs.service.CommentService;
import com.sjxy.bbs.service.TopicService;
import com.sjxy.bbs.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TopicService topicService;


    @GetMapping("/query-user-comment-list")
    @SaCheckLogin
    public PageResult<CommentBO> queryUserCommentList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(commentService.queryUserCommentList(pageNum, pageSize));
    }

    @GetMapping("/query-user-comment-list-by-user-id")
    @SaCheckLogin
    public PageResult<CommentBO> queryUserCommentListByUserId(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("userId") Long userId) {
        return PageResult.ok(commentService.queryUserCommentListByUserId(pageNum, pageSize, userId));
    }

    @GetMapping("/query-topic-comment")
    public PageResult<CommentBO> queryTopicComment(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("topicId") Long topicId) {
        return PageResult.ok(commentService.queryTopicComment(pageNum, pageSize, topicId));
    }

    @PostMapping("/publish-comment")
    @SaCheckLogin
    public ObjectResult<String> publishComment(@RequestBody CommentPublishDTO record) {
        commentService.publishComment(record);
        return ObjectResult.ok("评论发布成功");
    }

    @GetMapping("/delete")
    @SaCheckLogin
    @SaCheckRole(value = {"admin", "topic_manager"}, mode = SaMode.OR)
    public ObjectResult<String> delete(@RequestParam("id") Long id) {
        long userId = StpUtil.getLoginIdAsLong();
        String result = (String) redisUtil.hGet(RedisConstants.HASH_USER_TOKEN_INFO, String.valueOf(userId));
        UserTokenBO userTokenBO = JSONObject.parseObject(result, UserTokenBO.class);
        CommentPO commentPO = commentService.get(id);
        Assert.isTrue(commentPO != null, "评论不存在");

        boolean contains = userTokenBO.getMangedTagIds().contains(commentPO.getTopic().getTagId());
        if (contains) {
            commentService.delete(id);
            return ObjectResult.ok("删除成功");
        } else {
            StpUtil.kickout(userId);
            return ObjectResult.forbidden("无权访问");
        }
    }
}
