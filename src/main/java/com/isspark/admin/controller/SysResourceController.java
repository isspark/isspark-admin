package com.isspark.admin.controller;


import com.isspark.admin.common.domain.Result;
import com.isspark.admin.service.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/resource")
@Api(value = "资源服务", tags = "资源服务")
public class SysResourceController {

    @Autowired
    SysResourceService resourceService;

    @GetMapping(value = "/list/tree")
    @ApiOperation(value = "资源列表", notes = "资源列表", response = Result.class)
    public Result list(){
        return Result.success(resourceService.getTreeResources());
    }
}

