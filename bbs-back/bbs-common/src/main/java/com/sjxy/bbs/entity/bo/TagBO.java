package com.sjxy.bbs.entity.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sjxy.bbs.entity.po.UserPO;
import lombok.Data;

import java.util.List;

@Data
public class TagBO {
    private Long id;
    private String name;
    private String color;
    private String description;

    private UserBO createUser;
    private List<UserBO> managerList;
}
