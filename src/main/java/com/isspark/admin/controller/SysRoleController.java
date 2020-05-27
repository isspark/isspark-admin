package com.isspark.admin.controller;


import com.isspark.admin.common.domain.Result;
import com.isspark.admin.domain.vo.request.AddRoleReqVo;
import com.isspark.admin.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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

    @GetMapping(value = "/list/tree")
    @ApiOperation(value = "角色树", notes = "角色树", response = Result.class)
    public Result tree(){
        return Result.success(roleService.getTreeRoles());
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增/更新角色", notes = "新增/更新角色", response = Result.class)
    public Result add(@RequestBody @Valid AddRoleReqVo vo){
        return Result.success(roleService.addOrUpdate(vo));
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除角色", notes = "删除角色", response = Result.class)
    public Result deleteRole(@RequestParam("roleId") @NotBlank(message = "角色ID不能为空！") Long roleId){
        boolean result = roleService.deleteRoleAndResource(roleId);
        if(result){
            return Result.success();
        }
        return Result.fail("角色删除失败");
    }

}

