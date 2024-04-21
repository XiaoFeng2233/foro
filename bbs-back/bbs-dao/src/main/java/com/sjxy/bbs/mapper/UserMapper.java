package com.sjxy.bbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.po.UserRegisterStatisticPO;
import com.sjxy.bbs.entity.query.UserQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<UserPO> {
    UserPO get(@Param("id")Long id);

    List<UserRegisterStatisticPO> getAllUserRegisterStatistic();

    Page<UserPO> query(Page<UserPO> page, @Param("record")UserQuery record);
}




