package com.skillplay.repository;

import com.skillplay.entity.games.Games;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Games, Long> {


}
