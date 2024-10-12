package com.skillplay.controller.admin;

import com.skillplay.dto.TokensDto;
import com.skillplay.service.admin.TokensService;
import com.skillplay.utils.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final TokensService tokensService;

    @PostMapping("add-coins")
    private ResponseEntity<GlobalResponse> addCoins(@RequestBody TokensDto tokensDto){
        return ResponseEntity.ok(tokensService.addToken(tokensDto));
    }

}
