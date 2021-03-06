package com.isspark.admin.service;

import com.isspark.admin.domain.entity.SysResource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isspark.admin.domain.vo.request.AddResourceReqVo;
import com.isspark.admin.domain.vo.response.ResourceRespVo;

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

    List<ResourceRespVo> getTreeResources();

    Boolean addOrUpdateResource(AddResourceReqVo vo);

    Boolean delete(Long resourceId);

}
