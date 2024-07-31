package org.chad.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.chad.shortlink.admin.domain.dto.UserLoginDTO;
import org.chad.shortlink.admin.domain.dto.UserRegisterDTO;
import org.chad.shortlink.admin.domain.entity.Result;
import org.chad.shortlink.admin.domain.po.User;
import org.chad.shortlink.admin.domain.vo.ActualUserVO;
import org.chad.shortlink.admin.domain.vo.UserVO;
import org.chad.shortlink.admin.mapper.UserMapper;
import org.chad.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final RBloomFilter<String> bloomFilter;
    @Override
    public Result getUserByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getUsername, username);
        User user = getOne(wrapper);
        if(BeanUtil.isEmpty(user)){
            return Result.error("用户不存在");
        }
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        return Result.success(userVO);
    }

    @Override
    public Result hasUsername(String username) {
        return Result.success(bloomFilter.contains(username));
    }

    @Override
    public Result Register(UserRegisterDTO userRegisterReqDTO) {
        return null;
    }

    @Override
    public Result Update(UserRegisterDTO userRegisterReqDTO) {
        return null;
    }

    @Override
    public Result Login(UserLoginDTO userLoginReqDTO) {
        return null;
    }

    @Override
    public Result check_login(String username, String token) {
        return null;
    }

    @Override
    public Result logout(String username, String token) {
        return null;
    }

    @Override
    public Result getActualUserByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getUsername, username);
        User user = getOne(wrapper);
        if(BeanUtil.isEmpty(user)){
            return Result.error("用户不存在");
        }
        ActualUserVO actualUserVO = BeanUtil.copyProperties(user, ActualUserVO.class);
        return Result.success(actualUserVO);
    }
}
