package com.skillplay.repository;

import com.skillplay.entity.games.Valorant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValorantRepository extends JpaRepository<Valorant,Long> {

}
