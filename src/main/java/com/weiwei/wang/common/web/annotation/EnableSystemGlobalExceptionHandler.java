package com.weiwei.wang.common.web.annotation;


import com.weiwei.wang.common.web.SystemGlobalExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(SystemGlobalExceptionHandler.class)
public @interface EnableSystemGlobalExceptionHandler {
}
