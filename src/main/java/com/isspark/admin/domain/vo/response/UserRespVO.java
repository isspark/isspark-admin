package com.isspark.admin.domain.vo.response;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @version V1.0
 * @class: UserReqVO
 * @description:
 * @author: xuzheng
 * @create: 2020-04-13 17:51
 **/
@Data
@ApiModel
public class UserRespVO {

    @ApiModelProperty(value = "姓名",dataType = "string")
    @ExcelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "年龄",dataType = "int")
    @ExcelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "E-mail",dataType = "string")
    @ExcelProperty(value = "E-mail")
    private String email;

    @ApiModelProperty(value = "注册时间",dataType = "date")
    @ExcelProperty(value = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date registerTime;
}
