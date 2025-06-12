package com.weiwei.wang.common.web;

import com.weiwei.wang.common.domain.response.CommonResponse;
import com.weiwei.wang.common.web.domain.response.ExceptionDataResponse;
import com.weiwei.wang.common.web.enums.ResponseExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @ExceptionHandler(Exception.class)
    public CommonResponse<ExceptionDataResponse> handlerException(Exception e) {

        logger.error("handlerException ", e);

        ExceptionDataResponse exceptionDataResponse = new ExceptionDataResponse();

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        String urlPath = "";

        if( requestAttributes instanceof  ServletRequestAttributes) {
            jakarta.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            urlPath = request.getServletPath();



        }
        exceptionDataResponse.setUrlPath(urlPath);
        exceptionDataResponse.setExceptionName(e.getClass().getName());

        exceptionDataResponse.setExceptionName(applicationName);

        CommonResponse fail = CommonResponse.fail(ResponseExceptionEnum.SYSTEM_FAIL);
        fail.setData(exceptionDataResponse);
        return fail;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
