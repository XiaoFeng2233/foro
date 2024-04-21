package com.sjxy.bbs.service;

import com.sjxy.bbs.entity.bo.LoginBO;
import com.sjxy.bbs.entity.dto.LoginDTO;

public interface LoginService {
    LoginBO login(LoginDTO record);

}
