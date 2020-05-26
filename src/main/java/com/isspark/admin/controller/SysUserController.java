package com.isspark.admin.controller;


import com.isspark.admin.common.consts.SystemConst;
import com.isspark.admin.common.domain.Result;
import com.isspark.admin.domain.vo.request.AddUserReqVo;
import com.isspark.admin.domain.vo.request.UserPageReqVo;
import com.isspark.admin.service.SysUserService;
import com.isspark.admin.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @GetMapping(value = "/page")
    @ApiOperation(value = "用户分页", notes = "用户分页", response = Result.class)
    public Result page(UserPageReqVo vo) {
        return Result.success(userService.page(vo));
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "用户列表", notes = "用户列表", response = Result.class)
    public Result list(String username,String mobile){
        return Result.success(userService.list(username,mobile));
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增用户", notes = "新增用户", response = Result.class)
    public Result addUser(@RequestBody @Valid AddUserReqVo addUserReqVo){
        Boolean result = userService.addUser(addUserReqVo);
        if(result){
            return Result.success();
        }
        return Result.fail("用户添加失败！");
    }
}

