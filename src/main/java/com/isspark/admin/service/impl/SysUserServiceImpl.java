package com.isspark.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isspark.admin.common.exception.BusinessException;
import com.isspark.admin.domain.entity.SysResource;
import com.isspark.admin.domain.entity.SysUser;
import com.isspark.admin.domain.vo.request.AddUserReqVo;
import com.isspark.admin.domain.vo.request.UpdateUserReqVo;
import com.isspark.admin.domain.vo.request.UserPageReqVo;
import com.isspark.admin.domain.vo.response.SysUserRespVo;
import com.isspark.admin.domain.vo.response.UserInfoRespVo;
import com.isspark.admin.domain.vo.response.UserMenu;
import com.isspark.admin.mapper.SysUserMapper;
import com.isspark.admin.service.SysResourceService;
import com.isspark.admin.service.SysUserRoleService;
import com.isspark.admin.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isspark.admin.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final
    SysUserMapper userMapper;

    private final
    SysResourceService resourceService;

    private final
    SysUserRoleService userRoleService;

    @Autowired
    public SysUserServiceImpl(SysUserMapper userMapper, SysResourceService resourceService, SysUserRoleService userRoleService) {
        this.userMapper = userMapper;
        this.resourceService = resourceService;
        this.userRoleService = userRoleService;
    }

    @Override
    public UserInfoRespVo getUserInfo(@NotBlank String username) {
        UserInfoRespVo result = new UserInfoRespVo();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("name", username);
        SysUser user = userMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(user)) {
            try {
                throw new BusinessException("用户信息不存在");
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
        BeanUtils.copyProperties(user, result);
        List<SysResource> resources = resourceService.getResourceByUserName(username);
        if (CollectionUtils.isEmpty(resources)) {
            return result;
        }
        result.setMenus(toTreeMenus(resources));
        return result;
    }

    @Override
    public List<SysUserRespVo> list(String username, String mobile) {
        List<SysUserRespVo> result = new ArrayList<>();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            wrapper.eq("name", username);
        }
        if (StringUtils.isNotBlank(mobile)) {
            wrapper.eq("mobile", mobile);
        }
        List<SysUser> list = this.list(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(tmp -> {
                SysUserRespVo data = new SysUserRespVo();
                BeanUtils.copyProperties(tmp, data);
                result.add(data);
            });
        }
        return result;
    }

    @Override
    public IPage<SysUserRespVo> page(UserPageReqVo vo) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(vo.getUsername())) {
            wrapper.eq("name", vo.getUsername());
        }
        if (StringUtils.isNotBlank(vo.getMobile())) {
            wrapper.eq("mobile", vo.getMobile());
        }
        IPage usersPage = this.page(new Page<>(vo.getCurrentPage(), vo.getPageSize()), wrapper);
        List<SysUser> userList = usersPage.getRecords();
        if (CollectionUtils.isEmpty(userList)) {
            return new Page<>(vo.getCurrentPage(), vo.getPageSize());
        }
        List<SysUserRespVo> result = BeanUtil.copyListProperties(userList, SysUserRespVo::new);
        return usersPage.setRecords(result);
    }

    @Override
    public boolean addUser(AddUserReqVo vo) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("name", vo.getName());
        wrapper.or();
        wrapper.eq("mobile", vo.getMobile());
        SysUser user = this.getOne(wrapper);
        if (ObjectUtils.isNotEmpty(user)) {
            throw new BusinessException("用户已存在！");
        }
        user = new SysUser();
        BeanUtils.copyProperties(vo, user);
        boolean result = this.save(user);
        if (result) {
            userRoleService.updateUserRole(vo.getRoleIds(), user.getId());
        }
        return result;
    }

    @Override
    public Boolean updateUserAndUserRole(UpdateUserReqVo vo) {
        SysUser entity = new SysUser();
        BeanUtil.copyProperties(vo, entity);
        entity.setUpdateTime(new Date());
        Boolean result = this.updateById(entity);
        if (CollectionUtils.isNotEmpty(vo.getRoleIds())) {
            userRoleService.updateUserRole(vo.getRoleIds(), entity.getId());
        }
        return result;
    }

    @Override
    public Boolean deleteUserAndUserRole(@NotNull Integer userId){
        Boolean result = this.removeById(userId);
        userRoleService.deletUserRoleByUserId(userId);
        return result;
    }

    private List<UserMenu> toTreeMenus(List<SysResource> resources) {
        List<UserMenu> parents = new ArrayList<>();
        List<UserMenu> allMenu = new ArrayList<>();
        resources.forEach(res -> {
            UserMenu menu = new UserMenu();
            BeanUtils.copyProperties(res, menu);
            if (ObjectUtils.isEmpty(res.getParentId())) {
                parents.add(menu);
            }
            allMenu.add(menu);
        });
        List<UserMenu> result = parents.stream().sorted(Comparator.comparing(UserMenu::getSort)).collect(Collectors.toList());
        result.forEach(tmp -> getChildrenMenu(tmp, allMenu));
        return result;
    }

    private void getChildrenMenu(UserMenu menu, List<UserMenu> menus) {
        List<UserMenu> childrens = new ArrayList<>();
        menus.forEach(tmp -> {
            if (ObjectUtils.isNotEmpty(tmp.getParentId()) && menu.getId().intValue() == tmp.getParentId()) {
                childrens.add(tmp);
            }
        });
        if (CollectionUtils.isNotEmpty(childrens)) {
            List<UserMenu> sorted = childrens.stream().sorted(Comparator.comparing(UserMenu::getSort)).collect(Collectors.toList());
            menu.setChildren(sorted);
            sorted.forEach(tmp -> getChildrenMenu(tmp, menus));
        }
    }

}
