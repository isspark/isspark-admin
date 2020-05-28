package com.isspark.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.isspark.admin.domain.entity.SysUser;
import com.isspark.admin.domain.entity.User;
import com.isspark.admin.domain.vo.request.RegisterVo;
import com.isspark.admin.service.SysUserService;
import com.isspark.admin.service.UserService;
import com.isspark.admin.common.domain.Result;
import com.isspark.admin.common.enums.ResultEnum;
import com.isspark.admin.domain.vo.request.LoginVo;
import com.isspark.admin.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @version V1.0
 * @class: AuthController
 * @description:
 * @author: xuzheng
 * @create: 2020-05-18 10:36
 **/
@RestController
@Api(value = "授权服务", tags = "授权服务")
@Slf4j
public class AuthController {

    @Autowired
    SysUserService userService;

    @GetMapping(value = "/index")
    @ApiOperation(value = "首页", notes = "首页", response = Result.class)
    public Result index() {
        return Result.success("Hello World！");
    }

    @GetMapping(value = "/login")
    @ApiOperation(value = "登录", notes = "登录", response = Result.class)
    public Result login(@Valid LoginVo vo) {
        //添加用户认证信息
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("mobile", vo.getUsername());
        SysUser user = userService.getOne(wrapper);
        if (ObjectUtils.isEmpty(user)) {
            return Result.fail(ResultEnum.LOGIN_ERROR);
        }
        if (!vo.getPassword().equals(user.getPassword())) {
            return Result.fail(ResultEnum.LOGIN_ERROR);
        }
        if (1 == user.getStatus()) {
            return Result.fail(ResultEnum.ACCOUNT_DISABLED);
        }
        return Result.success(JWTUtil.createToken(user.getName()));
    }

    @GetMapping(value = "/logout")
    @ApiOperation(value = "注销", notes = "注销", response = Result.class)
    public Result logout() {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
        return Result.success();
    }

    @GetMapping(value = "/unAuth")
    @ApiOperation(value = "未授权", notes = "未授权", response = Result.class)
    public Result unAuth() {
        return Result.fail(ResultEnum.UNAUTH);
    }

    @PostMapping(value = "/signUp")
    @ApiOperation(value = "注册", notes = "注册", response = Result.class)
    public Result signUp(RegisterVo vo) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(vo, user);
        Boolean result = userService.save(user);
        if (result) {
            return Result.success("Sign Up Success ，" + user.getName());
        }
        return Result.fail("Sign Up fail!");
    }
}
