package com.example.project.common;

import lombok.Data;

@Data
public class Result {

    private int code;//编码 200/400
    private String msg;//成功/失败
    private Long total;//总记录数
    private Object data;//数据

    public static Result fail() {
        return result(400, "失败", 0L, null);
    }

    public static Result fail(String msg) {
        return result(500, msg, 0L, null);
    }

    public static Result suc() {
        return result(200, "成功", 0L, null);
    }

    public static Result suc(Object data) {
        return result(200, "成功", 0L, data);
    }

    public static Result suc(Object data, Long total) {
        return result(200, "成功", total, data);
    }

    private static Result result(int code, String msg, Long total, Object data) {
        Result res = new Result();
        res.setData(data);
        res.setMsg(msg);
        res.setCode(code);
        res.setTotal(total);
        return res;
    }

}
