package com.skillplay.service.games;

import com.skillplay.entity.games.BGMI;
import com.skillplay.entity.games.Games;

import java.util.List;

public interface GameService {

    List<Games> getAllGames();

    Games saveGame(BGMI games);

    Games updateGame(long gameID, Games games);

    Games deleteGame(long gameID);

}
