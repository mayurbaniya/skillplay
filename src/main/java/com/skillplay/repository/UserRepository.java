package com.skillplay.repository;

import com.skillplay.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByEmail(String email);
    User findByUsername(String username);
    User findByEmailAndPassword(String email, String password);

}
