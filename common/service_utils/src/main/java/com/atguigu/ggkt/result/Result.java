package com.atguigu.ggkt.result;

import lombok.Data;

@Data
public class Result<T> {


    private Integer code;

    private String message;

    private T data;

    public Result(){

    }

    /*//成功方法 无data
    public static<T> Result<T> ok(){
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("成功");
        return result;
    }

    //失败方法
    public static<T> Result<T> fail(){
        Result<T> result = new Result<>();
        result.setCode(201);
        result.setMessage("失败");
        return result;
    }*/

    //成功方法  有data
    public static<T> Result<T> ok(T data){
        Result<T> result = new Result<>();
        result.setCode(20000);
        result.setMessage("成功");
        if (data != null){
            result.setData(data);
        }
        return result;
    }

    //失败方法  有data
    public static<T> Result<T> fail(T data){
        Result<T> result = new Result<>();
        result.setCode(20001);
        result.setMessage("失败");
        if (data != null){
            result.setData(data);
        }
        return result;
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
}
