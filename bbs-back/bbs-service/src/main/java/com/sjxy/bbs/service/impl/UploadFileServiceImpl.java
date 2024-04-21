package com.sjxy.bbs.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sjxy.bbs.entity.bo.FileUploadBO;
import com.sjxy.bbs.entity.bo.MinioConfigBO;
import com.sjxy.bbs.entity.bo.SiteConfigBO;
import com.sjxy.bbs.entity.constants.FileConstants;
import com.sjxy.bbs.entity.po.UploadFilePO;
import com.sjxy.bbs.mapper.UploadFileMapper;
import com.sjxy.bbs.service.BaseService;
import com.sjxy.bbs.service.ConfigService;
import com.sjxy.bbs.service.UploadFileService;
import com.sjxy.bbs.util.RedisUtil;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UploadFileServiceImpl extends BaseService implements UploadFileService {
    @Autowired
    private ConfigService configService;
    @Autowired
    private UploadFileMapper mapper;
    @Autowired
    private RedisUtil redisUtil;


    @Override
    public FileUploadBO uploadFile(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        SiteConfigBO siteConfig = configService.getSiteConfig();
        Integer maxFileUploadSize = siteConfig.getMaxFileUploadSize();

        long fileSize = file.getSize() / 1024;
        Assert.isTrue(fileSize <= maxFileUploadSize, "图片大小过大，不得大于：" + maxFileUploadSize + "KB");

        MinioConfigBO minioConfig = configService.getMinioConfig();


        MinioClient minioClient = MinioClient
                .builder()
                .endpoint(minioConfig.getEndpoint())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();

        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(FileConstants.BUCKET_NAME).build());
        if (!bucketExists) {

            //创建bucket
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(FileConstants.BUCKET_NAME).build());

            //将bucket设置为只读
            StringBuilder policy = new StringBuilder();
            policy.append("{\n" +
                    "    \"Version\": \"2012-10-17\",\n" +
                    "    \"Statement\": [\n" +
                    "        {\n" +
                    "            \"Sid\":\"PublicRead\",\n" +
                    "            \"Effect\": \"Allow\",\n" +
                    "            \"Principal\": \"*\",\n" +
                    "            \"Action\": [\n" +
                    "                \"s3:GetBucketLocation\",\n" +
                    "                \"s3:GetObject\"\n" +
                    "            ],\n" +
                    "            \"Resource\": [\n" +
                    "                \"arn:aws:s3:::*\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}");

            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(FileConstants.BUCKET_NAME)
                    .config(policy.toString())
                    .build()
            );
        }


        String uuid = IdUtil.simpleUUID();
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
        String saveFileName = uuid + "." + suffix;

        InputStream inputStream = file.getInputStream();
        String objectName = String.format(FileConstants.FILE_PATH, LocalDate.now().format(DateTimeFormatter.ISO_DATE), saveFileName);
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(FileConstants.BUCKET_NAME)
                .object(objectName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build());

        inputStream.close();


        FileUploadBO fileUploadBO = new FileUploadBO();
        fileUploadBO.setFileId(uuid);
        fileUploadBO.setFileUrl(minioConfig.getEndpoint() + "/" + FileConstants.BUCKET_NAME + objectName);

        add(fileUploadBO.getFileId(), fileUploadBO.getFileUrl());
        return fileUploadBO;
    }

    @Override
    public List<UploadFilePO> listByFileIds(List<String> fileIds) {
        QueryWrapper<UploadFilePO> wrapper = new QueryWrapper<>();
        wrapper.in("file_id", fileIds);
        return mapper.selectList(wrapper);
    }

    private void add(String fileId, String fileUrl) {

        String username = getUsername();
        Long userId = getUserId();


        UploadFilePO uploadFilePO = new UploadFilePO();
        uploadFilePO.setFileId(fileId);
        uploadFilePO.setUrl(fileUrl);
        uploadFilePO.setCreateBy(userId);
        uploadFilePO.setCreateTime(new Date());
        uploadFilePO.setCreateUsername(username);
        mapper.insert(uploadFilePO);
    }
}
