package com.skillplay.controller;

import com.skillplay.dto.UserRequestDto;
import com.skillplay.security.JWTHelper;
import com.skillplay.security.UserDetailService;
import com.skillplay.service.AuthService;
import com.skillplay.utils.Constants;
import com.skillplay.utils.GlobalResponse;
import com.skillplay.utils.AppConstants;
import com.skillplay.utils.OtpRetryMechanism;
import com.skillplay.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/auth/")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserValidator userValidator;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;
    private final JWTHelper jwtHelper;


    @GetMapping("test")
    private GlobalResponse saveUser(){
        log.info("test method called");
        return GlobalResponse.builder().msg("test success..").status(AppConstants.SUCCESS).build();
    }


    @PostMapping("/sign-in")
    private ResponseEntity<GlobalResponse> signInUser(@RequestParam("email") String email, @RequestParam("password") String password){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (LockedException e) {
            return ResponseEntity.ok(GlobalResponse.builder().msg("Account is locked").status(AppConstants.ACCOUNT_LOCKED).build());
        } catch (DisabledException e) {
            return ResponseEntity.ok(GlobalResponse.builder().msg("Account is disabled").status(AppConstants.ACCOUNT_DISABLED).build());
        } catch (CredentialsExpiredException e) {
            return ResponseEntity.ok(GlobalResponse.builder().msg("Credentials have expired").status(AppConstants.CREDENTIALS_EXPIRED).build());
        } catch (AccountExpiredException e) {
            return ResponseEntity.ok(GlobalResponse.builder().msg("Account has expired").status(AppConstants.ACCOUNT_EXPIRED).build());
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(GlobalResponse.builder().msg("Invalid email or password").status(AppConstants.INVALID_CREDENTIALS).build());
        }


        final UserDetails userDetails = userDetailService.loadUserByUsername(email);
        String token = jwtHelper.generateToken(userDetails);

        GlobalResponse response = authService.signInUser(email, password, token);
        return ResponseEntity.ok(response);
    }


    @PostMapping({"sign-up","resend-otp"})
    private ResponseEntity<GlobalResponse> createUser(@RequestBody UserRequestDto userRequestDto ){

        if(!userValidator.isValidUsername(userRequestDto.getUsername()))
            return ResponseEntity.ok(GlobalResponse.builder().msg("Invalid Username, Username should only contain A-Z a-z _ and .").status(AppConstants.INVALID_USERNAME).build());

        if(!userValidator.isValidEmail(userRequestDto.getEmail()))
            ResponseEntity.ok(GlobalResponse.builder().msg("Invalid Email Address").status(AppConstants.INVALID_EMAIL).build());

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
