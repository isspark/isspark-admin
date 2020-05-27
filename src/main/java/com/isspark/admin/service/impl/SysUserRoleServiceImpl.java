package com.isspark.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isspark.admin.common.exception.BusinessException;
import com.isspark.admin.domain.entity.SysUserRole;
import com.isspark.admin.mapper.SysUserRoleMapper;
import com.isspark.admin.service.SysUserRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    SysUserRoleMapper userRoleMapper;

    @Override
    public Boolean updateUserRole(List<Integer> roleIds, @NotNull Long userId){
        if(CollectionUtils.isEmpty(roleIds)){
            throw new BusinessException("角色ID不能为空！");
        }
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        this.remove(wrapper);

        List<SysUserRole> userRoles = new ArrayList<>();
        roleIds.forEach(tmp ->{
            userRoles.add(new SysUserRole(userId.intValue(),tmp));
        });
        return this.saveBatch(userRoles);
    }

    @Override
    public Boolean deletUserRoleByUserId(Integer userId){
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return this.remove(wrapper);
    }

    @Override
    public List<Integer> getRoleIdsByUserId(Integer userId){
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<SysUserRole> userRoles = this.list(wrapper);
        if(CollectionUtils.isEmpty(userRoles)){
            return null;
        }
        List<Integer> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        return roleIds;
    }

}
