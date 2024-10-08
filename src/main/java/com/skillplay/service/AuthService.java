package com.skillplay.service;

import com.skillplay.entity.user.User;
import com.skillplay.repository.UserRepository;
import com.skillplay.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User saveUser(User user){
    	user.setStatus(Status.ACTIVE.get());
        return userRepository.save(user);

    }
}
