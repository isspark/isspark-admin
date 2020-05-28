package com.isspark.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.isspark.admin.common.exception.BusinessException;
import com.isspark.admin.domain.entity.SysResource;
import com.isspark.admin.domain.vo.request.AddResourceReqVo;
import com.isspark.admin.domain.vo.response.ResourceRespVo;
import com.isspark.admin.mapper.SysResourceMapper;
import com.isspark.admin.service.SysResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isspark.admin.service.SysRoleResourceService;
import com.isspark.admin.utils.BeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private SysRoleResourceService roleResourceService;

    @Override
    public List<SysResource> getResourceByUserName(String username) {
        return resourceMapper.getResourcesByUserName(username);
    }

    @Override
    public List<ResourceRespVo> getTreeResources() {
        List<SysResource> resources = this.list();
        if (CollectionUtils.isEmpty(resources)) {
            return null;
        }
        return toTreeMenus(resources);
    }

    @Override
    public Boolean addOrUpdateResource(AddResourceReqVo vo) {
        SysResource resource = new SysResource();
        BeanUtil.copyProperties(vo,resource);
        if(ObjectUtils.isEmpty(vo.getId())){
            QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
            wrapper.eq("value",vo.getValue());
            SysResource res = this.getOne(wrapper);
            if(ObjectUtils.isNotEmpty(res)){
                throw new BusinessException("已存在的资源编码!");
            }
            return this.save(resource);
        }
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        wrapper.eq("value",vo.getValue());
        wrapper.ne("id",vo.getId());
        SysResource res = this.getOne(wrapper);
        if(ObjectUtils.isNotEmpty(res)){
            throw new BusinessException("已存在的资源编码!");
        }
        return this.updateById(resource);
    }

    @Override
    public Boolean delete(Long resourceId) {
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",resourceId);
        List<SysResource> resource = this.list(wrapper);
        if(CollectionUtils.isNotEmpty(resource)){
            throw new BusinessException("该资源节点存在子节点，请先删除子节点!");
        }
        Boolean result = this.removeById(resourceId);
        roleResourceService.deleteRoleResourceByResourceId(resourceId);
        return result;
    }

    protected List<ResourceRespVo> toTreeMenus(List<SysResource> resources) {
        List<ResourceRespVo> parents = new ArrayList<>();
        List<ResourceRespVo> allMenu = new ArrayList<>();
        resources.forEach(res -> {
            ResourceRespVo menu = new ResourceRespVo();
            BeanUtils.copyProperties(res, menu);
            if (ObjectUtils.isEmpty(res.getParentId())) {
                parents.add(menu);
            }
            allMenu.add(menu);
        });
        List<ResourceRespVo> result = parents.stream().sorted(Comparator.comparing(ResourceRespVo::getSort)).collect(Collectors.toList());
        result.forEach(tmp -> {
            getChildrenMenu(tmp, allMenu);
        });
        return result;
    }

    protected void getChildrenMenu(ResourceRespVo menu, List<ResourceRespVo> menus) {
        List<ResourceRespVo> childrens = new ArrayList<>();
        menus.forEach(tmp -> {
            if (ObjectUtils.isNotEmpty(tmp.getParentId()) && menu.getId().intValue() == tmp.getParentId()) {
                childrens.add(tmp);
            }
        });
        if (CollectionUtils.isNotEmpty(childrens)) {
            List<ResourceRespVo> sorted = childrens.stream().sorted(Comparator.comparing(ResourceRespVo::getSort)).collect(Collectors.toList());
            menu.setChildren(sorted);
            sorted.forEach(tmp -> {
                getChildrenMenu(tmp, menus);
            });
        }
        return;
    }
}
