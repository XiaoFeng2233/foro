package com.sjxy.bbs.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.sjxy.bbs.entity.bo.FileUploadBO;
import com.sjxy.bbs.entity.bo.SiteConfigBO;
import com.sjxy.bbs.entity.result.ObjectResult;
import com.sjxy.bbs.service.ConfigService;
import com.sjxy.bbs.service.UploadFileService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadFileService uploadFileService;

    @SaCheckLogin
    @PostMapping("/image")
    public ObjectResult<FileUploadBO> uploadImage(@RequestParam("file")MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ObjectResult.ok(uploadFileService.uploadFile(file));
    }
}
