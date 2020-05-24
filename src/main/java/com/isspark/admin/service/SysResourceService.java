package com.isspark.admin.service;

import com.isspark.admin.domain.entity.SysResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
public interface SysResourceService extends IService<SysResource> {

    List<SysResource> getResourceByUserName(String username);

}
