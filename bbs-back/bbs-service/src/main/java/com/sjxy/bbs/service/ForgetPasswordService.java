package com.sjxy.bbs.service;

import com.sjxy.bbs.entity.bo.ForgetPasswordInfoBO;
import com.sjxy.bbs.entity.dto.ForgetPasswordDTO;
import com.sjxy.bbs.entity.dto.ResetPasswordDTO;

public interface ForgetPasswordService {

    /**
     * 发送找回密码邮件
     *
     * @param record 传参
     */
    void forgetPassword(ForgetPasswordDTO record);

    ForgetPasswordInfoBO getForgetPasswordInfo(String token, String code);

    void resetPassword(ResetPasswordDTO record);
}
