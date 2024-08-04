package org.chad.shortlink.project.common.web;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.chad.shortlink.project.domain.entity.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截参数验证异常
     */
    @SneakyThrows
    @ExceptionHandler(value = RuntimeException.class)
    public Result exceptionHandler() {
        return Result.error("出现异常");
    }
}