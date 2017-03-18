package net.dingyabin.com.result;

import com.alibaba.fastjson.JSON;

/**
 * Created by MrDing
 * Date: 2017/2/25.
 * Time:5:58
 */
public class Response<T> {

    private String code;

    private T data;

    private boolean success=true;

    private String message;

    public Response() {
    }

    public Response(String code, boolean success, String message, T data) {
        this.code = code;
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public static Response ok() {
        return new Response().Code("200").Success(true).Message("成功！");
    }


    public static Response error() {
        return new Response().Code("400").Success(false).Message("失败！");
    }


    public Response Code(String code) {
        this.code = code;
        return this;
    }


    public Response Data(T data) {
        this.data = data;
        return this;
    }


    public Response Message(String message) {
        this.message = message;
        return this;
    }

    public Response Success(boolean success) {
        this.success = success;
        return this;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }


    public T getData() {
        return data;
    }


    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
