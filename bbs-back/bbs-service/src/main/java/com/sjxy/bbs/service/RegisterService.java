package com.sjxy.bbs.service;

import com.sjxy.bbs.entity.dto.RegisterDTO;

public interface RegisterService {
    Long register(RegisterDTO record);

    Boolean active(String token, String code);
}
