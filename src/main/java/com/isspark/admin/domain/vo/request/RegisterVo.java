package com.isspark.admin.domain.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @version V1.0
 * @class: LoginVo
 * @description:
 * @author: xuzheng
 * @create: 2020-05-18 11:27
 **/
@Data
@ApiModel("登入VO")
public class RegisterVo {

    @ApiModelProperty(value = "用户名",dataType = "string")
    @NotNull(message = "用户名不能为空！")
    private String name;

    @ApiModelProperty(value = "密码",dataType = "string")
    @NotNull(message = "密码不能为空！")
    private String password;

    @ApiModelProperty(value = "真实姓名",dataType = "string")
    private String realName;

    @ApiModelProperty(value = "性别(0-男，1-女)",dataType = "int")
    private Integer sex;

    @ApiModelProperty(value = "手机号",dataType = "string")
    private String mobile;

}
