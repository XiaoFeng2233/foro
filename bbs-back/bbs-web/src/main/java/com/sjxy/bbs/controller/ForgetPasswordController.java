package com.sjxy.bbs.controller;

import com.sjxy.bbs.entity.bo.ForgetPasswordInfoBO;
import com.sjxy.bbs.entity.dto.ForgetPasswordDTO;
import com.sjxy.bbs.entity.dto.ResetPasswordDTO;
import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.service.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ForgetPasswordController {
    @Autowired
    private ForgetPasswordService forgetPasswordService;

    @PostMapping("/forget-password")
    public ObjectResult<String> forgetPassword(@RequestBody ForgetPasswordDTO record) {
        forgetPasswordService.forgetPassword(record);
        return ObjectResult.ok("密码重置连接已发送到您的邮箱中");
    }

    @GetMapping("/get-forget-password-info")
    public ObjectResult<ForgetPasswordInfoBO> getForgetPasswordInfo(@RequestParam("token") String token, @RequestParam("code") String code) {
        return ObjectResult.ok(forgetPasswordService.getForgetPasswordInfo(token, code));
    }

    @PostMapping("/reset-password")
    public ObjectResult<String> resetPassword(@RequestBody ResetPasswordDTO record) {
        forgetPasswordService.resetPassword(record);
        return ObjectResult.ok("密码重置成功");
    }

}
