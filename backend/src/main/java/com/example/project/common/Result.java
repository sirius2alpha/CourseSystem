package com.example.project.common;

import lombok.Data;

@Data
public class Result {

    private int code;//编码 200/400
    private String msg;//成功/失败
    private int total;//总记录数
    private Object data;//数据

    public static Result fail(String msg){
        return result(401,msg,0,null);
    }

    public static Result fail(){
        return result(401,"error",0,null);
    }

    public static Result suc(){
        return result(200,"成功",0,null);
    }

    public static Result suc(Object data){
        return result(200,"成功",0,data);
    }

    public static Result suc(Object data,int total){
        return result(200,"成功",total,data);
    }

    private static Result result(int code,String msg,int total,Object data){
        Result res=new Result();
        res.setData(data);
        res.setMsg(msg);
        res.setCode(code);
        res.setTotal(total);
        return res;
    }

}
