package com.sjxy.bbs.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.sjxy.bbs.entity.bo.UserFollowRelateBO;
import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.entity.result.PageResult;
import com.sjxy.bbs.service.UserFollowRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private UserFollowRelateService userFollowRelateService;
    @GetMapping("/query-user-follow-list")
    @SaCheckLogin
    public PageResult<UserFollowRelateBO> queryUserFollow(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        return PageResult.ok(userFollowRelateService.queryUserFollowList(pageNum,pageSize));
    }

    @GetMapping("/query-user-follow-list-by-user-id")
    @SaCheckLogin
    public PageResult<UserFollowRelateBO> queryUserFollowByUserId(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("userId")Long userId){
        return PageResult.ok(userFollowRelateService.queryUserFollowListByUserId(pageNum,pageSize,userId));
    }

    @GetMapping("/query-user-fans-list")
    @SaCheckLogin
    public PageResult<UserFollowRelateBO> queryUserFans(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        return PageResult.ok(userFollowRelateService.queryUserFansList(pageNum,pageSize));
    }

    @GetMapping("/query-user-fans-list-by-user-id")
    @SaCheckLogin
    public PageResult<UserFollowRelateBO> queryUserFansByUserId(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("userId")Long userId){
        return PageResult.ok(userFollowRelateService.queryUserFansListByUserId(pageNum,pageSize,userId));
    }

    @GetMapping("/unfollow-user")
    public ObjectResult<String> unFollowUser(@RequestParam("targetUserId")Long targetUserId){
        userFollowRelateService.unFollowUser(targetUserId);
        return ObjectResult.ok("取关成功");
    }

    @GetMapping("/follow-user")
    public ObjectResult<String> followUser(@RequestParam("targetUserId")Long targetUserId){
        userFollowRelateService.followUser(targetUserId);
        return ObjectResult.ok("关注成功");
    }
}
