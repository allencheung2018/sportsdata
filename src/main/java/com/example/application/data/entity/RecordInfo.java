package com.example.application.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class RecordInfo {

    public static final String[] nameColumns = {"matchDate", "league", "homeTeam", "homeGoal", "awayGoal", "ah",
            "ahBet", "direction",  "profit"};

    private Long id;
    private Date matchDate;
    public void setMatchDate(LocalDate date) {
        matchDate = Date.valueOf(date);
    }
    public LocalDate getMatchDate() {
        return matchDate.toLocalDate();
    }

    private Date betDate;
    public void setBetDate(LocalDate date) {
        betDate = Date.valueOf(date);
    }
    public LocalDate getBetDate() {
        return betDate.toLocalDate();
    }

    private String league;
    private String homeTeam;
    private Integer homeGoal;
    private Integer awayGoal;
    private Float ah;
    private Float ahBet;
    private Float profit;
    private String direction;
    private Float goalLine;
    private Float betGL;

    public RecordInfo(LocalDate matchDate, LocalDate betDate) {
        setMatchDate(matchDate);
        setBetDate(betDate);
    }
}
