package com.sjxy.bbs.controller;

import com.sjxy.bbs.entity.bo.SiteConfigBO;
import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class SiteConfigController {
    @Autowired
    private ConfigService configService;

    @GetMapping("/site-config")
    public ObjectResult<SiteConfigBO> siteConfig(){
        return ObjectResult.ok(configService.getSiteConfig());
    }
}
