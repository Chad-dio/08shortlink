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
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static org.chad.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final RBloomFilter<String> bloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;
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
        return Result.success(!bloomFilter.contains(username));
    }

    @Override
    public Result Register(UserRegisterDTO registerDTO) {
        if(bloomFilter.contains(registerDTO.getUsername())){
            return Result.error("用户名已经存在");
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + registerDTO.getUsername());
        try {
            if (lock.tryLock()) {
                try {
                    int inserted = baseMapper.insert(BeanUtil.copyProperties(registerDTO, User.class));
                    if (inserted < 1) {
                        return Result.error("用户创建失败");
                    }
                } catch (DuplicateKeyException ex) {
                    return Result.error("用户已经存在");
                }
                bloomFilter.add(registerDTO.getUsername());
                return Result.success("创建成功");
            }
        } finally {
            lock.unlock();
        }
        return Result.error("用户名已经存在");
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
