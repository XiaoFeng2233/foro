package com.sjxy.bbs.aop;

import com.sjxy.bbs.entity.bo.SiteConfigBO;
import com.sjxy.bbs.service.ConfigService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect

public class RegisterAspect {
    @Autowired
    private ConfigService configService;

    @Pointcut("@annotation(com.sjxy.bbs.aop.RegisterAnnotation)")
    public void checkOpenRegister(){}

    @Before("checkOpenRegister()")
    public void check(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        RegisterAnnotation annotation = signature.getMethod().getAnnotation(RegisterAnnotation.class);
        if(annotation.checkOpenRegister()){
            SiteConfigBO siteConfig = configService.getSiteConfig();
            if(!siteConfig.getOpenRegister()) {
                throw new RuntimeException("论坛已关闭注册");
            }
        }

    }
}
