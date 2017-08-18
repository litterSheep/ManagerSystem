package com.xinzhi.system.exception;


import com.xinzhi.system.response.ResultCode;

/**
 * Created by ly on 2017/7/1 18:00
 */
public class MyException extends RuntimeException {

    private Integer code;

    public MyException(ResultCode resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
