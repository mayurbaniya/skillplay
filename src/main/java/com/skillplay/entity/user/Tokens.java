package com.skillplay.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Tokens {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double token;

    private double miniToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userID;


}
