package com.sjxy.bbs.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UserRoleRelateAddDTO {
    private Long userId;

    private Long roleId;
}
