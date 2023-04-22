package com.example.application.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@MappedSuperclass
public abstract class League implements Serializable {
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
    public static class PrimaryInfo{
        private String div;
        private Date date;
        private String homeTeam;
    }
}
