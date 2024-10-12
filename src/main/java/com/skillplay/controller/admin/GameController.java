package com.skillplay.controller.admin;

import com.skillplay.service.games.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/games/")
public class GameController {

    private final GameService gameService;


//    @GetMapping
//    private List<Games> getAllGames(){
//        return gameService.getAllGames();
//    }
//
//
//    @PostMapping
//    private Object saveGame(@RequestBody BGMI pobg){
//    	return gameService.saveGame(pobg);
//
//    }



}
