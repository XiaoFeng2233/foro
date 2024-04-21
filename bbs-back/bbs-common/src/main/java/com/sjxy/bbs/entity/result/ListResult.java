package com.sjxy.bbs.entity.result;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ListResult<T> {

    private Integer code;
    private String msg;
    private List<T> data;

    private ListResult(){

    }

    private static <T> ListResult<T> build(Integer code, String msg, List<T> data) {
        ListResult<T> r = new ListResult<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static <T> ListResult<T> ok(List<T> data) {
        return build(HttpStatus.OK.value(), "success", data);
    }

    public static <T> ListResult<T> ok(String msg, List<T> data) {
        return build(HttpStatus.OK.value(), msg, data);
    }

    public static <T> ListResult<T> fail(String msg, List<T> data) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    public static <T> ListResult<T> fail(List<T> data) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR.value(), "fail", data);
    }

    public static <T> ListResult<T> unauthorized(List<T> data) {
        return build(HttpStatus.UNAUTHORIZED.value(), "unauthorized", data);
    }

    public static <T> ListResult<T> forbidden(List<T> data) {
        return build(HttpStatus.FORBIDDEN.value(), "forbidden", data);
    }

}
