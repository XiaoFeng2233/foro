package com.sjxy.bbs.service;

import com.sjxy.bbs.entity.dto.UserVerifyCodeAddDTO;
import com.sjxy.bbs.entity.po.UserVerifyCodePO;
import com.sjxy.bbs.entity.query.UserVerifyCodeQuery;

import java.util.List;

public interface UserVerifyCodeService {
    UserVerifyCodePO get(UserVerifyCodeQuery record);

    void update(UserVerifyCodePO record);

    void add(UserVerifyCodeAddDTO record);

    void delete(Long id);

    List<UserVerifyCodePO> queryAllExpireCode();

    void deleteBatch(List<Long> ids);
}
