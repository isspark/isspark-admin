package com.isspark.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.isspark.admin.common.exception.BusinessException;
import com.isspark.admin.domain.entity.SysResource;
import com.isspark.admin.domain.entity.SysRole;
import com.isspark.admin.domain.entity.SysUser;
import com.isspark.admin.domain.entity.SysUserRole;
import com.isspark.admin.domain.vo.response.SysUserRespVo;
import com.isspark.admin.domain.vo.response.UserInfoRespVo;
import com.isspark.admin.domain.vo.response.UserMenu;
import com.isspark.admin.mapper.SysUserMapper;
import com.isspark.admin.service.SysResourceService;
import com.isspark.admin.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Autowired
    SysUserMapper userMapper;

    @Autowired
    SysResourceService resourceService;

    @Override
    public UserInfoRespVo getUserInfo(@NotBlank String username) {
        UserInfoRespVo result = new UserInfoRespVo();
        QueryWrapper wrapper = new QueryWrapper();
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
        QueryWrapper wrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(username)){
            wrapper.eq("name",username);
        }
        if(StringUtils.isNotBlank(mobile)){
            wrapper.eq("mobile",mobile);
        }
        List<SysUser> list = this.list(wrapper);
        if(CollectionUtils.isNotEmpty(list)){
            list.forEach( tmp -> {
                SysUserRespVo data = new SysUserRespVo();
                BeanUtils.copyProperties(tmp,data);
                result.add(data);
            });
        }
        return result;
    }

    protected List<UserMenu> toTreeMenus(List<SysResource> resources) {
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
        result.forEach(tmp -> {
            getChildrenMenu(tmp, allMenu);
        });
        return result;
    }

    protected void getChildrenMenu(UserMenu menu, List<UserMenu> menus) {
        List<UserMenu> childrens = new ArrayList<>();
        menus.forEach(tmp -> {
            if (ObjectUtils.isNotEmpty(tmp.getParentId()) && menu.getId().intValue() == tmp.getParentId()) {
                childrens.add(tmp);
            }
        });
        if (CollectionUtils.isNotEmpty(childrens)) {
            List<UserMenu> sorted = childrens.stream().sorted(Comparator.comparing(UserMenu::getSort)).collect(Collectors.toList());
            menu.setChildren(sorted);
            sorted.forEach(tmp -> {
                getChildrenMenu(tmp, menus);
            });
        }
        return;
    }

}
