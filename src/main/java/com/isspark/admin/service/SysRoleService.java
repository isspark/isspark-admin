package com.isspark.admin.service;

import com.isspark.admin.domain.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isspark.admin.domain.vo.request.AddRoleReqVo;
import com.isspark.admin.domain.vo.response.TreeRoleRespVo;

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

    Boolean addRole(AddRoleReqVo vo);

    List<TreeRoleRespVo> getTreeRoles();

    Boolean update(AddRoleReqVo vo);

    Boolean addOrUpdate(AddRoleReqVo vo);

    Boolean deleteRoleAndResource(Long roleId);
}
