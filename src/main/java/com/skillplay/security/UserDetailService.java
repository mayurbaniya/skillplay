package com.skillplay.security;

import com.skillplay.entity.user.User;
import com.skillplay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmailOrUsername(username, username);

            if (user == null) {
                log.error("User not found with email: {}", username);
                throw new UsernameNotFoundException("User not found with email: " + username);
            }

            return new CustomUserDetails(user);

        } catch (Exception e) {
            log.error("Error occurred while loading user by email: {}", username, e);
            throw new UsernameNotFoundException("Error occurred while loading user by email: " + username, e);
        }
    }
}
