package com.sjxy.bbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjxy.bbs.entity.po.UserVerifyCodePO;
import com.sjxy.bbs.entity.query.UserVerifyCodeQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerifyCodeMapper extends BaseMapper<UserVerifyCodePO> {
    UserVerifyCodePO get(@Param("record") UserVerifyCodeQuery record);
}




