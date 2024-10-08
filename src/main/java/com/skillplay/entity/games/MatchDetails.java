package com.skillplay.entity.games;

import com.skillplay.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class MatchDetails {



    //    private int status; // 0 finished,1 active, 2 ongoing, 99 cancelled
    //    private String isPaid;
    //    private String matchLink;
    //    private int entryFee;
    //    private int matchCapacity;
    //
    //    private LocalDateTime startTime;
    //    private int winningAmount;
    //    private int perKill;


    private List<User> participants;

    private List<User> firstRank;
    private List<User> secondRank;
    private List<User> thirdRank;



}
