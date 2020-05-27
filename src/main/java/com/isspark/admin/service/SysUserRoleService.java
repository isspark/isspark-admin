package com.isspark.admin.service;

import com.isspark.admin.domain.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    Boolean updateUserRole(List<Integer> roleIds, @NotNull Long userId);

    Boolean deletUserRoleByUserId(Integer userId);

    List<Integer> getRoleIdsByUserId(Integer userId);

    Boolean deletUserRoleByRoleId(Integer roleId);
}
