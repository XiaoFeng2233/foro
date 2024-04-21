package com.sjxy.bbs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sjxy.bbs.entity.bo.TagBO;
import com.sjxy.bbs.entity.dto.TagAddDTO;
import com.sjxy.bbs.entity.dto.TagUpdateDTO;
import com.sjxy.bbs.entity.po.TagPO;

import java.util.List;

public interface TagService {
    Long getAllTagCount();
    IPage<TagBO> query(Integer pageNum, Integer pageSize);

    List<TagBO> list();

    TagPO get(Long tagId);

    TagBO info(Long tagId);

    void delete(Long tagId);

    void add(TagAddDTO record);

    void update(TagUpdateDTO record);
}
