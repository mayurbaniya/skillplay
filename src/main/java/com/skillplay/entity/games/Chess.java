package com.skillplay.entity.games;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@DiscriminatorValue("CHESS")
public class Chess extends Games {

    private String gameType; // Blitz, Rapid, Classical
    private int movesCount;
    private String result; // White Win, Black Win, Draw
    private boolean rated;
    // Add other Chess-specific fields
}
