package com.sjxy.bbs.controller;

import com.sjxy.bbs.entity.bo.LoginBO;
import com.sjxy.bbs.entity.dto.LoginDTO;
import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ObjectResult<LoginBO> login(@RequestBody LoginDTO loginDTO) {
        LoginBO loginBO = loginService.login(loginDTO);
        if (loginBO != null) {
            return ObjectResult.ok("登录成功", loginBO);
        } else {
            return ObjectResult.fail("登录失败，账号或密码错误", null);
        }
    }
}
