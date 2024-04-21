package com.sjxy.bbs.entity.dto;

import lombok.Data;

@Data
public class StorageConfigUpdateDTO {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
