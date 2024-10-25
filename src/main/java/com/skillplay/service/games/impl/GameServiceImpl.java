package com.skillplay.service.games.impl;

import com.skillplay.dto.BGMIDto;
import com.skillplay.dto.GameDto;
import com.skillplay.entity.games.BGMI;
import com.skillplay.entity.games.Games;
import com.skillplay.repository.BgmiRepository;
import com.skillplay.repository.GameRepository;
import com.skillplay.service.games.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final BgmiRepository bgmiRepository;

    @Override
    public List<Games> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Games saveGame(BGMI games) {
    	Games  savedGame = gameRepository.save(games);
    
    	
    	log.info("bgmiDto: {}",savedGame);
    	
        return savedGame;
    }

    @Override
    public Games updateGame(long gameID, Games games) {
        return null;
    }

    @Override
    public Games deleteGame(long gameID) {
        return null;
    }
}
