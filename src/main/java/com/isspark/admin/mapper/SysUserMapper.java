package com.isspark.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isspark.admin.domain.entity.SysRole;
import com.isspark.admin.domain.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isspark.admin.domain.vo.request.UserPageReqVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage<SysUser> page(Page<SysUser> page, UserPageReqVo vo);
}
