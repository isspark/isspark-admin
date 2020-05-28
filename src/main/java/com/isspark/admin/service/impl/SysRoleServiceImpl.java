package com.isspark.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.isspark.admin.common.exception.BusinessException;
import com.isspark.admin.domain.entity.SysRole;
import com.isspark.admin.domain.vo.request.AddRoleReqVo;
import com.isspark.admin.domain.vo.response.TreeRoleRespVo;
import com.isspark.admin.mapper.SysRoleMapper;
import com.isspark.admin.service.SysRoleResourceService;
import com.isspark.admin.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isspark.admin.service.SysUserRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private SysRoleResourceService roleResourceService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Override
    public List<SysRole> getRolesByUserName(String username) {
        return roleMapper.getRolesByUserName(username);
    }

    @Override
    public Boolean addRole(AddRoleReqVo vo) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name", vo.getName());
        wrapper.or();
        wrapper.eq("value", vo.getValue());
        SysRole role = this.getOne(wrapper);
        if (ObjectUtils.isNotEmpty(role)) {
            throw new BusinessException("该角色已存在！");
        }
        role = new SysRole();
        BeanUtils.copyProperties(vo, role);
        Boolean result = this.save(role);
        if (CollectionUtils.isNotEmpty(vo.getResourceIds())) {
            roleResourceService.addRoleResourcByRoleId(vo.getResourceIds(), role.getId());
        }
        return result;
    }

    @Override
    public Boolean update(AddRoleReqVo vo) {
        if (ObjectUtils.isEmpty(vo)) {
            throw new BusinessException("更新角色失败，缺少角色ID！");
        }
        SysRole role = new SysRole();
        BeanUtils.copyProperties(vo, role);
        Boolean result = this.updateById(role);
        if (CollectionUtils.isNotEmpty(vo.getResourceIds())) {
            roleResourceService.deleteRoleResourceByRoleId(vo.getId());
            roleResourceService.addRoleResourcByRoleId(vo.getResourceIds(), vo.getId());
        }
        return result;
    }

    @Override
    public Boolean addOrUpdate(@NotNull AddRoleReqVo vo) {
        if (ObjectUtils.isNotEmpty(vo.getId())) {
            return this.update(vo);
        } else {
            return this.addRole(vo);
        }
    }

    @Override
    public Boolean deleteRoleAndResource(Long roleId){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",roleId);
        List<SysRole> children = this.list(wrapper);
        if(CollectionUtils.isNotEmpty(children)){
            throw new BusinessException("删除失败：存在子角色，请先删除子节点下的所有角色！");
        }
        Boolean result = this.removeById(roleId);
        roleResourceService.deleteRoleResourceByRoleId(roleId);
        userRoleService.deletUserRoleByRoleId(roleId.intValue());
        return result;
    }

    @Override
    public List<TreeRoleRespVo> getTreeRoles() {
        List<SysRole> roles = this.list();
        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }
        return toTreeMenus(roles);
    }

    protected List<TreeRoleRespVo> toTreeMenus(List<SysRole> resources) {
        List<TreeRoleRespVo> parents = new ArrayList<>();
        List<TreeRoleRespVo> allMenu = new ArrayList<>();
        resources.forEach(res -> {
            TreeRoleRespVo menu = new TreeRoleRespVo();
            BeanUtils.copyProperties(res, menu);
            if (ObjectUtils.isEmpty(res.getParentId())) {
                parents.add(menu);
            }
            allMenu.add(menu);
        });
        List<TreeRoleRespVo> result = parents.stream().sorted(Comparator.comparing(TreeRoleRespVo::getSort)).collect(Collectors.toList());
        result.forEach(tmp -> {
            getChildrenMenu(tmp, allMenu);
        });
        return result;
    }

    protected void getChildrenMenu(TreeRoleRespVo menu, List<TreeRoleRespVo> menus) {
        List<TreeRoleRespVo> childrens = new ArrayList<>();
        menus.forEach(tmp -> {
            if (ObjectUtils.isNotEmpty(tmp.getParentId()) && menu.getId().intValue() == tmp.getParentId()) {
                childrens.add(tmp);
            }
        });
        if (CollectionUtils.isNotEmpty(childrens)) {
            List<TreeRoleRespVo> sorted = childrens.stream().sorted(Comparator.comparing(TreeRoleRespVo::getSort)).collect(Collectors.toList());
            menu.setChildren(sorted);
            sorted.forEach(tmp -> {
                getChildrenMenu(tmp, menus);
            });
        }
        return;
    }
}
