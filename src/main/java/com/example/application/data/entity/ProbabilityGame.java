package com.example.application.data.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ProbabilityGame {

    public static List<String> AsianHandicap = Arrays.asList("0", "-0.25", "0.25", "-0.5", "0.5", "-0.75", "0.75",
            "-1", "1", "-1.25", "1.25", "-1.5", "1.5", "-1.75", "1.75", "-2", "2", "-2.25", "2.25", "-2.5", "2.5");

    private String ah;
    private String totalMatch;
    private String hostWin2Ball;
    private String hostWin1Ball;
    private String hostWin;
    private String draw;
    private String awayWin;
    private String awayWin1Ball;
    private String awayWin2Ball;

    public ProbabilityGame() {

    }

    public ProbabilityGame(String totalMatch, String hostWin2Ball, String hostWin1Ball, String hostWin, String draw,
                           String awayWin, String awayWin1Ball, String awayWin2Ball) {
        this.totalMatch = totalMatch;
        this.hostWin2Ball = hostWin2Ball;
        this.hostWin1Ball = hostWin1Ball;
        this.hostWin = hostWin;
        this.draw = draw;
        this.awayWin = awayWin;
        this.awayWin1Ball = awayWin1Ball;
        this.awayWin2Ball = awayWin2Ball;
    }

    public static String getQueryStringAHCount(String tableName, String ah) {
        return "SELECT COUNT(*) FROM " + tableName  + " WHERE ahCurrentHome = " + ah
                + " AND awayTeam =:name AND date BETWEEN :begin AND :end";
    }
}
