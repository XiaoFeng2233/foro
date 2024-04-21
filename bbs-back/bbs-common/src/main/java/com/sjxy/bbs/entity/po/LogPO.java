package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;


@TableName(value ="t_log")
@Data
public class LogPO extends BasePO {

    @TableField("user_id")
    private Long userId;

    @TableField("type")
    private Integer type;

    @TableField("ip")
    private String ip;

    @TableField("location")
    private String location;

}