package com.skillplay.entity.games;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@DiscriminatorValue("VALORANT")
public class Valorant extends Games {

    private String agent; // Character selected
    private int roundsPlayed;
    private int wins;
    private int losses;
    private boolean spikePlanted;
    // Add other Valorant-specific fields
}
