package com.isspark.admin.controller;


import com.isspark.admin.common.domain.Result;
import com.isspark.admin.domain.vo.request.AddResourceReqVo;
import com.isspark.admin.service.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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


    @PostMapping(value = "/add")
    @ApiOperation(value = "更新资源", notes = "更新资源", response = Result.class)
    public Result addOrUpdate(@RequestBody @Valid AddResourceReqVo vo){
        Boolean result = resourceService.addOrUpdateResource(vo);
        if(result){
            return Result.success("资源更新成功！");
        }
        return Result.fail("资源更新失败！");
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除资源", notes = "删除资源", response = Result.class)
    public Result deleteUser(@RequestParam("resourceId") @NotBlank(message = "资源ID不能为空！") Long resourceId){
        boolean result = resourceService.delete(resourceId);
        if(result){
            return Result.success();
        }
        return Result.fail("删除资源失败!");
    }
}

