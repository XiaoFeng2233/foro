package com.sjxy.bbs.service;

import com.sjxy.bbs.entity.bo.FileUploadBO;
import com.sjxy.bbs.entity.po.UploadFilePO;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UploadFileService {
    FileUploadBO uploadFile(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    List<UploadFilePO> listByFileIds(List<String> fileIds);
}
