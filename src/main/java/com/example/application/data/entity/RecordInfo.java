package com.example.application.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class RecordInfo {

    public static final String[] nameColumns = {"date", "match", "homeTeam", "homeGoal", "awayGoal", "ah",
            "ahBet", "direction",  "profit"};

    private Date date;
    private String match;
    private String homeTeam;
    private Integer homeGoal;
    private Integer awayGoal;
    private Float ah;
    private Float ahBet;
    private Float profit;
    private String direction;
}
