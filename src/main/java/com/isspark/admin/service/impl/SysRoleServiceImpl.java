package com.isspark.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.isspark.admin.common.exception.BusinessException;
import com.isspark.admin.domain.entity.SysRole;
import com.isspark.admin.domain.vo.request.AddRoleReqVo;
import com.isspark.admin.mapper.SysRoleMapper;
import com.isspark.admin.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
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

    @Override
    public List<SysRole> getRolesByUserName(String username){
        return roleMapper.getRolesByUserName(username);
    }

    @Override
    public Boolean addRole(AddRoleReqVo vo){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name",vo.getName());
        wrapper.or();
        wrapper.eq("value",vo.getValue());
        SysRole role = this.getOne(wrapper);
        if(ObjectUtils.isNotEmpty(role)){
            throw new BusinessException("该角色已存在！");
        }
        role = new SysRole();
        BeanUtils.copyProperties(vo,role);
        Boolean result = this.save(role);
        return result;
    }
}
