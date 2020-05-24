package com.isspark.admin.mapper;

import com.isspark.admin.domain.entity.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
public interface SysResourceMapper extends BaseMapper<SysResource> {

    List<SysResource> getResourcesByUserName(String username);
}
