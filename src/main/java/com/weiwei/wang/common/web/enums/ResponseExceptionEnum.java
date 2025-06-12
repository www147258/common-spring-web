package com.weiwei.wang.common.web.enums;

import com.weiwei.wang.common.exception.ExceptionResponse;

public enum ResponseExceptionEnum implements ExceptionResponse {

    SYSTEM_FAIL(1, "操作失败！！！"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(2, "http请求方法不支持"),

    HTTP_MESSAGE_NOT_READABLE(3, "http请求内容无法读取，可能格式错误，请检查是否有请求参数或者请求参数格式错误"),

    HANDLER_METHOD_ARGUMENT_NOT_VALID(4,"参数校验失败,字段[%s]，原因[%s]"),
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
