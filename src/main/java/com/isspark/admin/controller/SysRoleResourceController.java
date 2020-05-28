package com.isspark.admin.controller;


import com.isspark.admin.common.domain.Result;
import com.isspark.admin.service.SysRoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 角色资源表 前端控制器
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/roleResource")
@Api(value = "角色资源服务", tags = "角色资源服务")
public class SysRoleResourceController {

    @Autowired
    private SysRoleResourceService roleResourceService;

    @GetMapping(value = "/resourceIds/byRoleId")
    @ApiOperation(value = "资源ID列表", notes = "资源ID列表", response = Result.class)
    public Result getResourceIds(@RequestParam("roleId") @NotNull(message = "用户ID不能为空！") Long roleId) {
        return Result.success(roleResourceService.getResourceIdsByRoleId(roleId));
    }
}

