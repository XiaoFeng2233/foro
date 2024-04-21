package com.sjxy.bbs.entity.result;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ObjectResult<T> {

    private Integer code;
    private String msg;
    private T data;

    private ObjectResult() {

    }

    private static <T> ObjectResult<T> build(Integer code, String msg, T data) {
        ObjectResult<T> r = new ObjectResult<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static <T> ObjectResult<T> ok(T data) {
        return build(HttpStatus.OK.value(), "success", data);
    }

    public static <T> ObjectResult<T> ok(String msg, T data) {
        return build(HttpStatus.OK.value(), msg, data);
    }

    public static <T> ObjectResult<T> fail(String msg, T data) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    public static <T> ObjectResult<T> fail(T data) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR.value(), "fail", data);
    }

    public static <T> ObjectResult<T> unauthorized(T data) {
        return build(HttpStatus.UNAUTHORIZED.value(), "unauthorized", data);
    }

    public static <T> ObjectResult<T> forbidden(T data) {
        return build(HttpStatus.FORBIDDEN.value(), "forbidden", data);
    }
}
