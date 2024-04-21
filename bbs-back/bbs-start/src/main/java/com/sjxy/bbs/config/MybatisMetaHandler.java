package com.sjxy.bbs.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sjxy.bbs.entity.constants.UserConstants;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisMetaHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createBy = getFieldValByName("create_by", metaObject);
        if (createBy == null) {
            setFieldValByName("create_by", UserConstants.SYSTEM_USER_ID, metaObject);
        }

        Object createTime = getFieldValByName("create_time", metaObject);
        if (createTime == null) {
            setFieldValByName("create_time", LocalDateTime.now(), metaObject);
        }

        Object createUsername = getFieldValByName("create_username", metaObject);
        if (createUsername == null) {
            setFieldValByName("create_username", UserConstants.SYSTEM_USER_NAME, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateBy = getFieldValByName("update_by", metaObject);
        if (updateBy == null) {
            setFieldValByName("update_by", UserConstants.SYSTEM_USER_ID, metaObject);
        }

        Object updateTime = getFieldValByName("update_time", metaObject);
        if (updateTime == null) {
            setFieldValByName("update_time", LocalDateTime.now(), metaObject);
        }

        Object updateUsername = getFieldValByName("update_username", metaObject);
        if (updateUsername == null) {
            setFieldValByName("update_username", UserConstants.SYSTEM_USER_NAME, metaObject);
        }
    }
}
