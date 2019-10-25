package com.lvmen.manager.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理
 *  就是在Controller上面包裹一层，出现异常时，直接处理返回执行的异常信息。如果这个过程中出现异常
 *  则继续抛出到MyErrorController。
 * Created by lvmen on 2019/10/25
 */
@ControllerAdvice(basePackages = {"com.lvmen.manager.controller"}) // 对Controller的增强
public class ErrorControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleException(Exception e){

        Map<String, Object> attrs = new HashMap<>();
        String errorCode = e.getMessage();
        ErrorEnum errorEnum = ErrorEnum.getByCode(errorCode);
        attrs.put("message",errorEnum.getMessage());
        attrs.put("code",errorEnum.getCode());
        attrs.put("canRetry", errorEnum.getCanRetry());
        attrs.put("type", "advice"); // 仅仅是对两种方式进行一下区别
        return new ResponseEntity(attrs, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
