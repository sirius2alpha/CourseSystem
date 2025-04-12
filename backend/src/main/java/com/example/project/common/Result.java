package com.example.project.common;

import lombok.Data;

/**
 * 统一响应结果类
 */
@Data
public class Result {
    
    private Integer code;
    private String message;
    private Object data;
    private Integer total;
    
    public static Result suc() {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("操作成功");
        return result;
    }
    
    public static Result suc(Object data) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }
    
    public static Result suc(Object data, Integer total) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        result.setTotal(total);
        return result;
    }
    
    public static Result fail() {
        Result result = new Result();
        result.setCode(400);
        result.setMessage("操作失败");
        return result;
    }
    
    public static Result fail(String message) {
        Result result = new Result();
        result.setCode(400);
        result.setMessage(message);
        return result;
    }
    
    public static Result error(String message) {
        Result result = new Result();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
    
    public static Result unauthorized() {
        Result result = new Result();
        result.setCode(401);
        result.setMessage("未授权");
        return result;
    }
}