package com.skillplay.dto;

import com.skillplay.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GameDto {
    private Long id;

    private LocalDateTime datePlayed;
    private User user;
}
