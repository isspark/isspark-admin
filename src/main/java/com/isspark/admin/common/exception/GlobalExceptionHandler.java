package com.isspark.admin.common.exception;

import com.isspark.admin.common.domain.Result;
import com.isspark.admin.common.enums.ResultEnum;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @version V1.0
 * @class: GlobalExceptionHandler
 * @description:
 * @author: xuzheng
 * @create: 2020-03-24 11:32
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        StringBuilder errorInfo = new StringBuilder();
        BindingResult bindingResult = exception.getBindingResult();
        for(int i = 0; i < bindingResult.getFieldErrors().size(); i++){
            if(i > 0){
                errorInfo.append(",");
            }
            FieldError fieldError = bindingResult.getFieldErrors().get(i);
            errorInfo.append(fieldError.getField()).append(" :").append(fieldError.getDefaultMessage());
        }

        //返回BaseResponse
        Result<String> response = new Result<>();
        response.setMessage(errorInfo.toString());
        response.setCode(Result.ERROR_CODE);
        return response;
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleConstraintViolationException(ConstraintViolationException exception) {
        StringBuilder errorInfo = new StringBuilder();
        String errorMessage ;

        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        for (ConstraintViolation<?> item : violations) {
            errorInfo.append(item.getMessage()).append(",");
        }
        errorMessage = errorInfo.toString().substring(0, errorInfo.toString().length()-1);

        Result response = new Result();
        response.setMessage(errorMessage);
        response.setCode(Result.ERROR_CODE);
        return response;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleBindException(BindException exception){

        String msg = null;
        List<ObjectError> errors =  exception.getAllErrors();
        if(errors != null && errors.size()> 0){
            msg = errors.get(0).getDefaultMessage();
        }
        return Result.fail(msg);
    }

    @ExceptionHandler(AuthorizationException.class)
    public Result handleAuthorizationException(AuthorizationException exception){
        return Result.fail(ResultEnum.UNAUTH.getCode(),exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Result handleAuthorizationException(UnauthorizedException exception){
        return Result.fail(ResultEnum.UNAUTH.getCode(),exception.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result handleAuthorizationException(BusinessException exception){
        return Result.fail(ResultEnum.BUSINESS_ERROR.getCode(),exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleDefaultException(Exception exception) {
        exception.printStackTrace();
        Result response = new Result<>();
        response.setMessage("其他错误");
        response.setCode(Result.ERROR_CODE);
        return response;
    }
}
