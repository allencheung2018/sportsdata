package com.example.application.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class GameInfo {

        private Date date;
        private String homeTeam;
        private String awayTeam;
        private Integer ftHomeGoal;
        private Integer ftAwayGoal;
        private Double ahCurrentHome;
        private Double ahHome;
}
