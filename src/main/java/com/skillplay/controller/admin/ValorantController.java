package com.skillplay.controller.admin;

import com.skillplay.dto.ValorantDTO;
import com.skillplay.service.games.ValorantService;
import com.skillplay.utils.GlobalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/valorant")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ValorantController {

    private final ValorantService valorantService;

    @PostMapping("create-match")
    public ResponseEntity<GlobalResponse> createGame(@RequestBody ValorantDTO dto) {
        return ResponseEntity.ok( valorantService.createValorantGame(dto));
    }

    @GetMapping("get-match-byId/{id}")
    public ResponseEntity<GlobalResponse> getGame(@PathVariable Long id) {

        return ResponseEntity.ok(valorantService.getValorantGame(id));
    }
}
