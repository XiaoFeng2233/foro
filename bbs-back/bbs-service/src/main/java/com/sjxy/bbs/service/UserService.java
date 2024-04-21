package com.sjxy.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.FileUploadBO;
import com.sjxy.bbs.entity.bo.UserBO;
import com.sjxy.bbs.entity.dto.UserChangePasswordDTO;
import com.sjxy.bbs.entity.dto.UserForbiddenDTO;
import com.sjxy.bbs.entity.dto.UserMuteDTO;
import com.sjxy.bbs.entity.dto.UserUpdateDTO;
import com.sjxy.bbs.entity.po.UserPO;
import com.sjxy.bbs.entity.po.UserRegisterStatisticPO;
import com.sjxy.bbs.entity.query.UserQuery;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {

    Long getAllUserCount();

    Integer add(UserPO record);

    UserPO get(UserQuery record);

    void update(UserUpdateDTO record);

    void update(UserPO record);

    void delete(UserPO record);

    void delete(Long id);

    /**
     * 获取用户的基本信息
     *
     * @return 用户信息
     */
    UserBO getUserInfo();


    UserBO getUserGeneralInfo();

    /**
     *
     * @param userId 用户ID
     * @return 查询其他用户信息
     */
    UserBO getUserInfoByUserId(Long userId);

    /**
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return 用户BO
     */
    Page<UserBO> userList(Integer pageNum, Integer pageSize);

    /**
     * 更新用户的昵称
     *
     * @param nickname 新的昵称
     */
    void updateNickname(String nickname);

    /**
     * 更新用户的签名
     *
     * @param description 新的签名
     */
    void updateDescription(String description);

    /**
     * 更新用户的密码
     *
     * @param record 用户传参
     */
    void updatePassword(UserChangePasswordDTO record);

    /**
     * @param file 新的头像文件
     * @return 文件上传信息
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    FileUploadBO uploadAvatar(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    /**
     * 更新用户的背景图片
     *
     * @param file 新的背景图片
     * @return 文件上传信息
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    FileUploadBO uploadBackgroundImage(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    Integer signIn();

    List<UserRegisterStatisticPO> getAllUserRegisterStatistic();

    Page<UserPO> query(UserQuery record,Integer pageNum, Integer pageSize);

    void muteUser(UserMuteDTO record);

    void forbiddenUser(UserForbiddenDTO record);

    void unMuteUser(Long userId);

    void unForbiddenUser(Long userId);

    List<UserPO> list(UserQuery record);

}
