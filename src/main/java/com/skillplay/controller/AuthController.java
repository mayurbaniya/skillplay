package com.skillplay.controller;


import com.skillplay.dto.UserRequestDto;
import com.skillplay.service.AuthService;
import com.skillplay.utils.Constants;
import com.skillplay.utils.GlobalResponse;
import com.skillplay.utils.AppConstants;
import com.skillplay.utils.OtpRetryMechanism;
import com.skillplay.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/auth/")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserValidator userValidator;


    @GetMapping("test")
    private GlobalResponse saveUser(){
        log.info("test method called");
        return GlobalResponse.builder().msg("test success..").status(AppConstants.SUCCESS).build();
    }

    @PostMapping({"sign-up","resend-otp"})
    private ResponseEntity<GlobalResponse> createUser(@RequestBody UserRequestDto userRequestDto ){

        if(!OtpRetryMechanism.isRetryAvailable(userRequestDto.getEmail(), AppConstants.PURPOSE_SIGN_UP_OTP))
            return ResponseEntity.ok(GlobalResponse.builder().msg(Constants.otpLimitReachedWarning).status(AppConstants.LIMIT_EXCEEDED).build());

        if(userValidator.checkUserExistByEmail(userRequestDto.getEmail()))
            return ResponseEntity.ok(GlobalResponse.builder().msg("Email Address is already in use").status(AppConstants.EMAIL_TAKEN).build());

        if(userValidator.checkUserExistByUsername(userRequestDto.getUsername()))
            return ResponseEntity.ok(GlobalResponse.builder().msg("Oops! Username taken").status(AppConstants.USERNAME_TAKEN).build());

        return ResponseEntity.ok(authService.createUser(userRequestDto));
    }

    @PostMapping("verify")
    private ResponseEntity<GlobalResponse> verifyAndSaveUser(@RequestParam("clientID") String clientID, @RequestParam("otp") String otp){
        return ResponseEntity.ok(authService.verifyAndSaveUser(clientID, otp));
    }

    @PostMapping("sign-in")
    private ResponseEntity<GlobalResponse> signInUser(@RequestParam("email") String email, @RequestParam("password") String password){
        return ResponseEntity.ok(authService.signInUser(email, password));
    }

    @PostMapping({"reset-password-request", "resend-password-otp"})
    private ResponseEntity<GlobalResponse> handleResetPasswordRequest(@RequestParam("email") String email){
        if(!OtpRetryMechanism.isRetryAvailable(email, AppConstants.PURPOSE_FORGOT_PASSWORD_OTP))
            return ResponseEntity.ok(GlobalResponse.builder().msg(Constants.otpLimitReachedWarning).status(AppConstants.LIMIT_EXCEEDED).build());

        if(!userValidator.checkUserExistByEmail(email))
            return ResponseEntity.ok(GlobalResponse.builder().msg("Oops! Account not found. Try creating a new account").status(AppConstants.USER_NOT_FOUND).build());

        return ResponseEntity.ok(authService.handleResetPasswordRequest(email));
    }

    @PostMapping("verify-resetPassword-otp")
    private ResponseEntity<GlobalResponse> verifyOtpForResetPassword(@RequestParam("clientID") String clientID, @RequestParam("otp") String otp){
        return ResponseEntity.ok(authService.verifyOtpAndAllowUserToResetPassword(clientID, otp));
    }

    @PostMapping("reset-password")
    private ResponseEntity<GlobalResponse> resetPassword(@RequestParam("clientID") String clientID, @RequestParam("newPassword") String password){
        return ResponseEntity.ok(authService.saveNewPassword(clientID, password));
    }

}
