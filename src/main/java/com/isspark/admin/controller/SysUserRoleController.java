package com.isspark.admin.controller;


import com.isspark.admin.common.domain.Result;
import com.isspark.admin.service.SysUserRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/user/role")
public class SysUserRoleController {

    @Autowired
    SysUserRoleService userRoleService;

    @GetMapping(value = "/roleIds")
    @ApiOperation(value = "用户角色ID", notes = "用户角色ID", response = Result.class)
    public Result getRoleIds(@RequestParam("userId") @NotNull(message = "用户ID不能为空！") Integer userId) {
        return Result.success(userRoleService.getRoleIdsByUserId(userId));
    }
}

