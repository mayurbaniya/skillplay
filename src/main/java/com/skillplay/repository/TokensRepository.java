package com.skillplay.repository;

import com.skillplay.entity.user.Tokens;
import com.skillplay.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokensRepository extends JpaRepository<Tokens, Long> {

    Optional<Tokens> findByUserID(User user);
}
