package com.sjxy.bbs.entity.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class NoticeBO {

    private Long id;

    private String image;


    private Long userId;


    private String content;

    private Date createTime;
}
