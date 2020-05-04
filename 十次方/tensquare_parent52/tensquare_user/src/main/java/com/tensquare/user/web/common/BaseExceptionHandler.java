package com.tensquare.user.web.common;

import constants.StatusCode;
import entity.ResultDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类(通知类)
 */
//@ControllerAdvice
//组合注解，相当于@ControllerAdvice+@ResponseBody
@RestControllerAdvice
public class BaseExceptionHandler {

//    @RequestBody
    //增强的方法：异常通知处理
    //@ExceptionHandler(Exception.class)
	@ExceptionHandler
    public ResultDTO error(Throwable e){
        e.printStackTrace();
        return new ResultDTO(false, StatusCode.ERROR,e.getMessage());
    }
}
