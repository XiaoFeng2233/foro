package com.sjxy.bbs.mapper;
import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.po.NoticePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjxy.bbs.entity.query.NoticeQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeMapper extends BaseMapper<NoticePO> {
    Page<NoticePO> query(Page<NoticePO> page, @Param("record") NoticeQuery record);

    int insertBatch(List<NoticePO> list);
}




