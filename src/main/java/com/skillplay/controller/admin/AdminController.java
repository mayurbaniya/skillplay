package com.skillplay.controller.admin;

import com.skillplay.dto.TokensDto;
import com.skillplay.service.admin.AdminService;
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
    private final AdminService adminService;

    @PostMapping("add-coins")
    private ResponseEntity<GlobalResponse> addCoins(@RequestBody TokensDto tokensDto){
        return ResponseEntity.ok(tokensService.addToken(tokensDto));
    }

    @PostMapping("ban-user")
    private ResponseEntity<GlobalResponse> banUser(@RequestParam("adminID") int adminID, @RequestParam("userID") int userID){

        return ResponseEntity.ok(adminService.banUser(adminID,userID));
    }



}
