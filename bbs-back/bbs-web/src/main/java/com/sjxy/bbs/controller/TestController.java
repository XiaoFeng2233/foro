package com.sjxy.bbs.controller;

import com.sjxy.bbs.entity.dto.UserRoleRelateAddDTO;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.service.UserRoleRelateService;
import com.sjxy.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleRelateService userRoleRelateService;

    @PostMapping("/addUser")
    public Integer addUser(@RequestBody UserPO userPO){
        userService.add(userPO);

        UserRoleRelateAddDTO relateAddDTO = new UserRoleRelateAddDTO();
        relateAddDTO.setRoleId(3L);
        relateAddDTO.setUserId(userPO.getId());
        userRoleRelateService.add(relateAddDTO);
        return 200;
    }
}
