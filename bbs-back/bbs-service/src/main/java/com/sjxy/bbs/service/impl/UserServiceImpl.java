package com.sjxy.bbs.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.FileUploadBO;
import com.sjxy.bbs.entity.bo.LogBO;
import com.sjxy.bbs.entity.bo.UserBO;
import com.sjxy.bbs.entity.constants.UserConstants;
import com.sjxy.bbs.entity.dto.*;
import com.sjxy.bbs.entity.enums.LogTypeEnum;
import com.sjxy.bbs.entity.enums.UserStatusEnum;
import com.sjxy.bbs.entity.mq.AddLogMessage;
import com.sjxy.bbs.entity.po.*;
import com.sjxy.bbs.entity.query.UserFollowRelateQuery;
import com.sjxy.bbs.entity.query.UserQuery;
import com.sjxy.bbs.entity.query.UserRoleRelateQuery;
import com.sjxy.bbs.mapper.UserMapper;
import com.sjxy.bbs.service.*;
import com.sjxy.bbs.util.MQUtil;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class UserServiceImpl extends BaseService implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private LogService logService;
    @Autowired
    private MQUtil mqUtil;
    @Autowired
    private UserFollowRelateService userFollowRelateService;
    @Autowired
    private ForbiddenLogService forbiddenLogService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserLikeRelateService userLikeRelateService;
    @Autowired
    private UserCollectRelateService userCollectRelateService;
    @Autowired
    private UserTagManageService userTagManageService;

    @Override
    public Long getAllUserCount() {
        return userMapper.selectCount(null);
    }

    @Override
    public Integer add(UserPO record) {
        return userMapper.insert(record);
    }

    @Override
    public UserPO get(UserQuery record) {
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.eq(record.getId() != null, "id", record.getId());
        wrapper.eq(StrUtil.isNotBlank(record.getUsername()), "username", record.getUsername());
        wrapper.eq(StrUtil.isNotBlank(record.getEmail()), "email", record.getEmail());
        UserPO userPO = userMapper.selectOne(wrapper);
        return userPO;
    }

    @Override
    public void update(UserUpdateDTO record) {

        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(record, userPO);

        //当要修改邮箱前,需要判断该邮箱是否存在
        if (StrUtil.isNotBlank(record.getEmail())) {
            UserQuery userQuery = new UserQuery();
            userQuery.setEmail(record.getEmail());
            UserPO existUser = get(userQuery);
            Assert.isTrue(existUser == null, "邮箱已存在");
        }

        if (StrUtil.isNotBlank(record.getPassword())) {
            String encodePassword = BCrypt.hashpw(record.getPassword(), BCrypt.gensalt(UserConstants.BCRYPT_STRENGTH));
            userPO.setPassword(encodePassword);
        }

        userPO.setUpdateBy(getUserId());
        userPO.setUpdateUsername(getUsername());
        userPO.setUpdateTime(new Date());

        userMapper.updateById(userPO);

        if (StrUtil.isNotBlank(record.getPassword())) {
            StpUtil.kickout(record.getId());
        }
    }

    @Override
    public void update(UserPO record) {
        record.setUpdateTime(new Date());
        record.setUpdateBy(UserConstants.SYSTEM_USER_ID);
        record.setUpdateUsername(UserConstants.SYSTEM_USER_NAME);
        userMapper.updateById(record);
    }

    @Override
    public void delete(UserPO record) {
        userMapper.deleteById(record);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userMapper.deleteById(id);
        //TODO 删除用户所有的帖子
        topicService.deleteByUserId(id);
        //TODO 删除用户的角色信息
        roleService.deleteByUserId(id);
        //TODO 删除用户所有的收藏信息
        userCollectRelateService.deleteByUserId(id);
        //TODO 删除用户所有的点赞信息
        userLikeRelateService.deleteByUserId(id);
        //TODO 删除用户所有的评论
        commentService.deleteByUserId(id);
        //TODO 删除所有用户的关注信息
        userFollowRelateService.deleteByUserId(id);
        //TODO 删除用户管理的板块
        userTagManageService.deleteByUserId(id);

    }

    @Override
    public UserBO getUserInfo() {
        UserQuery userQuery = new UserQuery();
        userQuery.setId(getUserId());
        UserPO userPO = get(userQuery);
        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(userPO, userBO);
        LogBO lastLog = logService.getLastLog(getUserId(), LogTypeEnum.LOGIN);
        userBO.setLastLoginLog(lastLog);
        return userBO;
    }

    @Override
    public UserBO getUserGeneralInfo() {
        UserQuery userQuery = new UserQuery();
        userQuery.setId(getUserId());
        UserPO userPO = get(userQuery);
        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(userPO, userBO);
        userBO.setSigned(false);
        LogBO lastLog = logService.getLastLog(getUserId(), LogTypeEnum.SIGN_IN);
        if (lastLog != null) {
            LocalDate now = LocalDate.now();
            LocalDate lastSignedDay = LocalDate.ofInstant(lastLog.getCreateTime().toInstant(), ZoneId.systemDefault());

            if (now.equals(lastSignedDay)) {
                userBO.setSigned(true);
            }
        }
        return userBO;
    }

    @Override
    public UserBO getUserInfoByUserId(Long userId) {
        Assert.isTrue(userId != null, "用户ID不能为空");
        UserQuery userQuery = new UserQuery();
        userQuery.setId(userId);
        UserPO userPO = get(userQuery);
        UserBO userBO = new UserBO();
        Assert.isTrue(userPO != null, "用户ID不存在");
        BeanUtils.copyProperties(userPO, userBO);
        LogBO lastLog = logService.getLastLog(userId, LogTypeEnum.LOGIN);
        userBO.setLastLoginLog(lastLog);

        //判断当前登录用户是否关注目标用户
        userBO.setFollowed(false);
        UserFollowRelateQuery userFollowRelateQuery = new UserFollowRelateQuery();
        userFollowRelateQuery.setFollowedUserId(userId);
        userFollowRelateQuery.setFollowUserId(getUserId());
        UserFollowRelatePO userFollowRelatePO = userFollowRelateService.get(userFollowRelateQuery);
        if (userFollowRelatePO != null) {
            userBO.setFollowed(true);
        }

        List<RolePO> roles = roleService.getRolesByUserId(userId);
        if (CollUtil.isNotEmpty(roles)) {
            List<String> list = roles.stream().map(RolePO::getName).toList();
            userBO.setRoles(list);
        }

        return userBO;
    }

    @Override
    public Page<UserBO> userList(Integer pageNum, Integer pageSize) {
        Page<UserPO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        Page<UserPO> userPOPage = userMapper.selectPage(page, wrapper);

        List<UserBO> collect = userPOPage.getRecords().stream().map(userPO -> {
            UserBO userBO = new UserBO();
            BeanUtils.copyProperties(userPO, userBO);
            return userBO;
        }).toList();

        Page<UserBO> userBOPage = new Page<>();
        BeanUtils.copyProperties(userPOPage, userBOPage);
        userBOPage.setRecords(collect);
        return userBOPage;
    }

    @Override
    public void updateNickname(String nickname) {
        Assert.isTrue(StrUtil.isNotBlank(nickname), "昵称不能为空");

        Long userId = getUserId();

        UserPO userPO = userMapper.get(userId);
        Assert.isTrue(userPO != null, "非法请求");

        userPO.setNickname(nickname);
        userMapper.updateById(userPO);
    }

    @Override
    public void updateDescription(String description) {
        Assert.isTrue(StrUtil.isNotBlank(description), "个人签名不能为空");
        Long userId = getUserId();

        UserPO userPO = userMapper.get(userId);
        Assert.isTrue(userPO != null, "非法请求");

        userPO.setDescription(description);
        userMapper.updateById(userPO);
    }

    @Override
    public void updatePassword(UserChangePasswordDTO record) {
        Assert.isTrue(StrUtil.isNotBlank(record.getOldPwd()), "旧密码不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getNewPwd()), "新密码不能为空");

        Long userId = getUserId();

        UserPO userPO = userMapper.get(userId);
        Assert.isTrue(userPO != null, "非法请求");

        //判断密码是否正确
        boolean checkpw = BCrypt.checkpw(record.getOldPwd(), userPO.getPassword());
        Assert.isTrue(checkpw, "密码输入错误");

        String encodedPassword = BCrypt.hashpw(record.getNewPwd(), BCrypt.gensalt(UserConstants.BCRYPT_STRENGTH));
        userPO.setPassword(encodedPassword);
        userMapper.updateById(userPO);

        StpUtil.logout();

    }

    @Override
    public FileUploadBO uploadAvatar(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        Long userId = getUserId();

        UserPO userPO = userMapper.get(userId);
        Assert.isTrue(userPO != null, "非法请求");

        FileUploadBO fileUploadBO = uploadFileService.uploadFile(file);
        userPO.setAvatar(fileUploadBO.getFileUrl());
        userMapper.updateById(userPO);

        return fileUploadBO;

    }

    @Override
    public FileUploadBO uploadBackgroundImage(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Long userId = getUserId();

        UserPO userPO = userMapper.get(userId);
        Assert.isTrue(userPO != null, "非法请求");

        FileUploadBO fileUploadBO = uploadFileService.uploadFile(file);
        userPO.setBackgroundImage(fileUploadBO.getFileUrl());
        userMapper.updateById(userPO);

        return fileUploadBO;

    }

    @Override
    public Integer signIn() {
        UserPO userPO = userMapper.get(getUserId());
        int addScore = RandomUtil.randomInt(UserConstants.USER_SIGNIN_MIN_SCORE, UserConstants.USER_SIGNIN_MAX_SCORE);
        userPO.setScore(userPO.getScore() + addScore);
        userMapper.updateById(userPO);

        //让rabbitmq 添加签到日志
        AddLogMessage msg = new AddLogMessage();
        msg.setUserId(getUserId());
        msg.setType(LogTypeEnum.SIGN_IN.getCode());
        msg.setIp(getIp());
        msg.setLocation(getIpLocation());
        msg.setCreateTime(new Date());
        mqUtil.send("LogExchange.direct", "add.log", msg);

        return addScore;
    }

    @Override
    public List<UserRegisterStatisticPO> getAllUserRegisterStatistic() {
        LocalDate today = LocalDate.now();
        List<String> dateList = IntStream.rangeClosed(1, 30)
                .mapToObj(today::minusDays).map(date -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    return date.format(formatter);
                }).toList();
        List<UserRegisterStatisticPO> statisticList = userMapper.getAllUserRegisterStatistic();
        Map<String, UserRegisterStatisticPO> collect =
                statisticList.stream().collect(Collectors.toMap(UserRegisterStatisticPO::getTime, item -> item));

        dateList.forEach(date -> {
            if (!collect.containsKey(date)) {
                UserRegisterStatisticPO userRegisterStatisticPO = new UserRegisterStatisticPO();
                userRegisterStatisticPO.setTime(date);
                userRegisterStatisticPO.setCount(0);
                statisticList.add(userRegisterStatisticPO);
            }
        });

        List<UserRegisterStatisticPO> list = statisticList.stream().sorted(UserRegisterStatisticPO::compareTo).toList();
        return list;
    }

    @Override
    public Page<UserPO> query(UserQuery record, Integer pageNum, Integer pageSize) {
        Page<UserPO> page = new Page<>(pageNum, pageSize);
        Page<UserPO> query = userMapper.query(page, record);
        List<UserPO> list = query.getRecords().stream().map(userPO -> {
            userPO.setPassword(null);
            return userPO;
        }).toList();
        query.setRecords(list);
        return query;
    }

    @Override
    public void muteUser(UserMuteDTO record) {
        Assert.isTrue(record.getUserId() != null, "用户id不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getEndTime()), "禁言结束时间不能为空");
        DateTime endTime = DateUtil.parse(record.getEndTime(), "yyyy-MM-dd HH:mm:ss");
        Assert.isTrue(endTime.isAfter(new Date()), "禁言结束时间不能小于当前时间");

        UserPO userPO = userMapper.selectById(record.getUserId());
        Assert.isTrue(userPO != null, "用户不存在");
        userPO.setStatus(UserStatusEnum.MUTE.getCode());
        userPO.setMuteEndTime(endTime);

        userPO.setUpdateBy(getUserId());
        userPO.setUpdateTime(new Date());
        userPO.setUpdateUsername(getUsername());

        userMapper.updateById(userPO);
    }

    @Override
    @Transactional
    public void forbiddenUser(UserForbiddenDTO record) {
        Assert.isTrue(record.getUserId() != null, "用户id不能为空");
        Assert.isTrue(StrUtil.isNotBlank(record.getEndTime()), "封禁结束时间不能为空");
        DateTime endTime = DateUtil.parse(record.getEndTime(), "yyyy-MM-dd HH:mm:ss");
        Assert.isTrue(endTime.isAfter(new Date()), "封禁结束时间不能小于当前时间");
        Assert.isTrue(StrUtil.isNotBlank(record.getReason()), "封禁原因不能为空");

        UserPO userPO = userMapper.selectById(record.getUserId());
        Assert.isTrue(userPO != null, "用户不存在");
        Assert.isTrue(userPO.getStatus() != UserStatusEnum.FORBIDDEN.getCode(), "用户已处于封禁状态");
        Assert.isTrue(userPO.getStatus() != UserStatusEnum.MUTE.getCode(), "用户已处于禁言状态");

        userPO.setStatus(UserStatusEnum.FORBIDDEN.getCode());
        userPO.setForbiddenEndTime(endTime);
        userPO.setUpdateBy(getUserId());
        userPO.setUpdateTime(new Date());
        userPO.setUpdateUsername(getUsername());

        userMapper.updateById(userPO);

        ForbiddenLogAddDTO dto = new ForbiddenLogAddDTO();
        dto.setFinishTime(endTime);
        dto.setStartTime(new Date());
        dto.setUserId(record.getUserId());
        dto.setReason(record.getReason());
        forbiddenLogService.add(dto);

        StpUtil.kickout(record.getUserId());
    }

    @Override
    public void unMuteUser(Long userId) {
        Assert.isTrue(userId != null, "用户id不能为空");
        UserPO userPO = userMapper.selectById(userId);
        Assert.isTrue(userPO != null, "用户不存在");
        Assert.isTrue(UserStatusEnum.MUTE.getCode().equals(userPO.getStatus()), "用户未处于禁言状态");

        userPO.setStatus(UserStatusEnum.OK.getCode());
        userPO.setMuteEndTime(new Date());

        userPO.setUpdateTime(new Date());
        userPO.setUpdateUsername(getUsername());
        userPO.setUpdateBy(getUserId());
        userMapper.updateById(userPO);
    }

    @Override
    public void unForbiddenUser(Long userId) {
        Assert.isTrue(userId != null, "用户id不能为空");
        UserPO userPO = userMapper.selectById(userId);
        Assert.isTrue(userPO != null, "用户不存在");
        Assert.isTrue(UserStatusEnum.FORBIDDEN.getCode().equals(userPO.getStatus()), "用户未处于封禁状态");
        userPO.setStatus(UserStatusEnum.OK.getCode());
        userPO.setForbiddenEndTime(new Date());

        userPO.setUpdateTime(new Date());
        userPO.setUpdateUsername(getUsername());
        userPO.setUpdateBy(getUserId());
        userMapper.updateById(userPO);
    }

    @Override
    public List<UserPO> list(UserQuery record) {
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(record.getUsername()), "username", record.getUsername());
        wrapper.eq(StrUtil.isNotBlank(record.getEmail()), "email", record.getEmail());
        wrapper.eq(record.getStatus() != null, "status", record.getStatus());
        return userMapper.selectList(wrapper);
    }
}
