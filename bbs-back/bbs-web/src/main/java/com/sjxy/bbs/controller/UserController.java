package com.sjxy.bbs.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.sjxy.bbs.entity.bo.*;
import com.sjxy.bbs.entity.dto.UserChangePasswordDTO;
import com.sjxy.bbs.entity.query.TopicQuery;
import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.entity.result.PageResult;
import com.sjxy.bbs.service.LogService;
import com.sjxy.bbs.service.TopicService;
import com.sjxy.bbs.service.UserCollectRelateService;
import com.sjxy.bbs.service.UserService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private UserCollectRelateService userCollectRelateService;

    @GetMapping("/logout")
    @SaCheckLogin
    public ObjectResult<String> logout() {
        StpUtil.logout();
        return ObjectResult.ok(StpUtil.getTokenInfo().getTokenValue());
    }

    @GetMapping("/info")
    @SaCheckLogin
    public ObjectResult<UserBO> info() {
        return ObjectResult.ok(userService.getUserInfo());
    }

    @GetMapping("/general-info")
    @SaCheckLogin
    public ObjectResult<UserBO> generalInfo(){
        return ObjectResult.ok(userService.getUserGeneralInfo());
    }

    @GetMapping("/user-list")
    @SaCheckLogin
    public PageResult<UserBO> userList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(userService.userList(pageNum, pageSize));
    }

    @GetMapping("/sign-in")
    @SaCheckLogin
    public ObjectResult<Integer> signIn(){
        return ObjectResult.ok(userService.signIn());
    }


    @PostMapping("/update-nickname")
    @SaCheckLogin
    public ObjectResult<String> updateNickname(@RequestParam("nickname") String nickname) {
        userService.updateNickname(nickname);
        return ObjectResult.ok("昵称修改成功");
    }

    @PostMapping("/update-avatar")
    @SaCheckLogin
    public ObjectResult<FileUploadBO> updateAvatar(@RequestParam("file") MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ObjectResult.ok(userService.uploadAvatar(file));
    }

    @PostMapping("/update-background-image")
    @SaCheckLogin
    public ObjectResult<FileUploadBO> updateBackgroundImage(@RequestParam("file") MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ObjectResult.ok(userService.uploadBackgroundImage(file));
    }

    @PostMapping("/update-password")
    @SaCheckLogin
    public ObjectResult<String> updatePassword(@RequestBody UserChangePasswordDTO record) {
        userService.updatePassword(record);
        return ObjectResult.ok("密码修改成功");
    }

    @PostMapping("/update-description")
    @SaCheckLogin
    public ObjectResult<String> updateDescription(@RequestParam("description") String description) {
        userService.updateDescription(description);
        return ObjectResult.ok("签名修改成功");
    }

    @GetMapping("/login-log")
    @SaCheckLogin
    public PageResult<LogBO> loginLog(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(logService.getUserLoginLog(pageNum, pageSize));
    }

    @GetMapping("/query-user-topic")
    @SaCheckLogin
    public PageResult<TopicBO> queryUserTopic(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(topicService.queryUserTopic(pageNum, pageSize));
    }

    @GetMapping("/query-user-topic-by-user-id")
    @SaCheckLogin
    public PageResult<TopicBO> queryUserTopic(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("userId") Long userId) {
        return PageResult.ok(topicService.queryUserTopicByUserId(pageNum, pageSize,userId));
    }

    @GetMapping("/query-user-collect")
    @SaCheckLogin
    public PageResult<UserCollectRelateBO> queryUserCollect(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(userCollectRelateService.queryUserCollectList(pageNum, pageSize));
    }

    @GetMapping("/query-user-collect-by-user-id")
    @SaCheckLogin
    public PageResult<UserCollectRelateBO> queryUserCollectListByUserId(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("userId") Long userId) {
        return PageResult.ok(userCollectRelateService.queryUserCollectListByUserId(pageNum, pageSize, userId));
    }

    @GetMapping("/get-info-by-user-id")
    @SaCheckLogin
    public ObjectResult<UserBO> getInfoByUserId(@RequestParam("userId") Long userId) {
        return ObjectResult.ok(userService.getUserInfoByUserId(userId));
    }

}
