package com.skillplay.controller;


import com.skillplay.entity.user.User;
import com.skillplay.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("test")
    private User saveUser(@RequestBody User user){

        return authService.saveUser(user);
    }

}
