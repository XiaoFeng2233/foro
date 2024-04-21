package com.sjxy.bbs.entity.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class PageResult<T> {
    private Integer code;
    private String msg;
    private List<T> data;
    private Long total;
    private Long size;
    private Long current;

    private PageResult() {

    }

    private static <T> PageResult<T> build(Integer code, String msg, IPage<T> iPage) {
        PageResult<T> r = new PageResult<>();
        r.setCode(code);
        r.setMsg(msg);
        if (iPage != null) {
            r.setData(iPage.getRecords());
            r.setCurrent(iPage.getCurrent());
            r.setSize(iPage.getSize());
            r.setTotal(iPage.getTotal());
        }

        return r;
    }

    public static <T> PageResult<T> ok(IPage<T> data) {
        return build(HttpStatus.OK.value(), "success", data);
    }

    public static <T> PageResult<T> ok(String msg, IPage<T> data) {
        return build(HttpStatus.OK.value(), msg, data);
    }

    public static <T> PageResult<T> fail(String msg, IPage<T> data) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    public static <T> PageResult<T> fail(IPage<T> data) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR.value(), "fail", data);
    }

    public static <T> PageResult<T> unauthorized(IPage<T> data) {
        return build(HttpStatus.UNAUTHORIZED.value(), "unauthorized", data);
    }

    public static <T> PageResult<T> forbidden(IPage<T> data) {
        return build(HttpStatus.FORBIDDEN.value(), "forbidden", data);
    }

}
