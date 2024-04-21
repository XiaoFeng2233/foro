package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.ClobTypeHandler;

@TableName(value = "t_config")
@Data
public class ConfigPO extends BasePO {

    @TableField("name")
    private String name;

    @TableField(typeHandler = ClobTypeHandler.class)
    private String value;


}