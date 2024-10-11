package com.skillplay.controller;


import com.skillplay.dto.UserRequestDto;
import com.skillplay.entity.user.User;
import com.skillplay.service.AuthService;
import com.skillplay.utils.GlobalResponse;
import com.skillplay.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserValidator userValidator;


    @PostMapping("test")
    private User saveUser(@RequestBody User user){
        return authService.saveUser(user);
    }



    @PostMapping("login")
    private ResponseEntity<GlobalResponse> loginUser(
            @RequestBody Map<String, String> map
    ){
        System.err.println("email : " + map.get("email"));
        System.err.println("pass : " + map.get("password"));

        GlobalResponse user = authService.loginUser(map.get("email"), map.get("password"));
        return ResponseEntity.ok(user);
    }

    @PostMapping({"signup","resendOTP"})
    private ResponseEntity<GlobalResponse> createUser( @RequestBody UserRequestDto userRequestDto ){

        if(userValidator.checkUserExistByEmail(userRequestDto.getEmail()))
            return ResponseEntity.ok(GlobalResponse.builder().msg("Email Address is already in use").build());

        if(userValidator.checkUserExistByUsername(userRequestDto.getUsername()))
            return ResponseEntity.ok(GlobalResponse.builder().msg("Oops! Username taken").build());

        return ResponseEntity.ok(authService.createUser(userRequestDto));

    }

    @PostMapping("verify")
    private ResponseEntity<GlobalResponse> verifyAndSaveUser(@RequestParam("clientID") String clientID, @RequestParam("otp") String otp){
        return ResponseEntity.ok(authService.verifyAndSaveUser(clientID, otp));
    }





}
