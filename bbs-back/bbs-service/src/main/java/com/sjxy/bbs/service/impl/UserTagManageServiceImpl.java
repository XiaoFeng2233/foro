package com.sjxy.bbs.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.TagBO;
import com.sjxy.bbs.entity.bo.UserBO;
import com.sjxy.bbs.entity.bo.UserTagManageBO;
import com.sjxy.bbs.entity.dto.UserRoleRelateAddDTO;
import com.sjxy.bbs.entity.dto.UserTagManageAddDTO;
import com.sjxy.bbs.entity.po.UserTagManagePO;
import com.sjxy.bbs.entity.query.UserTagManageQuery;
import com.sjxy.bbs.mapper.UserTagManageMapper;
import com.sjxy.bbs.service.BaseService;
import com.sjxy.bbs.service.UserRoleRelateService;
import com.sjxy.bbs.service.UserTagManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserTagManageServiceImpl extends BaseService implements UserTagManageService {
    @Autowired
    private UserTagManageMapper mapper;
    @Autowired
    private UserRoleRelateService userRoleRelateService;

    @Override
    public List<UserTagManageBO> list(UserTagManageQuery record) {
        List<UserTagManagePO> list = mapper.list(record);
        if (CollUtil.isNotEmpty(list)) {
            List<UserTagManageBO> result = list.stream().map(item -> {
                UserTagManageBO userTagManageBO = new UserTagManageBO();
                BeanUtils.copyProperties(item, userTagManageBO);
                if (item.getUser() != null) {
                    UserBO userBO = new UserBO();
                    BeanUtils.copyProperties(item.getUser(), userBO);
                    userTagManageBO.setUser(userBO);
                }

                if (item.getTag() != null) {
                    TagBO tagBO = new TagBO();
                    BeanUtils.copyProperties(item.getTag(), tagBO);
                    userTagManageBO.setTag(tagBO);
                }
                return userTagManageBO;
            }).toList();
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    @CacheEvict(value = {"tag:list", "tag:info","tag:query"}, allEntries = true)
    public void add(UserTagManageAddDTO record) {
        Assert.isTrue(record != null, "传参不能为空");
        Assert.isTrue(record.getTagId() != null, "板块id不能为空");
        Assert.isTrue(record.getUserId() != null, "用户id不能为空");

        UserTagManageQuery query = new UserTagManageQuery();
        query.setTagId(record.getTagId());
        query.setUserId(record.getUserId());
        List<UserTagManageBO> list = list(query);
        Assert.isTrue(CollUtil.isEmpty(list), "该用户已经是本板块的版主了");

        UserTagManagePO po = new UserTagManagePO();
        po.setTagId(record.getTagId());
        po.setUserId(record.getUserId());
        po.setCreateBy(getUserId());
        po.setCreateTime(new Date());
        po.setCreateUsername(getUsername());

        UserRoleRelateAddDTO dto = new UserRoleRelateAddDTO();
        dto.setRoleId(2L);
        dto.setUserId(record.getUserId());
        userRoleRelateService.add(dto);
        mapper.insert(po);
    }

    @Override
    public Page<UserTagManageBO> query(Integer pageNum, Integer pageSize, UserTagManageQuery record) {
        Page<UserTagManagePO> page = new Page<>(pageNum, pageSize);
        Page<UserTagManagePO> result = mapper.query(page, record);

        Page<UserTagManageBO> resultPage = new Page<>();
        BeanUtils.copyProperties(result, resultPage);
        if (CollUtil.isNotEmpty(result.getRecords())) {
            List<UserTagManageBO> list = result.getRecords().stream().map(item -> {
                UserTagManageBO userTagManageBO = new UserTagManageBO();
                BeanUtils.copyProperties(item, userTagManageBO);
                if (item.getUser() != null) {
                    UserBO userBO = new UserBO();
                    BeanUtils.copyProperties(item.getUser(), userBO);
                    userTagManageBO.setUser(userBO);
                }

                if (item.getTag() != null) {
                    TagBO tagBO = new TagBO();
                    BeanUtils.copyProperties(item.getTag(), tagBO);
                    userTagManageBO.setTag(tagBO);
                }
                return userTagManageBO;
            }).toList();

            resultPage.setRecords(list);
        }

        return resultPage;
    }

    @Override
    public void delete(Long id) {
        UserTagManagePO userTagManagePO = mapper.selectById(id);
        if(userTagManagePO != null){
            mapper.deleteById(id);
            userRoleRelateService.deleteUserRoleRelate(userTagManagePO.getUserId(),2L);
        }

    }

    @Override
    @CacheEvict(value = {"tag:list", "tag:info","tag:query"}, allEntries = true)
    public void deleteByUserId(Long userId) {
        Assert.isTrue(userId != null, "用户id不能为空");
        QueryWrapper<UserTagManagePO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
    }
}
