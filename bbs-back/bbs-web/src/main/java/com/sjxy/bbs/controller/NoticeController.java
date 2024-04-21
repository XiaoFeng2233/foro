package com.sjxy.bbs.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.sjxy.bbs.entity.bo.NoticeBO;
import com.sjxy.bbs.entity.result.PageResult;
import com.sjxy.bbs.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/query-user-notice-list")
    @SaCheckLogin
    public PageResult<NoticeBO> queryUserNoticeList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        return PageResult.ok(noticeService.queryUserNotice(pageNum,pageSize));
    }
}
