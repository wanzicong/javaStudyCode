package com.tensquare.qa.web.common;

import constants.StatusCode;

import entity.ResultDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类(通知类)
 */
@RestControllerAdvice
public class BaseExceptionHandler {
	@ExceptionHandler
    public ResultDTO error(Exception e){
        e.printStackTrace();
        return new ResultDTO(false, StatusCode.ERROR,e.getMessage());
    }
}
