package org.chad.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.chad.shortlink.admin.domain.dto.UserLoginDTO;
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
    public Result register(@RequestBody UserRegisterDTO userRegisterReqDTO){
        return userService.Register(userRegisterReqDTO);
    }

    @PutMapping
    public Result update(@RequestBody UserRegisterDTO userRegisterReqDTO){
        return userService.Update(userRegisterReqDTO);
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginReqDTO){
        return userService.Login(userLoginReqDTO);
    }

    @GetMapping("/check-login")
    public Result check_login(@RequestParam String username, @RequestParam String token){
        return userService.check_login(username, token);
    }

    @DeleteMapping("/logout")
    public Result logout(@RequestParam String username,@RequestParam String token){
        return userService.logout(username, token);
    }
}

