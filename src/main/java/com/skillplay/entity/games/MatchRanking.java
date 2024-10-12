package com.skillplay.entity.games;

import com.skillplay.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
name = "match_rankings",
uniqueConstraints = {@UniqueConstraint(columnNames = {"match_id", "rankValue"})})
public class MatchRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rankValue; // 1, 2, 3

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private MatchDetails matchDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
