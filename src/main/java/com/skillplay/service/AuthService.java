package com.skillplay.service;

import com.skillplay.dto.UserRequestDto;
import com.skillplay.dto.UserResponseDto;
import com.skillplay.entity.user.Tokens;
import com.skillplay.entity.user.User;
import com.skillplay.repository.TokensRepository;
import com.skillplay.repository.UserRepository;
import com.skillplay.service.mail.MailTemplates;
import com.skillplay.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final KeyAndOtpUtils keyAndOtpUtils;
    private final MailTemplates mailTemplates;
    private final TokensRepository tokensRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public GlobalResponse signInUser(String email, String password) {
        try {
            User user = userRepository.findByEmail(email);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                Optional<Tokens> tokensOptional = tokensRepository.findByUserID(user);

                UserResponseDto response = modelMapper.map(user, UserResponseDto.class);
                if (tokensOptional.isPresent()) {
                    Tokens tokens = tokensOptional.get();
                    response.setTokens(tokens.getToken());
                    response.setMiniTokens(tokens.getMiniToken());
                }

                return GlobalResponse.builder()
                        .msg("Welcome to Skillplay " + user.getUsername())
                        .status(AppConstants.SUCCESS)
                        .data(response)
                        .build();
            } else {
                return GlobalResponse.builder()
                        .msg("Invalid Email or Password! Try again later")
                        .status(AppConstants.INVALID_PASSWORD)
                        .build();
            }
        } catch (Exception e) {
            log.error("error occurred while signing in user :{}", e.getMessage());
            return GlobalResponse.builder()
                    .msg("Error While Fetching User information, Try again later")
                    .err(e.getMessage())
                    .status(AppConstants.ERROR)
                    .build();
        }
    }

    public GlobalResponse createUser(UserRequestDto userRequestDto){
        String otp = keyAndOtpUtils.generateOtp(4);
        log.info("generated OTP:{}",otp);
        Map<String, UserRequestDto> otpUserMap = new HashMap<>();
        otpUserMap.put(otp, userRequestDto);

        try {
            String uniqueKey = keyAndOtpUtils.generateUniqueKey();
            GlobalStorage.userMap.put(uniqueKey, otpUserMap);
            boolean sent = mailTemplates.sendOtpForAccountCreation(otp, userRequestDto.getEmail());
            if(sent){

                Map<String, Object> response = new HashMap<>();
                response.put("clientID", uniqueKey);
                response.put("user", userRequestDto);

                OtpRetryMechanism.incrementRetryCount(userRequestDto.getEmail(), AppConstants.PURPOSE_SIGN_UP_OTP);

                return GlobalResponse.builder()
                        .msg("OTP sent to , "+userRequestDto.getEmail()+", Otp is valid for 5 minutes")
                        .data(response)
                        .status(AppConstants.SUCCESS)
                        .build();
            }else {
                return GlobalResponse.builder()
                        .msg("Failed to send OTP, Something went wrong")
                        .status(AppConstants.FAILED)
                        .build();
            }
        }catch (Exception ex){
            log.error("Error while generating unique key or sending OTP: {}", ex.getMessage(), ex);
        }
        return GlobalResponse.builder()
                .msg("Oops! Something went wrong! Try again later")
                .status(AppConstants.ERROR)
                .build();
    }

    public GlobalResponse verifyAndSaveUser(String clientID, String otp) {

        if(!GlobalStorage.userMap.containsKey(clientID)){
            return GlobalResponse.builder()
                    .msg("Invalid clientID! Try sending OTP again")
                    .status(AppConstants.INVALID_CLIENT_ID)
                    .build();
        }

        Map<String, UserRequestDto> userData = GlobalStorage.userMap.get(clientID);
        String otpFromMap = userData.keySet().iterator().next();

        if(!keyAndOtpUtils.isKeyValid(clientID)){
            return GlobalResponse.builder()
                    .msg("OTP Expired! Try again")
                    .status(AppConstants.OTP_EXPIRED)
                    .build();
        }

        if(otp.equals(otpFromMap)){

            User user = modelMapper.map(userData.get(otpFromMap), User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            log.info("user : -> "+user);

            User saved = userRepository.save(user);
            GlobalStorage.userMap.remove(clientID);

            OtpRetryMechanism.clearRetryCount(saved.getEmail(), AppConstants.PURPOSE_SIGN_UP_OTP);

            return GlobalResponse.builder()
                .msg("User created successfully!")
                .data(modelMapper.map(saved, UserResponseDto.class))
                .status(AppConstants.SUCCESS)
                .build();
        }else{
            return GlobalResponse.builder()
                    .msg("Invalid OTP!")
                    .status(AppConstants.INVALID_OTP)
                    .build();
        }

    }


    public GlobalResponse handleResetPasswordRequest(String email){

        try {
            String uniqueKey = keyAndOtpUtils.generateUniqueKey();
            String otp = keyAndOtpUtils.generateOtp(4);

            Map<String,String> emailmap = new HashMap<>();
            emailmap.put(email, otp);
            GlobalStorage.otpMap.put(uniqueKey, emailmap);

            if(mailTemplates.sendForgotPasswordOtpMail(otp, email)){

                Map<String, Object> response = new HashMap<>();
                response.put("clientID", uniqueKey);

                OtpRetryMechanism.incrementRetryCount(email, AppConstants.PURPOSE_FORGOT_PASSWORD_OTP);

                return GlobalResponse.builder()
                        .msg("Otp sent to "+ email +", Otp is valid for 5 minutes")
                        .data(response)
                        .status(AppConstants.SUCCESS)
                        .build();
            }else{
                return GlobalResponse.builder()
                        .msg("Failed to send the reset password mail, Try again after sometime")
                        .status(AppConstants.FAILED)
                        .build();
            }

        }catch(Exception e){
            log.error("ERROR OCCURED WHILE SENDING RESET PASSWORD MAIL :{}",e);
                    return GlobalResponse.builder()
                            .msg("Something went wrong! Try again later")
                            .status(AppConstants.ERROR)
                            .build();
        }
    }

    public GlobalResponse verifyOtpAndAllowUserToResetPassword(String clientID, String otp) {

        if (!GlobalStorage.otpMap.containsKey(clientID)) {
            return GlobalResponse.builder()
                    .msg("Invalid clientID! Try sending OTP again")
                    .status(AppConstants.INVALID_CLIENT_ID)
                    .build();
        }

        if (!keyAndOtpUtils.isKeyValid(clientID)) {
            return GlobalResponse.builder()
                    .msg("OTP Expired! Try again")
                    .status(AppConstants.OTP_EXPIRED)
                    .build();
        }

        Map<String, String> otpMap = GlobalStorage.otpMap.get(clientID);

        String storedOtp = otpMap.values().iterator().next();
        String email = otpMap.keySet().iterator().next();

        if (otp.equals(storedOtp)) {
            try {
                String newClientID = keyAndOtpUtils.generateUniqueKey();

                Map<String, String> newOtpMap = new HashMap<>();
                newOtpMap.put("email", email);

                GlobalStorage.otpMap.put(newClientID, newOtpMap);
                GlobalStorage.otpMap.remove(clientID);

                Map<String, Object> response = new HashMap<>();
                response.put("clientID", newClientID);

                // Return a success response with the new client ID
                return GlobalResponse.builder()
                        .msg("Otp Verified! you can reset password now")
                        .status(AppConstants.SUCCESS)
                        .data(response)
                        .build();

            } catch (Exception e) {
                log.error("ERROR OCCURRED WHILE GENERATING NEW CLIENT ID FOR RESET PASSWORD: {}", e);

                return GlobalResponse.builder()
                        .msg("Something went wrong! Try again later")
                        .status(AppConstants.ERROR)
                        .build();
            }
        } else {
            return GlobalResponse.builder()
                    .msg("Invalid OTP!")
                    .status(AppConstants.INVALID_OTP)
                    .build();
        }
    }


    public GlobalResponse saveNewPassword(String clientID, String password){

        if(!GlobalStorage.otpMap.containsKey(clientID)){

            return GlobalResponse.builder()
                    .msg("Invalid clientID! Try sending OTP again")
                    .status(AppConstants.INVALID_CLIENT_ID)
                    .build();
        }

        if(!keyAndOtpUtils.isKeyValid(clientID)){
            return GlobalResponse.builder()
                    .msg("OTP Expired! Try again")
                    .status(AppConstants.OTP_EXPIRED)
                    .build();
        }

        String email = GlobalStorage.otpMap.get(clientID).get("email");

        try {

            User userByEmail = userRepository.findByEmail(email);
            userByEmail.setPassword(password);
            userByEmail.setModified(new Date());
            userRepository.save(userByEmail);

            OtpRetryMechanism.clearRetryCount(email, AppConstants.PURPOSE_FORGOT_PASSWORD_OTP);

            GlobalStorage.otpMap.remove(clientID);
            return GlobalResponse.builder()
                    .msg("Password Changed")
                    .status(AppConstants.SUCCESS)
                    .build();

        }catch (Exception e){

            log.error("ERROR OCCURED WHILE SAVING USER  :{}",e);
            return GlobalResponse.builder()
                    .msg("Something went wrong! Try again later")
                    .status(AppConstants.ERROR)
                    .build();
        }

    }


}
