package com.tensquare.gathering.web.common;

import constants.StatusCode;
import entity.ResultDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * controller的公共异常处理类
 */
//@ControllerAdvice//对所有controller的对象中的所有方法进行增强
//@ResponseBody
//组合注解，相当于@ControllerAdvice+@ResponseBody
@RestControllerAdvice
public class BaseExceptionHandler {

    //用来增强的方法。。。
    //处理异常
    //相当于AOP的异常通知
    @ExceptionHandler//默认抓取所有类型异常
//    @ExceptionHandler(java.lang.ArithmeticException.class)//只抓取指定的异常
//    @ResponseBody
    public ResultDTO error(Throwable e) {
        //记录日志（发邮件、发短信、、、、）
        System.out.println("记录日志：发生了异常");
        e.printStackTrace();
        //返回响应结果
//        return  new ResultDTO(false, StatusCode.ERROR,"系统异常繁忙");
        return new ResultDTO(false, StatusCode.ERROR, e.getMessage());
    }
}
