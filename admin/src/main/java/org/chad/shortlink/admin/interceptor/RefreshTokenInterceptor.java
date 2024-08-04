package org.chad.shortlink.admin.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.chad.shortlink.admin.common.user.UserContext;
import org.chad.shortlink.admin.common.user.UserInfoDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.chad.shortlink.admin.common.constant.RedisCacheConstant.LOGIN_USER_TTL;
import static org.chad.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

public class RefreshTokenInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.尝试取出token
        String token = request.getHeader("authorization");
        //2.判断携带token没
        if(StrUtil.isBlank(token)){
            //3.请求没带token，用户没登陆,交给下一个拦截器拦截
            return true;
        }
        //4.尝试取出redis里面token对应的用户
        String key = USER_LOGIN_KEY + token;
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
        //5.检验token过期没
        if(entries.isEmpty()){
            //6.过期了，放行到下一个拦截
            return true;
        }
        //7.提取用户信息，放入threadlocal
        UserInfoDTO user = BeanUtil.fillBeanWithMap(entries, new UserInfoDTO(), false);
        UserContext.setUser(user);
        //8.没过期，刷新token的TTL
        stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除用户
        UserContext.removeUser();
    }
}