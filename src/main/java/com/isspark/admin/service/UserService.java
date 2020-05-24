package com.isspark.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isspark.admin.domain.entity.User;
import com.isspark.admin.domain.vo.response.UserRespVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isspark.admin.domain.vo.request.UserReqVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzheng
 * @since 2020-04-09
 */
public interface UserService extends IService<User> {

    List<UserRespVO> findByAge(Integer age);

    IPage<UserRespVO> find(UserReqVO reqVO);

    List<UserRespVO> getAllUsers();
}
