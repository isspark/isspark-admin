package com.isspark.admin.common.exception;

/**
 * @version V1.0
 * @class: BusinessException
 * @description:
 * @author: xuzheng
 * @create: 2020-05-25 10:51
 **/
public class BusinessException extends RuntimeException {

    public BusinessException(){
        super();
    }

    public BusinessException(String message) {
        super(message);
    }
}
