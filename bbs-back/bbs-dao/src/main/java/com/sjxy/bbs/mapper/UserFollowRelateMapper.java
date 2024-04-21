package com.sjxy.bbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.po.UserFollowRelatePO;
import com.sjxy.bbs.entity.query.UserFollowRelateQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowRelateMapper extends BaseMapper<UserFollowRelatePO> {
    Page<UserFollowRelatePO> query(Page<UserFollowRelatePO> page,@Param("record") UserFollowRelateQuery record);
    List<UserFollowRelatePO> list(@Param("record") UserFollowRelateQuery record);

    UserFollowRelatePO get(@Param("record") UserFollowRelateQuery record);
}




