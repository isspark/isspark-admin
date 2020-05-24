package com.isspark.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.isspark.admin.domain.entity.SysRole;
import com.isspark.admin.domain.entity.SysUser;
import com.isspark.admin.domain.entity.SysUserRole;
import com.isspark.admin.mapper.SysUserMapper;
import com.isspark.admin.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {



}
