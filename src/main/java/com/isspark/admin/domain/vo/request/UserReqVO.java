package com.isspark.admin.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isspark.admin.domain.vo.PageBaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @version V1.0
 * @class: UserReqVO
 * @description:
 * @author: xuzheng
 * @create: 2020-04-26 14:31
 **/
@Data
@ApiModel
public class UserReqVO extends PageBaseReqVo {

    @ApiModelProperty(value = "年龄",dataType = "int")
    private Integer age;

    @ApiModelProperty(value = "姓名",dataType = "string")
    private String name;

    @ApiModelProperty(value = "时间",dataType = "string",example = "2019-07-01")
    @JsonFormat(pattern = "yyyy-MM")
    private Date time;
}
