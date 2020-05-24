package com.isspark.admin.mapper;

import com.isspark.admin.domain.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> getRolesByUserName(String username);
}
