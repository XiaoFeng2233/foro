package com.sjxy.bbs.util;

import com.github.jarod.qqwry.IPZone;
import com.github.jarod.qqwry.QQWry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Slf4j
@Component
public class IpUtil {
    public String getIpLocation(String ip){
        ClassPathResource classPathResource = new ClassPathResource("qqwry.dat");
        try {
            String absolutePath = classPathResource.getFile().getAbsolutePath();
            QQWry qqWry = new QQWry(Paths.get(absolutePath));
            IPZone ipZone = qqWry.findIP(ip);
            return ipZone.getMainInfo();
        }catch (Exception e){
            log.error("IP地址转位置出错");
            throw new RuntimeException("IP地址转位置出错");
        }
    }
}
