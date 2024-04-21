package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_permission")
@Data
public class PermissionPO extends BasePO {

    @TableField("name")
    private String name;

    @TableField("code")
    private String code;
}