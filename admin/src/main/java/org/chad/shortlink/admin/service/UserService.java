package org.chad.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.chad.shortlink.admin.domain.dto.UserLoginDTO;
import org.chad.shortlink.admin.domain.dto.UserRegisterDTO;
import org.chad.shortlink.admin.domain.entity.Result;
import org.chad.shortlink.admin.domain.po.User;

public interface UserService extends IService<User> {
    Result getUserByUsername(String username);

    Result hasUsername(String username);

    Result Register(UserRegisterDTO userRegisterReqDTO);

    Result Update(UserRegisterDTO userRegisterReqDTO);

    Result Login(UserLoginDTO userLoginReqDTO);

    Result check_login(String username, String token);

    Result logout(String username, String token);

    Result getActualUserByUsername(String username);
}
