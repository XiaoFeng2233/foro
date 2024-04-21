package com.sjxy.bbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.po.UserTagManagePO;
import com.sjxy.bbs.entity.query.UserTagManageQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTagManageMapper extends BaseMapper<UserTagManagePO> {
    List<UserTagManagePO> list(@Param("record") UserTagManageQuery record);
    Page<UserTagManagePO> query(Page<UserTagManagePO> page,@Param("record") UserTagManageQuery record);
}




