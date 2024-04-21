package com.sjxy.bbs.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.TagBO;
import com.sjxy.bbs.entity.bo.UserBO;
import com.sjxy.bbs.entity.bo.UserTagManageBO;
import com.sjxy.bbs.entity.dto.TagAddDTO;
import com.sjxy.bbs.entity.dto.TagUpdateDTO;
import com.sjxy.bbs.entity.enums.YesOrNoEnum;
import com.sjxy.bbs.entity.po.TagPO;
import com.sjxy.bbs.entity.query.UserTagManageQuery;
import com.sjxy.bbs.mapper.TagMapper;
import com.sjxy.bbs.service.BaseService;
import com.sjxy.bbs.service.TagService;
import com.sjxy.bbs.service.UserTagManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTML;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl extends BaseService implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private UserTagManageService userTagManageService;

    @Override
    public Long getAllTagCount() {
        return tagMapper.selectCount(null);
    }

    @Override
    @Cacheable(value = "tag:query", key = "#pageNum+'-'+#pageSize")
    public IPage<TagBO> query(Integer pageNum, Integer pageSize) {
        Assert.notNull(pageNum, "pageNum 不能为空");
        Assert.notNull(pageSize, "pageSize 不能为空");
        Page<TagPO> page = new Page<>(pageNum, pageSize);
        Page<TagPO> allTagPO = tagMapper.selectPage(page, null);
        Page<TagBO> result = new Page<>();
        List<TagBO> collect = allTagPO.getRecords().stream().map(tagPO -> {
            TagBO tagBO = new TagBO();
            BeanUtils.copyProperties(tagPO, tagBO);
            return tagBO;
        }).collect(Collectors.toList());
        result.setRecords(collect);
        result.setCurrent(allTagPO.getCurrent());
        result.setSize(allTagPO.getSize());
        result.setTotal(allTagPO.getTotal());
        return result;
    }

    @Override
    @Cacheable(value = "tag:list")
    public List<TagBO> list() {
        List<TagPO> tagList = tagMapper.selectList(null);
        List<TagBO> collect = tagList.stream().map(tagPO -> {
            TagBO tagBO = new TagBO();
            BeanUtils.copyProperties(tagPO, tagBO);
            return tagBO;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public TagPO get(Long tagId) {
        Assert.isTrue(tagId != null, "板块id不能为空");
        TagPO tagPO = tagMapper.selectById(tagId);
        return tagPO;
    }

    @Override
    @Cacheable(value = "tag:info", key = "#tagId")
    public TagBO info(Long tagId) {
        Assert.isTrue(tagId != null, "板块id不能为空");
        TagPO tagPO = get(tagId);
        TagBO tagBO = new TagBO();
        Assert.isTrue(tagPO != null, "板块不存在");
        BeanUtils.copyProperties(tagPO, tagBO);

        if (tagPO.getCreateUser() != null) {
            UserBO userBO = new UserBO();
            BeanUtils.copyProperties(tagPO.getCreateUser(), userBO);
            tagBO.setCreateUser(userBO);
        }

        UserTagManageQuery query = new UserTagManageQuery();
        query.setTagId(tagId);
        List<UserTagManageBO> list = userTagManageService.list(query);
        List<UserBO> managerList = list.stream().map(UserTagManageBO::getUser).toList();
        tagBO.setManagerList(managerList);
        return tagBO;
    }

    @Override
    @CacheEvict(value = {"tag:list", "tag:info","tag:query"}, allEntries = true)
    public void delete(Long tagId) {
        Assert.isTrue(tagId != null, "板块id不能为空");
        tagMapper.deleteById(tagId);
    }

    @Override
    @CacheEvict(value = {"tag:list", "tag:info","tag:query"}, allEntries = true)
    public void add(TagAddDTO record) {
        Assert.isTrue(StrUtil.isNotBlank(record.getDescription()), "板块描述不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getName()), "板块名称不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getColor()), "板块颜色不能为空");
        TagPO tagPO = new TagPO();
        BeanUtils.copyProperties(record, tagPO);
        tagPO.setCreateBy(getUserId());
        tagPO.setCreateTime(new Date());
        tagPO.setCreateUsername(getUsername());
        tagPO.setStatus(YesOrNoEnum.YES.getCode());
        tagMapper.insert(tagPO);
    }

    @Override
    @CacheEvict(value = {"tag:list", "tag:info","tag:query"}, allEntries = true)
    public void update(TagUpdateDTO tagUpdateDTO) {
        Assert.isTrue(StrUtil.isNotBlank(tagUpdateDTO.getDescription()), "板块描述不能为空");
        Assert.isTrue(StrUtil.isNotBlank(tagUpdateDTO.getName()), "板块名称不能为空");
        Assert.isTrue(StrUtil.isNotBlank(tagUpdateDTO.getColor()), "板块颜色不能为空");
        Assert.isTrue(tagUpdateDTO.getId() != null, "板块id不能为空");

        TagPO tagPO = get(tagUpdateDTO.getId());
        Assert.notNull(tagPO, "板块不存在");
        BeanUtils.copyProperties(tagUpdateDTO, tagPO);
        tagPO.setUpdateBy(getUserId());
        tagPO.setUpdateTime(new Date());
        tagPO.setUpdateUsername(getUsername());
        tagMapper.updateById(tagPO);
    }

}
