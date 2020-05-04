package com.tensquare.base.web.common;

import constants.StatusCode;
import entity.ResultDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*公共异常处理类*/
@RestControllerAdvice
public class BaseExceptionHandler {
    /* 默认处理所有异常*/
    @ExceptionHandler
    public ResultDTO error(Throwable e) {
        e.printStackTrace();
        return new ResultDTO(false, StatusCode.ERROR, e.getMessage());
    }
}
