package com.sjxy.bbs.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.sjxy.bbs.entity.result.ObjectResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotPermissionException.class)
    public ObjectResult<String> handleNotPermissionException(NotPermissionException e) {
        ObjectResult<String> error = ObjectResult.forbidden("您无权访问");
        e.printStackTrace();
        return error;
    }

    @ExceptionHandler(NotLoginException.class)
    public ObjectResult<String> handleNotLoginException(NotLoginException e) {
        ObjectResult<String> error = ObjectResult.unauthorized("未登录，禁止访问");
        e.printStackTrace();
        return error;
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ObjectResult<String> handleNoHandlerFoundException(NoHandlerFoundException e) {
        ObjectResult<String> error = ObjectResult.fail("error",e.getMessage());
        e.printStackTrace();
        return error;
    }


    @ExceptionHandler(RuntimeException.class)
    public ObjectResult<String> handleRuntimeException(RuntimeException e) {
        ObjectResult<String> error = ObjectResult.fail("error",e.getMessage());
        e.printStackTrace();
        return error;
    }

    @ExceptionHandler(Exception.class)
    public ObjectResult<String> handleException(Exception e) {
        ObjectResult<String> error = ObjectResult.fail("error",e.getMessage());
        e.printStackTrace();
        return error;
    }
}
