package com.sjxy.bbs.entity.mq;

import lombok.Data;

@Data
public class UpdateUserStatusMessage extends BaseMessage {
    private Long userId;
    private Integer oldStatus;
    private Integer newStatus;
}
