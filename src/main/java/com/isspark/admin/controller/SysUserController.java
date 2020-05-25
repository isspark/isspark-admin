package com.isspark.admin.controller;


import com.isspark.admin.common.consts.SystemConst;
import com.isspark.admin.common.domain.Result;
import com.isspark.admin.service.SysUserService;
import com.isspark.admin.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@RestController
@Api(value = "用户服务", tags = "用户服务")
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    SysUserService userService;

    @GetMapping(value = "/info")
    @ApiOperation(value = "用户信息", notes = "用户信息", response = Result.class)
    public Result getInfo(HttpServletRequest request) {
        String username = JWTUtil.getUsername(request.getHeader(SystemConst.TOKEN_NAME));
        if(StringUtils.isBlank(username)){
            return Result.fail("用户信息获取失败");
        }
        return Result.success(userService.getUserInfo(username));
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "用户列表", notes = "用户列表", response = Result.class)
    public Result list(String username,String mobile){
        return Result.success(userService.list(username,mobile));
    }
}

