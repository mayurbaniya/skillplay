package com.skillplay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillplay.entity.games.BGMI;

public interface BgmiRepository extends JpaRepository<BGMI, Long>{

}
