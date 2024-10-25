package com.skillplay.entity.games;

import com.skillplay.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "match_details")
public class MatchDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Additional Fields
    private int status; // 0 finished,1 active, 2 ongoing, 99 cancelled
    private boolean isPaid;
    private String matchLink;
    private int entryFee;
    private int matchCapacity;
    private LocalDateTime startTime;
    private int winningAmount;
    private int perKill;


    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Games game;

    @ManyToMany
    @JoinTable(
            name = "match_participants",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants;

    @OneToMany(mappedBy = "matchDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchRanking> rankings;
}
