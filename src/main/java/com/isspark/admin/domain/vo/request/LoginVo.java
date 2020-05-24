package com.isspark.admin.domain.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @version V1.0
 * @class: LoginVo
 * @description:
 * @author: xuzheng
 * @create: 2020-05-18 11:27
 **/
@Data
@ApiModel("注册VO")
public class LoginVo {

    @ApiModelProperty(value = "用户名",dataType = "string")
    @NotBlank(message = "用户名不能为空！")
    private String name;

    @ApiModelProperty(value = "密码",dataType = "string")
    @NotBlank(message = "密码不能为空！")
    private String password;

}
