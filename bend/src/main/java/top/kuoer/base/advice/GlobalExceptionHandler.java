package top.kuoer.base.advice;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.kuoer.base.common.Result;
import top.kuoer.base.common.ResultCode;

@RestControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(NotLoginException.class)
    public Result handlerNotLoginException(NotLoginException nle) {
        return new Result(ResultCode.AUTHERROR, nle.getMessage());
    }

    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        return SaResult.error(e.getMessage());
    }

}
