package com.lagou.base;

import com.alibaba.fastjson.JSONObject;

public enum StatusCode {

    SUCCESS(0, "success"), FAIL(1, "fail");

    private int code; // 状态码 0-成功 1-失败
    private String message; // 状态描述信息

    StatusCode() {
    }

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 重写toString()方法，把枚举对象转换为JSON
     * @return
     */
    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("status", code);
        obj.put("message", message);
        String result = obj.toString();
        return result;
    }
}
