package com.sjxy.bbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.sjxy.bbs.mapper")
@EnableCaching
public class BbsApplication {

    public static void main(String[] args) {

        SpringApplication.run(BbsApplication.class, args);
    }

}
