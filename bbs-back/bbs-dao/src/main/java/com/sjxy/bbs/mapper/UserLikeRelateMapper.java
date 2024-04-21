package com.sjxy.bbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.po.UserLikeRelatePO;
import com.sjxy.bbs.entity.query.UserLikeRelateQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLikeRelateMapper extends BaseMapper<UserLikeRelatePO> {
    Page<UserLikeRelatePO> query(Page<UserLikeRelatePO> page, @Param("record") UserLikeRelateQuery record);

    List<UserLikeRelatePO> list(@Param("record") UserLikeRelateQuery record);

    UserLikeRelatePO get(@Param("record") UserLikeRelateQuery record);
}




