package com.sjxy.bbs.controller;

import com.sjxy.bbs.entity.dto.RegisterDTO;
import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ObjectResult<Long> register(@RequestBody RegisterDTO record) {
        return ObjectResult.ok(registerService.register(record));
    }

    @GetMapping("/register/active")
    public ObjectResult<String> registerActive(@RequestParam("token") String token, @RequestParam("code") String code) {
        Boolean isActive = registerService.active(token, code);
        if(isActive){
            return ObjectResult.ok("账户激活成功，等待3秒或等待下方按钮跳转至登录界面登录","账户激活成功，等待3秒或等待下方按钮跳转至登录界面登录");
        }else{
            return ObjectResult.fail("链接不存在或已过期","链接不存在或已过期");
        }
    }
}
