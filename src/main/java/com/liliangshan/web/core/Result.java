package com.liliangshan.web.core;

/************************************
 * Result
 * @author liliangshan
 * @date 2020/5/23
 ************************************/
public class Result {

    private int code;
    private String message;
    private Object data;

    public Result(Object data) {
        this.code = 200;
        this.message = "ok";
        this.data = data;
    }

    public Result(String message, Object data) {
        this(200, message, data);
    }

    public Result(int code, Object data) {
        this(code, "ok", data);
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

}
