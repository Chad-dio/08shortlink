package org.chad.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.chad.shortlink.admin.domain.dto.UserRegisterDTO;
import org.chad.shortlink.admin.domain.entity.Result;
import org.chad.shortlink.admin.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/v1/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public Result getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @GetMapping("/actual/{username}")
    public Result getActualUserByUsername(@PathVariable String username){
        return userService.getActualUserByUsername(username);
    }

    @GetMapping("/has-username")
    public Result hasUsername(@RequestParam String username){
        return userService.hasUsername(username);
    }

    @PostMapping
    public Result<Void> register(@RequestBody UserRegisterDTO userRegisterReqDTO){
        userService.Register(userRegisterReqDTO);
        return Result.success();
    }

//    @PutMapping("/api/short-link/admin/v1/user")
//    public Result<Void> update(@RequestBody UserRegisterReqDTO userRegisterReqDTO){
//        userService.Update(userRegisterReqDTO);
//        return Result.success();
//    }
//
//    @PostMapping("/api/short-link/admin/v1/user/login")
//    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO userLoginReqDTO){
//        UserLoginRespDTO userLoginRespDTO = userService.Login(userLoginReqDTO);
//        return Result.success(userLoginRespDTO);
//    }
//
//    @GetMapping("/api/short-link/admin/v1/user/check-login")
//    public Result check_login(@RequestParam String username, @RequestParam String token){
//        return userService.check_login(username, token);
//    }
//
//    @DeleteMapping("/api/short-link/admin/v1/user/logout")
//    public Result logout(@RequestParam String username,@RequestParam String token){
//        return userService.logout(username, token);
//    }
}

