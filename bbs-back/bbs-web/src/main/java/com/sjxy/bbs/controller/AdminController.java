package com.sjxy.bbs.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.sjxy.bbs.entity.bo.*;
import com.sjxy.bbs.entity.dto.*;
import com.sjxy.bbs.entity.po.TopicPublishStatisticPO;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.po.UserRegisterStatisticPO;
import com.sjxy.bbs.entity.query.TopicQuery;
import com.sjxy.bbs.entity.query.UserQuery;
import com.sjxy.bbs.entity.result.ListResult;
import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.entity.result.PageResult;
import com.sjxy.bbs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@SaCheckLogin
@SaCheckRole("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private UserService userService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserTagManageService userTagManageService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/get-statistic")
    public ObjectResult<StatisticBO> getStatistic() {
        return ObjectResult.ok(adminService.getStatistic());
    }

    @GetMapping("/get-user-statistic")
    public ListResult<UserRegisterStatisticPO> getUserStatistic() {
        return ListResult.ok(userService.getAllUserRegisterStatistic());
    }

    @GetMapping("/get-topic-statistic")
    public ListResult<TopicPublishStatisticPO> getTopicStatistic() {
        return ListResult.ok(topicService.getAllTopicPublishStatistic());
    }


    @GetMapping("/get-email-config")
    public ObjectResult<EmailConfigBO> getEmailConfig() {
        return ObjectResult.ok(configService.getEmailConfig());
    }

    @PostMapping("/update-email-config")
    public ObjectResult<String> updateEmailConfig(@RequestBody EmailConfigUpdateDTO record) {
        configService.updateEmailConfig(record);
        return ObjectResult.ok("修改成功");
    }

    @GetMapping("/get-storage-config")
    public ObjectResult<MinioConfigBO> getStorageConfig() {
        return ObjectResult.ok(configService.getMinioConfig());
    }

    @PostMapping("/update-storage-config")
    public ObjectResult<String> updateStorageConfig(@RequestBody StorageConfigUpdateDTO record) {
        configService.updateStorageConfig(record);
        return ObjectResult.ok("修改成功");
    }

    @GetMapping("/get-site-config")
    public ObjectResult<SiteConfigBO> getSiteConfig() {
        return ObjectResult.ok(configService.getSiteConfig());
    }


    @PostMapping("/update-site-config")
    public ObjectResult<String> updateSiteConfig(@RequestBody SiteConfigUpdateDTO record) {
        configService.updateSiteConfig(record);
        return ObjectResult.ok("修改成功");
    }

    @PostMapping("/query-user")
    public PageResult<UserPO> userList(@RequestBody UserQuery userQuery, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(userService.query(userQuery, pageNum, pageSize));
    }

    @PostMapping("/query-tag")
    public PageResult<TagBO> tagList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(tagService.query(pageNum, pageSize));
    }

    @GetMapping("/delete-tag")
    public ObjectResult<String> deleteTag(@RequestParam("id") Long id) {
        tagService.delete(id);
        return ObjectResult.ok("删除成功");
    }

    @PostMapping("/add-tag")
    public ObjectResult<String> addTag(@RequestBody TagAddDTO record) {
        tagService.add(record);
        return ObjectResult.ok("添加成功");
    }

    @PostMapping("/update-tag")
    public ObjectResult<String> addTag(@RequestBody TagUpdateDTO record) {
        tagService.update(record);
        return ObjectResult.ok("修改成功");
    }

    @PostMapping("/mute-user")
    public ObjectResult<String> muteUser(@RequestBody UserMuteDTO record) {
        userService.muteUser(record);
        return ObjectResult.ok("禁言成功");
    }

    @PostMapping("/forbidden-user")
    public ObjectResult<String> forbiddenUser(@RequestBody UserForbiddenDTO record) {
        userService.forbiddenUser(record);
        return ObjectResult.ok("封禁成功");
    }

    @GetMapping("/un-mute-user")
    public ObjectResult<String> unMuteUser(@RequestParam("userId") Long userId) {
        userService.unMuteUser(userId);
        return ObjectResult.ok("解除禁言成功");
    }

    @GetMapping("/un-forbidden-user")
    public ObjectResult<String> unForbiddenUser(@RequestParam("userId") Long userId) {
        userService.unForbiddenUser(userId);
        return ObjectResult.ok("解除封禁成功");
    }

    @PostMapping("/update-user")
    public ObjectResult<String> updateUser(@RequestBody UserUpdateDTO record) {
        userService.update(record);
        return ObjectResult.ok("修改成功");
    }

    @GetMapping("/delete-user")
    public ObjectResult<String> deleteUser(@RequestParam("id") Long userId) {
        userService.delete(userId);
        return ObjectResult.ok("删除成功");
    }

    @GetMapping("/user-list")
    public ListResult<UserPO> userList() {
        UserQuery query = new UserQuery();
        return ListResult.ok(userService.list(query));
    }

    @PostMapping("/add-tag-manager")
    public ObjectResult<String> addTagManager(@RequestBody UserTagManageAddDTO record) {
        userTagManageService.add(record);
        return ObjectResult.ok("添加成功");
    }

    @GetMapping("/query-tag-manager")
    public PageResult<UserTagManageBO> queryTagManager(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(userTagManageService.query(pageNum, pageSize, null));
    }

    @GetMapping("/delete-tag-manager")
    public ObjectResult<String> deleteTagManager(@RequestParam("id") Long id) {
        userTagManageService.delete(id);
        return ObjectResult.ok("删除成功");
    }

    @PostMapping("/query-topic")
    public PageResult<TopicBO> queryTopic(@RequestBody TopicQuery topicQuery,@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        return PageResult.ok(topicService.queryBO(pageNum,pageSize,topicQuery));
    }

    @GetMapping("/delete-comment")
    public ObjectResult<String> deleteComment(@RequestParam("id")Long id){
        commentService.delete(id);
        return ObjectResult.ok("删除成功");
    }

    @GetMapping("/delete-topic")
    public ObjectResult<String> deleteTopic(@RequestParam("id")Long id){
        topicService.delete(id);
        return ObjectResult.ok("删除成功");
    }

    @PostMapping("/update-topic")
    public ObjectResult<String> updateTopic(@RequestBody TopicUpdateDTO record){
        topicService.update(record);
        return ObjectResult.ok("更新成功");
    }
}
