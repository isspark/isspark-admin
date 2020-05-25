package com.isspark.admin.service.impl;

import com.isspark.admin.domain.entity.SysResource;
import com.isspark.admin.mapper.SysResourceMapper;
import com.isspark.admin.service.SysResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService {

    @Autowired
    private SysResourceMapper resourceMapper;

    @Override
    public List<SysResource> getResourceByUserName(String username) {
        return resourceMapper.getResourcesByUserName(username);
    }
}
