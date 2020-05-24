package com.isspark.admin.common.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isspark.admin.common.enums.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：返回参数包装类
 * @Date ：2020/1/19 11:36
 * @Author ：HX0012875
 */
@Data
@ApiModel
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -4355486238624866684L;

    public static final String SUCCESS_CODE = "200";

    public static final String ERROR_CODE = "500";

    public static final String FAIL_CODE = "999";

    /**
     * 结果码
     */
    @ApiModelProperty(name = "code", value = "接口返回的状态码")
    private String code;

    /**
     * 提示信息
     */
    @ApiModelProperty(name = "message", value = "接口返回的提示信息")
    private String message;

    /**
     * 具体内容
     */
    @ApiModelProperty(name = "data", value = "接口返回的具体内容")
    private T data;

    public Result(){
        this.code="0";
        this.message="success";
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMsg();
        this.data = data;
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, "success", null);
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "success", data);
    }
    public static <T> Result<T> fail() {
        return new Result<>(FAIL_CODE, "fail", null);
    }
    public static <T> Result<T> fail(String message) {
        return new Result<>(FAIL_CODE, message, null);
    }
    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(code, message, null);
    }
    public static <T> Result<T> fail(String code, String message,T data) {
        return new Result<>(code, message, data);
    }
    public static <T> Result<T> fail(ResultEnum resultEnum){
        return new Result<>(resultEnum,null);
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
