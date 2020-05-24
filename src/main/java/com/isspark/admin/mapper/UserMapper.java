package com.isspark.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isspark.admin.domain.entity.User;
import com.isspark.admin.domain.vo.response.UserRespVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isspark.admin.domain.vo.request.UserReqVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xuzheng
 * @since 2020-04-09
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    IPage<UserRespVO> find(Page<UserRespVO> page, @Param("reqVO") UserReqVO reqVO);
}
