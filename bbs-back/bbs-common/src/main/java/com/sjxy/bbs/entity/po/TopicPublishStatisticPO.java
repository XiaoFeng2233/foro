package com.sjxy.bbs.entity.po;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class TopicPublishStatisticPO implements Comparable<TopicPublishStatisticPO>{
    private String time;
    private Integer count;

    @Override
    public int compareTo(TopicPublishStatisticPO o) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate thisDate = LocalDate.parse(this.time,dateTimeFormatter);
        LocalDate targetDate = LocalDate.parse(o.time,dateTimeFormatter);
        if(thisDate.isBefore(targetDate)){
            return -1;
        }else if (thisDate.isAfter(targetDate)){
            return 1;
        }else{
            return 0;
        }
    }
}
