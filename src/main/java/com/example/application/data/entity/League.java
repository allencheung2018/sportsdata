package com.example.application.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;


@Getter
@Setter
@MappedSuperclass
public abstract class League implements Serializable {

    public static final String[] nameColumns = {"date", "homeTeam", "awayTeam", "ftHomeGoal", "ftAwayGoal",
            "ahCurrentHome", "ahHome"};

    @Column(name = "Div")
    private String div;

    @Column(name = "Date")
    private Date date;

    @Column(name = "HomeTeam")
    private String homeTeam;

    @EmbeddedId
    private PrimaryInfo id;

    @Column(name = "AwayTeam")
    private String awayTeam;

    @Column(name = "FTHG")
    private int ftHomeGoal;

    @Column(name = "FTAG")
    private int ftAwayGoal;

    @Column(name = "AHh")
    private double ahHome;

    @Column(name = "AHCh")
    private double ahCurrentHome;

    @Getter
    @Embeddable
    public static class PrimaryInfo {
        private String div;
        private Date date;
        private String homeTeam;
    }

//    @Getter
//    @Setter
//    @AllArgsConstructor
//    public static class GameInfo implements Serializable{
//
////        public GameInfo(Date date, String home, String away, int homeGoal, int awayGoal, double ahch, double ah){
////            this.date = date;
////            this.homeTeam = home;
////            this.awayTeam = away;
////            this.ftHomeGoal = homeGoal;
////            this.ftAwayGoal = awayGoal;
////            this.ahCurrentHome = ahch;
////            this.ahHome = ah;
////        }
//
//        private Date date;
//        private String homeTeam;
//        private String awayTeam;
//        private Integer ftHomeGoal;
//        private Integer ftAwayGoal;
//        private Double ahCurrentHome;
//        private Double ahHome;
//    }
}
