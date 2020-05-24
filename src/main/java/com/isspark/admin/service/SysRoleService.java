package com.isspark.admin.service;

import com.isspark.admin.domain.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getRolesByUserName(String username);
}
