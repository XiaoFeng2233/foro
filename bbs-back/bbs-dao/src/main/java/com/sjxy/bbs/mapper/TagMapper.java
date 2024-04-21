package com.sjxy.bbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjxy.bbs.entity.po.TagPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagMapper extends BaseMapper<TagPO> {
    TagPO selectById(@Param("id")Long id);
}




