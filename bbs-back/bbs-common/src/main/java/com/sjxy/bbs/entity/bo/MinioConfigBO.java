package com.sjxy.bbs.entity.bo;

import lombok.Data;

@Data
public class MinioConfigBO {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
