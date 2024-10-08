package com.skillplay.service;

import com.skillplay.entity.user.User;
import com.skillplay.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User saveUser(User user){

        return userRepository.save(user);

    }
}
