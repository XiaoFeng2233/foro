package com.sjxy.bbs.entity.enums;

public enum YesOrNoEnum {
    NO(0),
    YES(1);
    private Integer code;

    YesOrNoEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
