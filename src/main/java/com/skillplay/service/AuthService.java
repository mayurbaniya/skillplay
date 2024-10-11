package com.skillplay.service;

import com.skillplay.dto.UserRequestDto;
import com.skillplay.dto.UserResponseDto;
import com.skillplay.entity.user.User;
import com.skillplay.repository.UserRepository;
import com.skillplay.service.mail.MailTemplates;
import com.skillplay.utils.GlobalResponse;
import com.skillplay.utils.GlobalStorage;
import com.skillplay.utils.KeyAndOtpUtils;
import com.skillplay.utils.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final KeyAndOtpUtils keyAndOtpUtils;
    private final MailTemplates mailTemplates;

    public User saveUser(User user){
    	user.setStatus(StatusEnum.ACTIVE.get());
        return userRepository.save(user);

    }


    public GlobalResponse loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if(user.getStatus().equals(StatusEnum.BANNED.get())){
                return GlobalResponse.builder()
                        .msg("You're banned, from this platform ")
                        .status(StatusEnum.BANNED)
                        .build();
            }
            if(user.getStatus().equals(StatusEnum.DELETED.get())){
                return GlobalResponse.builder()
                        .msg("Account Deleted")
                        .status(StatusEnum.DELETED)
                        .build();
            }
            if (user.getPassword().equals(password) && user.getStatus().equals(StatusEnum.ADMIN.get())){

                return GlobalResponse.builder()
                        .msg("ADMIN LOGGED IN " + user.getFirstName())
                        .data(user)
                        .status(StatusEnum.SUCCESS)
                        .build();
            }

            if (user.getPassword().equals(password) && user.getStatus().equals(StatusEnum.ACTIVE.get())) {
                return GlobalResponse.builder()
                        .msg("Welcome back " + user.getFirstName())
                        .data(user)
                        .status(StatusEnum.SUCCESS)
                        .build();
            } else {
                return GlobalResponse.builder()
                        .msg("Wrong Password! please try again or click on 'Forgot Password' ")
                        .status(StatusEnum.INVALID_PASSWORD)
                        .build();
            }
        } else {
            return GlobalResponse.builder()
                    .msg("User not found, please create a new Account")
                    .status(StatusEnum.USER_NOT_FOUND)
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
                return GlobalResponse.builder()
                        .msg("OTP sent to , "+userRequestDto.getEmail())
                        .data(response)
                        .status(StatusEnum.SUCCESS)
                        .build();
            }else {
                return GlobalResponse.builder()
                        .msg("Failed to send OTP, Something went wrong")
                        .status(StatusEnum.FAILED)
                        .build();
            }

        }catch (Exception ex){
            log.error("Error while generating unique key or sending OTP: {}", ex.getMessage(), ex);
        }

        return GlobalResponse.builder()
                .msg("Oops! Something went wrong! Try again later")
                .status(StatusEnum.ERROR)
                .build();
    }


    public GlobalResponse verifyAndSaveUser(String clientID, String otp){


        if(!GlobalStorage.userMap.containsKey(clientID)){
            return GlobalResponse.builder()
                    .msg("Invalid clientID! Try sending OTP again")
                    .status(StatusEnum.INVALID_CLIENT_ID)
                    .build();
        }

        Map<String, UserRequestDto> userData = GlobalStorage.userMap.get(clientID);
        String otpFromMap = userData.keySet().iterator().next();

        if(!keyAndOtpUtils.isKeyValid(clientID)){
            return GlobalResponse.builder()
                    .msg("OTP Expired! Try again")
                    .status(StatusEnum.OTP_EXPIRED)
                    .build();
        }

        if(otp.equals(otpFromMap)){

                    User user = modelMapper.map(userData.get(otpFromMap), User.class);
                    log.info("user : -> "+user);
            User saved = userRepository.save(user);
            GlobalStorage.userMap.remove(clientID);
            return GlobalResponse.builder()
                .msg("User created successfully!")
                .data(modelMapper.map(saved, UserResponseDto.class))
                .status(StatusEnum.SUCCESS)
                .build();
        }else {
            return GlobalResponse.builder()
                    .msg("Invalid OTP!")
                    .status(StatusEnum.INVALID_OTP)
                    .build();
        }

    }


}
