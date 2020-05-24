package com.isspark.admin.common.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.isspark.admin.domain.entity.SysResource;
import com.isspark.admin.domain.entity.SysRole;
import com.isspark.admin.domain.entity.SysUser;
import com.isspark.admin.domain.entity.User;
import com.isspark.admin.service.SysResourceService;
import com.isspark.admin.service.SysRoleService;
import com.isspark.admin.service.SysUserService;
import com.isspark.admin.service.UserService;
import com.isspark.admin.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @version V1.0
 * @class: MyRealm
 * @description:
 * @author: xuzheng
 * @create: 2020-05-18 09:56
 **/
@Slf4j
@Component
public class MyRealm extends AuthorizingRealm{


    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysResourceService resourceService;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 授权方法
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("开始授权认证...");
        if(ObjectUtils.isEmpty(principals) || StringUtils.isBlank(principals.toString())){
            throw new AuthorizationException("权限认证失败！");
        }
        String username = JWTUtil.getUsername(principals.toString());
        log.debug("身份认证用户{}的token:{}",username,principals.toString());
        //获取用户角色
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name",username);
        SysUser user = userService.getOne(wrapper);
        if(ObjectUtils.isEmpty(user)){
            throw new AuthorizationException("权限认证失败！");
        }
        List<SysRole> roles = roleService.getRolesByUserName(username);
        if(CollectionUtils.isEmpty(roles)){
            throw new AuthorizationException("权限认证失败！");
        }

        List<SysResource> resources = resourceService.getResourceByUserName(username);
        if(CollectionUtils.isEmpty(resources)){
            throw new AuthorizationException("权限认证失败！");
        }

        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles.stream().map(SysRole::getValue).collect(Collectors.toSet()));
        simpleAuthorizationInfo.addStringPermissions(resources.stream().map(SysResource::getValue).collect(Collectors.toSet()));
        return simpleAuthorizationInfo;
    }

    /**
     * 认证方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.debug("开始身份认证...");
        Object jwtToken = token.getCredentials();
        if(ObjectUtils.isEmpty(jwtToken) || StringUtils.isBlank(jwtToken.toString())){
            throw new AuthenticationException("token未识别，请重新登录！");
        }
        String username = JWTUtil.getUsername(String.valueOf(jwtToken));
        log.debug("身份认证用户{}的token:{}",username,jwtToken);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",username);
        SysUser user = userService.getOne(queryWrapper);
        if(user == null){
            throw new AuthenticationException("用户不存在！");
        }
        //这里验证authenticationToken和simpleAuthenticationInfo的信息
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(jwtToken, jwtToken, getName());
        return simpleAuthenticationInfo;
    }
}
