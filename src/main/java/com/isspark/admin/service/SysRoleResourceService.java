package com.isspark.admin.service;

import com.isspark.admin.domain.entity.SysRoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 角色资源表 服务类
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
public interface SysRoleResourceService extends IService<SysRoleResource> {

    Boolean deleteRoleResourceByRoleId(Long roleId);

    Boolean deleteRoleResourceByResourceId(Long resourceId);

    Boolean addRoleResourcByRoleId(List<Integer> resourceId, Long roleId);

    List<Integer> getResourceIdsByRoleId(@NotNull Long roleId);
}
