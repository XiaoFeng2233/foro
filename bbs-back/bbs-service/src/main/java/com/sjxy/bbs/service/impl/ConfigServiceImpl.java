package com.sjxy.bbs.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sjxy.bbs.entity.bo.EmailConfigBO;
import com.sjxy.bbs.entity.bo.MinioConfigBO;
import com.sjxy.bbs.entity.bo.SiteConfigBO;
import com.sjxy.bbs.entity.bo.UserConfigBO;
import com.sjxy.bbs.entity.dto.EmailConfigUpdateDTO;
import com.sjxy.bbs.entity.dto.SiteConfigUpdateDTO;
import com.sjxy.bbs.entity.dto.StorageConfigUpdateDTO;
import com.sjxy.bbs.entity.po.ConfigPO;
import com.sjxy.bbs.mapper.ConfigMapper;
import com.sjxy.bbs.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigMapper configMapper;

    @Override
    public List<ConfigPO> list() {
        return configMapper.selectList(null);
    }

    @Override
    @Cacheable(value = "config:email", key = "#root.methodName")
    public EmailConfigBO getEmailConfig() {
        List<ConfigPO> configList = list();
        Map<String, ConfigPO> map = configList.stream().collect(Collectors.toMap(ConfigPO::getName, e -> e));
        EmailConfigBO emailConfig = new EmailConfigBO();

        Optional.ofNullable(map.get("email.host")).ifPresent(bo -> {
            emailConfig.setHost(bo.getValue());
        });

        Optional.ofNullable(map.get("email.port")).ifPresent(bo -> {
            emailConfig.setPort(bo.getValue());
        });

        Optional.ofNullable(map.get("email.username")).ifPresent(bo -> {
            emailConfig.setUsername(bo.getValue());
        });

        Optional.ofNullable(map.get("email.password")).ifPresent(bo -> {
            emailConfig.setPassword(bo.getValue());
        });

        Optional.ofNullable(map.get("email.address")).ifPresent(bo -> {
            emailConfig.setAddress(bo.getValue());
        });

        Optional.ofNullable(map.get("email.useSSL")).ifPresent(bo -> {
            int i = Integer.parseInt(bo.getValue());
            emailConfig.setUseSSL(i != 0);
        });

        Optional.ofNullable(map.get("email.registerTemplate")).ifPresent(bo -> {
            emailConfig.setRegisterTemplate(bo.getValue());
        });

        Optional.ofNullable(map.get("email.forgetTemplate")).ifPresent(bo -> {
            emailConfig.setForgetTemplate(bo.getValue());
        });

        return emailConfig;
    }

    @Cacheable(value = "config:site", key = "#root.methodName")
    public SiteConfigBO getSiteConfig() {
        List<ConfigPO> configList = list();
        Map<String, ConfigPO> map = configList.stream().collect(Collectors.toMap(ConfigPO::getName, e -> e));

        SiteConfigBO siteConfigBO = new SiteConfigBO();

        Optional.ofNullable(map.get("site.name")).ifPresent(bo -> {
            siteConfigBO.setName(bo.getValue());
        });

        Optional.ofNullable(map.get("site.favicon")).ifPresent(bo -> {
            siteConfigBO.setFavicon(bo.getValue());
        });

        Optional.ofNullable(map.get("site.logo")).ifPresent(bo -> {
            siteConfigBO.setLogo(bo.getValue());
        });

        Optional.ofNullable(map.get("site.url")).ifPresent(bo -> {
            siteConfigBO.setUrl(bo.getValue());
        });

        Optional.ofNullable(map.get("site.open_register")).ifPresent(bo -> {
            Integer value = Integer.parseInt(bo.getValue());
            if (value.equals(1)) {
                siteConfigBO.setOpenRegister(true);
            } else {
                siteConfigBO.setOpenRegister(false);
            }
        });

        Optional.ofNullable(map.get("site.open")).ifPresent(bo -> {
            Integer value = Integer.parseInt(bo.getValue());
            if (value.equals(1)) {
                siteConfigBO.setOpen(true);
            } else {
                siteConfigBO.setOpen(false);
            }
        });

        Optional.ofNullable(map.get("site.footer")).ifPresent(bo -> {
            siteConfigBO.setFooter(bo.getValue());
        });

        Optional.ofNullable(map.get("site.max_file_upload_size")).ifPresent(bo -> {
            siteConfigBO.setMaxFileUploadSize(Integer.parseInt(bo.getValue()));
        });


        return siteConfigBO;
    }

    @Override
    @Cacheable(value = "config:user", key = "#root.methodName")
    public UserConfigBO getUserConfig() {
        List<ConfigPO> configList = list();
        Map<String, ConfigPO> map = configList.stream().collect(Collectors.toMap(ConfigPO::getName, e -> e));

        UserConfigBO userConfigBO = new UserConfigBO();

        Optional.ofNullable(map.get("user.defaultAvatar")).ifPresent(bo -> {
            userConfigBO.setDefaultAvatar(bo.getValue());
        });

        Optional.ofNullable(map.get("user.defaultBackgroundImage")).ifPresent(bo -> {
            userConfigBO.setDefaultAvatar(bo.getValue());
        });


        return userConfigBO;
    }

    @Override
    @Cacheable(value = "config:minio", key = "#root.methodName")
    public MinioConfigBO getMinioConfig() {
        List<ConfigPO> configList = list();
        Map<String, ConfigPO> map = configList.stream().collect(Collectors.toMap(ConfigPO::getName, e -> e));
        MinioConfigBO minioConfigBO = new MinioConfigBO();

        Optional.ofNullable(map.get("minio.endpoint")).ifPresent(bo -> {
            minioConfigBO.setEndpoint(bo.getValue());
        });

        Optional.ofNullable(map.get("minio.accessKey")).ifPresent(bo -> {
            minioConfigBO.setAccessKey(bo.getValue());
        });

        Optional.ofNullable(map.get("minio.secretKey")).ifPresent(bo -> {
            minioConfigBO.setSecretKey(bo.getValue());
        });

        return minioConfigBO;
    }

    @Override
    @CacheEvict(value = "config:email", allEntries = true)
    public void updateEmailConfig(EmailConfigUpdateDTO record) {
        verifyUpdateEmailConfigParam(record);
        Field[] declaredFields = record.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                String name = field.getName();
                Object value = field.get(record);
                UpdateWrapper<ConfigPO> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("value", value);
                updateWrapper.eq("name", "email." + name);
                configMapper.update(updateWrapper);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @CacheEvict(value = "config:minio", allEntries = true)
    public void updateStorageConfig(StorageConfigUpdateDTO record) {
        verifyUpdateStorageConfigParam(record);
        Field[] declaredFields = record.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                String name = field.getName();
                Object value = field.get(record);
                UpdateWrapper<ConfigPO> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("value", value);
                updateWrapper.eq("name", "minio." + name);
                configMapper.update(updateWrapper);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @CacheEvict(value = "config:site", allEntries = true)
    public void updateSiteConfig(SiteConfigUpdateDTO record) {
        verifyUpdateSiteConfigParam(record);
        Field[] declaredFields = record.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                String name = field.getName();
                Object value = field.get(record);
                UpdateWrapper<ConfigPO> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("value", value);
                updateWrapper.eq("name", "site." + name);
                configMapper.update(updateWrapper);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void verifyUpdateEmailConfigParam(EmailConfigUpdateDTO record) {
        Assert.isTrue(StrUtil.isNotBlank(record.getHost()), "发信服务器地址不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getPort()), "发信服务器端口不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getUsername()), "邮箱账号不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getPassword()), "邮箱密码不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getAddress()), "邮箱完整地址不能为空");
        Assert.isTrue(record.getUseSSL() != null, "是否开启SSL字段不能为空");
    }

    private void verifyUpdateStorageConfigParam(StorageConfigUpdateDTO record) {
        Assert.isTrue(StrUtil.isNotBlank(record.getEndpoint()), "endpoint不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getAccessKey()), "accessKey不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getSecretKey()), "secretKey不能为空");
    }

    private void verifyUpdateSiteConfigParam(SiteConfigUpdateDTO record) {
        Assert.isTrue(StrUtil.isNotBlank(record.getName()), "网站名称不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getLogo()), "网站logo不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getFavicon()), "网站favicon不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getUrl()), "网站地址不能为空");
        Assert.isTrue(record.getOpen_register() != null, "是否开启注册字段不能为空");
        Assert.isTrue(record.getOpen() != null, "是否开启网站字段不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getFooter()), "网站底部不能为空");
        Assert.isTrue(record.getMax_file_upload_size() != null, "文件上传最大值不能为空");
    }
}
