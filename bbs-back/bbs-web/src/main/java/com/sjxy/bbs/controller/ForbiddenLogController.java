package com.sjxy.bbs.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.sjxy.bbs.entity.bo.ForbiddenLogBO;
import com.sjxy.bbs.entity.result.PageResult;
import com.sjxy.bbs.service.ForbiddenLogService;
import com.sjxy.bbs.service.UserCollectRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forbidden-log")
public class ForbiddenLogController {
    @Autowired
    private ForbiddenLogService forbiddenLogService;

    @GetMapping("/query-user-forbidden-log")
    @SaCheckLogin
    public PageResult<ForbiddenLogBO> queryUserForbiddenLog(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(forbiddenLogService.queryUserForbiddenLog(pageNum, pageSize));
    }

    @GetMapping("/query-user-forbidden-log-by-user-id")
    @SaCheckLogin
    public PageResult<ForbiddenLogBO> queryUserForbiddenLogByUserId(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("userId") Long userId) {
        return PageResult.ok(forbiddenLogService.queryUserForbiddenLogByUserId(pageNum, pageSize, userId));
    }

}
