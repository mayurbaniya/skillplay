package com.skillplay.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ValorantDTO {
    private Long id;
    private LocalDateTime datePlayed;
    private String agent;
    private int roundsPlayed;
    private int wins;
    private int losses;
    private boolean spikePlanted;




}
