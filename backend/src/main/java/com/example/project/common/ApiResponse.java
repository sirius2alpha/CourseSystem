package com.example.project.common;

import lombok.Data;

/**
 * 统一API响应结果
 */
@Data
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T data;
    private Integer total;

    /**
     * 成功响应
     */
    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    /**
     * 成功响应（带数据）
     */
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("操作成功");
        response.setData(data);
        return response;
    }

    /**
     * 成功响应（带数据和总数）
     */
    public static <T> ApiResponse<T> success(T data, Integer total) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("操作成功");
        response.setData(data);
        response.setTotal(total);
        return response;
    }

    /**
     * 失败响应
     */
    public static <T> ApiResponse<T> fail() {
        return fail("操作失败");
    }

    /**
     * 失败响应（带消息）
     */
    public static <T> ApiResponse<T> fail(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(400);
        response.setMessage(message);
        return response;
    }

    /**
     * 错误响应
     */
    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(500);
        response.setMessage(message);
        return response;
    }

    /**
     * 未授权响应
     */
    public static <T> ApiResponse<T> unauthorized() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(401);
        response.setMessage("未授权");
        return response;
    }
}