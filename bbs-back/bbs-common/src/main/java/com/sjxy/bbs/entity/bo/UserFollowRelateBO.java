package com.sjxy.bbs.entity.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sjxy.bbs.entity.po.UserPO;
import lombok.Data;

@Data
public class UserFollowRelateBO {
    private Long id;

    private Long followUserId;

    private Long followedUserId;

    private UserBO followUser;

    private UserBO followedUser;

    private Boolean followed;
}
