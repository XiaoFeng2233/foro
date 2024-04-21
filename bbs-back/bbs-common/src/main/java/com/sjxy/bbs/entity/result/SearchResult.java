package com.sjxy.bbs.entity.result;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class SearchResult<T> {
    private Integer code;
    private String msg;
    private List<T> data;
    private Long total;
    private Long size;
    private Long current;

    private SearchResult() {

    }

    private static <T> SearchResult<T> build(Integer code, String msg, List<T> data, Long pageNum, Long pageSize, Long pageTotal) {
        SearchResult<T> r = new SearchResult<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        r.setSize(pageSize);
        r.setTotal(pageTotal);
        r.setCurrent(pageNum);
        return r;
    }

    public static <T> SearchResult<T> ok(List<T> data, Long pageNum, Long pageSize, Long pageTotal) {
        return build(HttpStatus.OK.value(), "success", data, pageNum, pageSize, pageTotal);
    }

    public static <T> SearchResult<T> ok(String msg, List<T> data, Long pageNum, Long pageSize, Long pageTotal) {
        return build(HttpStatus.OK.value(), msg, data, pageNum, pageSize, pageTotal);
    }


    public static <T> SearchResult<T> fail(String msg, List<T> data) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data, 0L, 0L, 0L);
    }

    public static <T> SearchResult<T> fail(List<T> data) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR.value(), "fail", data, 0L, 0L, 0L);
    }

    public static <T> SearchResult<T> unauthorized(List<T> data) {
        return build(HttpStatus.UNAUTHORIZED.value(), "unauthorized", data, 0L, 0L, 0L);
    }

    public static <T> SearchResult<T> forbidden(List<T> data) {
        return build(HttpStatus.FORBIDDEN.value(), "forbidden", data, 0L, 0L, 0L);
    }

}
