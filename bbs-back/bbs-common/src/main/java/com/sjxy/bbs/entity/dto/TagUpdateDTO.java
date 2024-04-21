package com.sjxy.bbs.entity.dto;

import lombok.Data;

@Data
public class TagUpdateDTO {
    private Long id;
    private String name;
    private String description;
    private String color;
}
