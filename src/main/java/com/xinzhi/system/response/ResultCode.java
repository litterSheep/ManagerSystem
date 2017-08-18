package com.xinzhi.system.response;

/**
 * Created by ly on 2017/7/1 16:16.
 */
public enum ResultCode {
    SUCCESS(0, "成功"),
    ERROR(-1, "失败"),
    UNKNOWN_ERROR(-2, "未知错误");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
