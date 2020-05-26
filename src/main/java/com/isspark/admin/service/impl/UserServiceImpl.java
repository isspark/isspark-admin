package com.isspark.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isspark.admin.domain.entity.User;
import com.isspark.admin.domain.vo.response.UserRespVO;
import com.isspark.admin.mapper.UserMapper;
import com.isspark.admin.service.UserService;
import com.isspark.admin.domain.vo.request.UserReqVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzheng
 * @since 2020-04-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserRespVO> findByAge(Integer age) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("age",age);
        return converterTOUserVo(userMapper.selectList(wrapper));
    }

    public List<UserRespVO> getAllUsers(){
        return converterTOUserVo(list());
    }

    private List<UserRespVO> converterTOUserVo(List<User> users){
        List<UserRespVO> result = new ArrayList<>();
        users.forEach(tmp -> {
            UserRespVO data = new UserRespVO();
            BeanUtils.copyProperties(tmp,data);
            result.add(data);
        });
        return result;
    }

    @Override
    public IPage<UserRespVO> find(UserReqVO reqVO) {
        return userMapper.find(new Page<>(reqVO.getCurrentPage(),reqVO.getPageSize()),reqVO);
    }
}
