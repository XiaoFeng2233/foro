package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;


@TableName(value = "t_tag")
@Data
public class TagPO extends BasePO {

    @TableField("name")
    private String name;
    @TableField("description")
    private String description;
    @TableField("status")
    private Integer status;
    @TableField("color")
    private String color;

    @TableField(exist = false)
    private UserPO createUser;
}