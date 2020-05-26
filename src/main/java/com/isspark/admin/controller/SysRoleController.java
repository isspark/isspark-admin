package com.isspark.admin.controller;


import com.isspark.admin.common.domain.Result;
import com.isspark.admin.domain.vo.request.AddRoleReqVo;
import com.isspark.admin.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/role")
@Api(value = "角色服务", tags = "角色服务")
public class SysRoleController {

    @Autowired
    SysRoleService roleService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "角色列表", notes = "角色列表", response = Result.class)
    public Result list(){
        return Result.success(roleService.list());
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增角色", notes = "新增角色", response = Result.class)
    public Result add(@RequestBody @Valid AddRoleReqVo vo){
        return Result.success(roleService.addRole(vo));
    }

}

