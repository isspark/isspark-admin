package com.isspark.admin.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @version V1.0
 * @class: PageBaseReqVo
 * @description:
 * @author: xuzheng
 * @create: 2020-04-26 14:14
 **/
@ApiModel
@Data
public class PageBaseReqVo {

    @ApiModelProperty(value = "页码",dataType = "int",required = true)
    private Integer currentPage =1;

    @ApiModelProperty(value = "页面大小",dataType = "int",required = true)
    private Integer pageSize = 1;
}
