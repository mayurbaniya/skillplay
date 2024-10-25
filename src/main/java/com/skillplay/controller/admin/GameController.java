package com.skillplay.controller.admin;

import com.skillplay.entity.games.BGMI;
import com.skillplay.entity.games.Games;
import com.skillplay.service.games.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/games/")
public class GameController {

    private final GameService gameService;


    @GetMapping
    private List<Games> getAllGames(){

        return gameService.getAllGames();
    }


    @PostMapping
    private Object saveGame(@RequestBody BGMI pobg){
    	return gameService.saveGame(pobg);
       // log.info("controller: {}",savedGame);
    }



}
