package com.example.springboot.comon;

import lombok.Data;

@Data
public class Result {
    public static final String SUCCESS_CODE = "200";
    public static final String ERROR_CODE = "404";

    private String code;
    private Object data;
    private String msg;

    // 成功的静态方法
    public static Result success() {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setMsg(msg);
        return result;
    }

    // 错误的静态方法
    public static Result error() {
        Result result = new Result();
        result.setCode(ERROR_CODE);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(ERROR_CODE);
        result.setMsg(msg);
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}

