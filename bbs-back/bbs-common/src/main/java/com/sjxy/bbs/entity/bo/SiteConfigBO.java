package com.sjxy.bbs.entity.bo;

import lombok.Data;

@Data
public class SiteConfigBO {
    private String name;
    private String logo;
    private String favicon;
    private String url;
    private Boolean openRegister;
    private Boolean open;
    private String footer;

    //文件最大上传大小,单位  KB
    private Integer maxFileUploadSize;
}
