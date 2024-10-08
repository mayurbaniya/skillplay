package com.skillplay.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BGMIDto extends GameDto{

    private String matchType; // solo, duo, squad
    private String map;
    private String perspective; //tpp or fpp
    private String matchMode; // tdm, classic
    private int perKill;
}
