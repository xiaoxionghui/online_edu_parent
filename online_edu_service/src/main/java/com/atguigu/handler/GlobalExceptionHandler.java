package com.atguigu.handler;


import com.atguigu.exception.EduException;
import com.atguigu.response.RetVal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName GlobalExceptionHandler
 * @Description:统一异常处理器
 * @Author xiaoxionghui
 * @Date 2020/11/2 20:53
 * @Version V1.0
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RetVal error(Exception e){
        System.out.println("处理异常信息");
        return RetVal.error().message("出现全局异常啦");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public RetVal error(ArithmeticException e){
        System.out.println("处理特殊异常");
        return RetVal.error().message("出现特殊异常了");
    }

    @ExceptionHandler(EduException.class)
    @ResponseBody
    public RetVal error(EduException e){
        e.printStackTrace();
        return RetVal.error().message(e.getMessage());
    }
}
