package com.sjxy.bbs.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class UserBO {

    private Long id;

    private String username;

    private String email;

    private String nickname;

    private String avatar;

    private Integer gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    private String backgroundImage;

    private String description;

    private Integer score;

    private Integer topicCount;

    private Integer commentCount;

    private Integer followCount;

    private Integer collectCount;

    private Integer fansCount;

    private Date createTime;

    private LogBO lastLoginLog;

    private Boolean signed;

    private Boolean followed;

    private List<String> roles;
}
