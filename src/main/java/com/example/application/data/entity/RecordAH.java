package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "RecordAH")
@Getter
@Setter
public class RecordAH implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "Date")
    private Date gameDate;

    @Column(name = "Match")
    private String match;

    @Column(name = "FTHG")
    private Integer hostGoal;

    @Column(name = "FTAG")
    private Integer awayGoal;

    @Column(name = "AH")
    private Float ah;

    @Column(name = "BetAH")
    private Float ahBet;

    @Column(name = "HomeTeam")
    private String homeTeam;

    @Column(name = "Direction")
    private String direction;

    @Column(name = "Profit")
    private Float profit;
}
