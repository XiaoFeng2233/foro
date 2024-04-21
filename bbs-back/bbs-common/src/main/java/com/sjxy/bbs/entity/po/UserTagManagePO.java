package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName t_user_tag_manage
 */
@TableName(value = "t_user_tag_manage")
@Data
public class UserTagManagePO extends BasePO {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 板块ID
     */
    @TableField(value = "tag_id")
    private Long tagId;


    @TableField(exist = false)
    private UserPO user;

    @TableField(exist = false)
    private TagPO tag;

}