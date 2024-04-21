package com.sjxy.bbs.entity.bo;

import lombok.Data;

@Data
public class StatisticBO {
    private Long userCount;
    private Long topicCount;
    private Long commentCount;
    private Long tagCount;
}
