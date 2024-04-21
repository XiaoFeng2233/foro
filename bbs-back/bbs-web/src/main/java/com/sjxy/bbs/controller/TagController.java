package com.sjxy.bbs.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.sjxy.bbs.entity.bo.TagBO;
import com.sjxy.bbs.entity.result.ListResult;
import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.entity.result.PageResult;
import com.sjxy.bbs.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/query")
    PageResult<TagBO> query(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize) {
        return PageResult.ok(tagService.query(pageNum, pageSize));
    }

    @GetMapping("/info")
    public ObjectResult<TagBO> info(@RequestParam("id")Long tagId){
        return ObjectResult.ok(tagService.info(tagId));
    }

    @SaCheckLogin
    @GetMapping("/list")
    public ListResult<TagBO> list(){
        return ListResult.ok(tagService.list());
    }
}
