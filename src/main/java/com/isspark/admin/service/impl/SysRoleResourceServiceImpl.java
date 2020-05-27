package com.isspark.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.isspark.admin.common.exception.BusinessException;
import com.isspark.admin.domain.entity.SysRoleResource;
import com.isspark.admin.mapper.SysRoleResourceMapper;
import com.isspark.admin.service.SysRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色资源表 服务实现类
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@Service
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource> implements SysRoleResourceService {

    @Autowired
    SysRoleResourceService roleResourceService;

    public Boolean deleteRoleResourceByRoleId(@NotBlank Long roleId){
        QueryWrapper<SysRoleResource> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        return this.remove(wrapper);
    }

    public Boolean addRoleResourcByRoleId(List<Integer> resourceId,Long roleId){
        if(CollectionUtils.isEmpty(resourceId) || ObjectUtils.isEmpty(roleId)){
            throw new BusinessException("插入角色资源失败，缺少必要的参数！");
        }
        List<SysRoleResource> datas = new ArrayList<>();
        resourceId.forEach( tmp -> datas.add(new SysRoleResource(roleId.intValue(),tmp)));
        return this.saveBatch(datas);
    }
}
