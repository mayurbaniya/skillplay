package com.skillplay.validation;

import com.skillplay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public boolean checkUserExistByEmail(String email){
        return Optional.ofNullable(userRepository.findByEmail(email)).isPresent();
    }

    public boolean checkUserExistByUsername(String username){
        return Optional.ofNullable(userRepository.findByUsername(username)).isPresent();
    }
}
