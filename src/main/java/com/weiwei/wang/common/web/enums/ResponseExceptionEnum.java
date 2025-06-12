package com.weiwei.wang.common.web.enums;

import com.weiwei.wang.common.exception.ExceptionResponse;

public enum ResponseExceptionEnum implements ExceptionResponse {

    SYSTEM_FAIL(1, "操作失败！！！"),
    ;


    ResponseExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
