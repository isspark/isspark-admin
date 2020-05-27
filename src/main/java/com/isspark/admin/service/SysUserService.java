package com.isspark.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isspark.admin.common.exception.BusinessException;
import com.isspark.admin.domain.entity.SysRole;
import com.isspark.admin.domain.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isspark.admin.domain.vo.request.AddUserReqVo;
import com.isspark.admin.domain.vo.request.UpdateUserReqVo;
import com.isspark.admin.domain.vo.request.UserPageReqVo;
import com.isspark.admin.domain.vo.response.SysUserRespVo;
import com.isspark.admin.domain.vo.response.UserInfoRespVo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    /**
     *  根据用户名获取用户信息VO
     * @param username 用户名
     * @return
     */
    UserInfoRespVo getUserInfo(String username);

    List<SysUserRespVo> list(String username,String mobile);

    boolean addUser(AddUserReqVo vo);

    IPage<SysUserRespVo> page(UserPageReqVo vo);

    Boolean updateUserAndUserRole(UpdateUserReqVo vo);

    Boolean deleteUserAndUserRole(Integer userId);
}
