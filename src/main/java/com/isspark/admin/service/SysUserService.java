package com.isspark.admin.service;

import com.isspark.admin.common.exception.BusinessException;
import com.isspark.admin.domain.entity.SysRole;
import com.isspark.admin.domain.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isspark.admin.domain.vo.request.AddUserReqVo;
import com.isspark.admin.domain.vo.response.SysUserRespVo;
import com.isspark.admin.domain.vo.response.UserInfoRespVo;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
public interface SysUserService extends IService<SysUser> {

    UserInfoRespVo getUserInfo(String username);

    List<SysUserRespVo> list(String username,String mobile);

    boolean addUser(AddUserReqVo vo);
}
