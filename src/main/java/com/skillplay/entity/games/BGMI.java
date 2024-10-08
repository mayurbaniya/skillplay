package com.skillplay.entity.games;



import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("BGMI")
public class BGMI extends Games {


    private String matchType; // solo, duo, squad
    private String map;
    private String perspective; //tpp or fpp
    private String matchMode; // tdm, classic
    private int perKill;

}
