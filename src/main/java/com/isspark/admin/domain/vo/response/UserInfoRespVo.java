package com.isspark.admin.domain.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @version V1.0
 * @class: UserInfoRespVo
 * @description:
 * @author: xuzheng
 * @create: 2020-05-25 10:35
 **/
@Data
public class UserInfoRespVo {

    @NotNull(message = "用户名不能为空！")
    private String name;

    @ApiModelProperty(value = "真实姓名",dataType = "string")
    private String realName;

    @ApiModelProperty(value = "性别(0-男，1-女)",dataType = "int")
    private Integer sex;

    @ApiModelProperty(value = "手机号",dataType = "string")
    private String mobile;

    @ApiModelProperty(value = "菜单权限",dataType = "list")
    private List<UserMenu> menus;
}
