package com.sjxy.bbs.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.po.CommentPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjxy.bbs.entity.query.CommentQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentMapper extends BaseMapper<CommentPO> {
    CommentPO get(@Param("id") Long id);
    Page<CommentPO> query(Page<CommentPO> page,@Param("record") CommentQuery record);
}




