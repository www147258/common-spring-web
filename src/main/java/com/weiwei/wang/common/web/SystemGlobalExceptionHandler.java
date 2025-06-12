package com.weiwei.wang.common.web;

import com.weiwei.wang.common.domain.response.CommonResponse;
import com.weiwei.wang.common.exception.BusinessException;
import com.weiwei.wang.common.web.domain.response.ExceptionDataResponse;
import com.weiwei.wang.common.web.enums.ResponseExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestControllerAdvice
public class SystemGlobalExceptionHandler {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final Logger logger = LoggerFactory.getLogger(SystemGlobalExceptionHandler.class);



    @ExceptionHandler(BusinessException.class)
    public CommonResponse<ExceptionDataResponse> handlerBusinessException(BusinessException e) {
        logger.warn("handlerBusinessException ", e);
        CommonResponse fail = CommonResponse.fail(e);
        fail.setData(buildExceptionDataResponse(e));
        return fail;
    }

    //

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<ExceptionDataResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.warn("handlerMethodArgumentNotValidException ", e);
        FieldError fieldError = e.getFieldError();
        String field = fieldError.getField();
        String defaultMessage = fieldError.getDefaultMessage();

        String message = ResponseExceptionEnum.HANDLER_METHOD_ARGUMENT_NOT_VALID.getMessage();


        CommonResponse fail = CommonResponse.fail(ResponseExceptionEnum.HANDLER_METHOD_ARGUMENT_NOT_VALID);
        fail.setData(buildExceptionDataResponse(e));
        fail.setMessage(String.format(message, field, defaultMessage));
        return fail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResponse<ExceptionDataResponse> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.warn("handlerHttpMessageNotReadableException ", e);
        CommonResponse fail = CommonResponse.fail(ResponseExceptionEnum.HTTP_MESSAGE_NOT_READABLE);
        fail.setData(buildExceptionDataResponse(e));
        return fail;
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResponse<ExceptionDataResponse> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.warn("handlerHttpRequestMethodNotSupportedException ", e);
        CommonResponse fail = CommonResponse.fail(ResponseExceptionEnum.HTTP_REQUEST_METHOD_NOT_SUPPORTED);
        fail.setData(buildExceptionDataResponse(e));
        return fail;
    }


    private ExceptionDataResponse buildExceptionDataResponse(Exception e) {
        ExceptionDataResponse exceptionDataResponse = new ExceptionDataResponse();

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        String urlPath = "";

        if( requestAttributes instanceof  ServletRequestAttributes) {
            jakarta.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            urlPath = request.getServletPath();
        }
        exceptionDataResponse.setUrlPath(urlPath);

        exceptionDataResponse.setExceptionName(e.getClass().getName());

        exceptionDataResponse.setServerName(applicationName);
        return exceptionDataResponse;
    }



    @ExceptionHandler(Exception.class)
    public CommonResponse<ExceptionDataResponse> handlerException(Exception e) {

        logger.error("handlerException ", e);

        CommonResponse fail = CommonResponse.fail(ResponseExceptionEnum.SYSTEM_FAIL);
        fail.setData(buildExceptionDataResponse(e));
        return fail;

    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
