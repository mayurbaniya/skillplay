package com.skillplay.service.games.impl;

import com.skillplay.entity.games.BGMI;
import com.skillplay.entity.games.Games;
import com.skillplay.repository.GameRepository;
import com.skillplay.service.games.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    public List<Games> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Games saveGame(BGMI games) {
        return gameRepository.save(games);
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
