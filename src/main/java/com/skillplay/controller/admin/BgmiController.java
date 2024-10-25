package com.skillplay.controller.admin;

import com.skillplay.dto.BGMIDto;
import com.skillplay.service.games.BgmiService;
import com.skillplay.utils.GlobalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/bgmi/")
public class BgmiController {

    private final BgmiService bgmiService;

    @PostMapping("create-match")
    private ResponseEntity<GlobalResponse> createMatch(@RequestBody BGMIDto bgmiDto){
        return ResponseEntity.ok(new GlobalResponse());
    }

    @GetMapping("get-by-Id/{matchID}")
    private ResponseEntity<GlobalResponse> getMatchById(@PathVariable("matchID") int matchID){
        return ResponseEntity.ok(bgmiService.getMatchById(matchID));
    }

    @GetMapping("get-all/{pageNo}/{pageSize}")
    private ResponseEntity<GlobalResponse> getAllMatches(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize){
        return ResponseEntity.ok(bgmiService.getAllMatches(pageNo, pageSize));
    }


}
