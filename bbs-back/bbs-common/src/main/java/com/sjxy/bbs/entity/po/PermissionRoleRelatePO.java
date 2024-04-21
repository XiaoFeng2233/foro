package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@TableName(value ="t_permission_role_relate")
@Data
public class PermissionRoleRelatePO extends BasePO {

    @TableField("permission_id")
    private Long permissionId;

    @TableField("role_id")
    private Long roleId;

}