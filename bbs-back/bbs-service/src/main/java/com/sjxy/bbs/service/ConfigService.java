package com.sjxy.bbs.service;

import com.sjxy.bbs.entity.bo.EmailConfigBO;
import com.sjxy.bbs.entity.bo.MinioConfigBO;
import com.sjxy.bbs.entity.bo.SiteConfigBO;
import com.sjxy.bbs.entity.bo.UserConfigBO;
import com.sjxy.bbs.entity.dto.EmailConfigUpdateDTO;
import com.sjxy.bbs.entity.dto.SiteConfigUpdateDTO;
import com.sjxy.bbs.entity.dto.StorageConfigUpdateDTO;
import com.sjxy.bbs.entity.po.ConfigPO;

import java.util.List;

public interface ConfigService {
    List<ConfigPO> list();

    EmailConfigBO getEmailConfig();

    SiteConfigBO getSiteConfig();

    UserConfigBO getUserConfig();

    MinioConfigBO getMinioConfig();

    void updateEmailConfig(EmailConfigUpdateDTO record);

    void updateStorageConfig(StorageConfigUpdateDTO record);

    void updateSiteConfig(SiteConfigUpdateDTO record);
}
