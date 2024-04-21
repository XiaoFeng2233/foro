package com.sjxy.bbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.po.UserCollectRelatePO;
import com.sjxy.bbs.entity.query.UserCollectRelateQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserCollectRelateMapper extends BaseMapper<UserCollectRelatePO> {
    Page<UserCollectRelatePO> query(Page<UserCollectRelatePO> page, @Param("record") UserCollectRelateQuery record);

    List<UserCollectRelatePO> list(@Param("record") UserCollectRelateQuery record);

    UserCollectRelatePO get(@Param("record") UserCollectRelateQuery record);
}




