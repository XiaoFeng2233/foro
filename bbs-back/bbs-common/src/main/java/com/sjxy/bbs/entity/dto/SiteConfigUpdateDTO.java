package com.sjxy.bbs.entity.dto;

import lombok.Data;

@Data
public class SiteConfigUpdateDTO {
    private String name;
    private String logo;
    private String favicon;
    private String url;
    private Short open_register;
    private Short open;
    private String footer;
    private Integer max_file_upload_size;
}
