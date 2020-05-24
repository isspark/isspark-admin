package com.isspark.admin.service.impl;

import com.isspark.admin.domain.entity.SysRole;
import com.isspark.admin.mapper.SysRoleMapper;
import com.isspark.admin.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    public List<SysRole> getRolesByUserName(String username){
        return roleMapper.getRolesByUserName(username);
    }
}
