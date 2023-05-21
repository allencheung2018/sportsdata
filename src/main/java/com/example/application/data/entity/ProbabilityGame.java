package com.example.application.data.entity;

import com.example.application.views.list.ListViewE0;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ProbabilityGame {

    public static List<String> AsianHandicap = Arrays.asList("0", "-0.25", "0.25", "-0.5", "0.5", "-0.75", "0.75",
            "-1", "1", "-1.25", "1.25", "-1.5", "1.5", "-1.75", "1.75", "-2", "2", "-2.25", "2.25", "-2.5", "2.5");
    public static final String HOST = "主";
    public static final String HOST_DRAW = "主+平";
    public static final String HOST_AWAY = "主+客";
    public static final String AWAY_DRAW = "客+平";
    public static final String HOST_175 = "主-1.75";
    public static final String HOST_15 = "主-1.5";
    public static final String HOST_125 = "主-1.25";
    public static final String HOST_1 = "主-1.0";
    public static final String HOST_075 = "主-0.75";
    public static final String HOST_05 = "主-0.5";
    public static final String HOST_025 = "主-0.25";
    public static final String HOST_0 = "主0.0";
    public static final String HOST025 = "主0.25";
    public static final String HOST05 = "主0.5";
    public static final String HOST075 = "主0.75";
    public static final String HOST1 = "主1.0";
    public static final String HOST125 = "主1.25";
    public static final String HOST15 = "主1.5";
    public static final String HOST175 = "主1.75";
    public static final String HOST20 = "主2.0";
    public static final String HOST225 = "主2.25";
    public static final String AWAY = "客";
    public static final String AWAY_175 = "客-1.75";
    public static final String AWAY_15 = "客-1.5";
    public static final String AWAY_125 = "客-1.25";
    public static final String AWAY_1 = "客-1.0";
    public static final String AWAY_075 = "客-0.75";
    public static final String AWAY_05 = "客-0.5";
    public static final String AWAY_025 = "客-0.25";
    public static final String AWAY_0 = "客0.0";
    public static final String AWAY025 = "客0.25";
    public static final String AWAY05 = "客0.5";
    public static final String AWAY075 = "客0.75";
    public static final String AWAY1 = "客1.0";
    public static final String AWAY125 = "客1.25";
    public static final String AWAY15 = "客1.5";
    public static final String AWAY175 = "客1.75";
    public static final String AWAY2 = "客2.0";
    public static final String AWAY225 = "客2.25";
    public static final String AWAY25 = "客2.5";
    public static final String AWAY275 = "客2.75";
    public static final String AWAY3 = "客3.0";

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
        return "SELECT COUNT(*) FROM " + tableName + " WHERE ahCurrentHome = " + ah
                + " AND awayTeam =:name AND date BETWEEN :begin AND :end";
    }

    public static Pair<String, String> getProbabilityOfDirection(String direction, String ah,
                                                                 ListViewE0.ProbabilityGameCompute compute) {
        String chance = "";
        String against = "";
        if (null == compute) {
            return Pair.of("", "");
        }

        if (HOST.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin()) * 100);
            against = String.format("%.1f", (compute.getAwayWin() + compute.getDraw()) * 100);
        } else if (AWAY.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin()) * 100);
            against = String.format("%.1f", (compute.getHostWin() + compute.getDraw()) * 100);
        } else if (HOST_DRAW.equals(direction)) {
            chance = String.format("%.1f", (compute.getHostWin() + compute.getDraw()) * 100);
            against = String.format("%.1f", (compute.getAwayWin()) * 100);
        } else if (HOST_AWAY.equals(direction)) {
            chance = String.format("%.1f", (compute.getHostWin() + compute.getAwayWin()) * 100);
            against = String.format("%.1f", (compute.getDraw()) * 100);
        } else if (AWAY_DRAW.equals(direction)) {
            chance = String.format("%.1f", (compute.getDraw() + compute.getAwayWin()) * 100);
            against = String.format("%.1f", (compute.getHostWin()) * 100);
        } else if (HOST_175.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getDraw() + compute.getAwayWin() + compute.getHostWin1Ball()) * 100);
        } else if (HOST_15.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getDraw() + compute.getAwayWin() + compute.getHostWin1Ball()) * 100);
        } else if (HOST_125.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getDraw() + compute.getAwayWin() + compute.getHostWin1Ball()) * 100);
        } else if (HOST_1.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getDraw() + compute.getAwayWin()) * 100);
        } else if (HOST_075.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin()) * 100);
            against = String.format("%.1f", (compute.getDraw() + compute.getAwayWin()) * 100);
        } else if (HOST_05.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin()) * 100);
            against = String.format("%.1f", (compute.getDraw() + compute.getAwayWin()) * 100);
        } else if (HOST_025.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin()) * 100);
            against = String.format("%.1f", (compute.getDraw() + compute.getAwayWin()) * 100);
        } else if (HOST_0.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin()) * 100);
            against = String.format("%.1f", (compute.getAwayWin()) * 100);
        } else if (HOST025.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() + compute.getDraw()) * 100);
            against = String.format("%.1f", (compute.getAwayWin()) * 100);
        } else if (HOST05.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() + compute.getDraw()) * 100);
            against = String.format("%.1f", (compute.getAwayWin()) * 100);
        } else if (HOST075.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() + compute.getDraw()) * 100);
            against = String.format("%.1f", (compute.getAwayWin()) * 100);
        } else if (HOST1.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() + compute.getDraw()) * 100);
            against = String.format("%.1f", (compute.getAwayWin() - compute.getAwayWin1Ball()) * 100);
        } else if (HOST125.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() + compute.getDraw() + compute.getAwayWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getAwayWin() - compute.getAwayWin1Ball()) * 100);
        } else if (HOST15.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() + compute.getDraw() + compute.getAwayWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getAwayWin() - compute.getAwayWin1Ball()) * 100);
        } else if (HOST175.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() + compute.getDraw() + compute.getAwayWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getAwayWin() - compute.getAwayWin1Ball()) * 100);
        } else if (HOST20.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() + compute.getDraw() + compute.getAwayWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getAwayWin() - compute.getAwayWin1Ball() - compute.getAwayWin2Ball()) * 100);
        } else if (HOST225.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getHostWin() + compute.getDraw()
                    + compute.getAwayWin1Ball() + compute.getAwayWin2Ball()) * 100);
            against = String.format("%.1f", (compute.getAwayWin() - compute.getAwayWin1Ball() - compute.getAwayWin2Ball()) * 100);
        } else if (AWAY_1.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() - compute.getAwayWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getHostWin() + compute.getDraw()) * 100);
        } else if (AWAY_125.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() - compute.getAwayWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getHostWin() + compute.getDraw() + compute.getAwayWin1Ball()) * 100);
        } else if (AWAY_075.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin()) * 100);
            against = String.format("%.1f", (compute.getHostWin() + compute.getDraw()) * 100);
        } else if (AWAY_05.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin()) * 100);
            against = String.format("%.1f", (compute.getHostWin() + compute.getDraw()) * 100);
        } else if (AWAY_025.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin()) * 100);
            against = String.format("%.1f", (compute.getHostWin() + compute.getDraw()) * 100);
        } else if (AWAY_0.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin()) * 100);
            against = String.format("%.1f", (compute.getHostWin()) * 100);
        } else if (AWAY025.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() + compute.getDraw()) * 100);
            against = String.format("%.1f", (compute.getHostWin()) * 100);
        } else if (AWAY05.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() + compute.getDraw()) * 100);
            against = String.format("%.1f", (compute.getHostWin()) * 100);
        } else if (AWAY075.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() + compute.getDraw()) * 100);
            against = String.format("%.1f", (compute.getHostWin()) * 100);
        } else if (AWAY1.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() + compute.getDraw()) * 100);
            against = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball()) * 100);
        } else if (AWAY125.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() + compute.getDraw() + compute.getHostWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball()) * 100);
        } else if (AWAY15.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() + compute.getDraw() + compute.getHostWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball()) * 100);
        } else if (AWAY175.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() + compute.getDraw() + compute.getHostWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball()) * 100);
        } else if (AWAY2.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() + compute.getDraw() + compute.getHostWin1Ball()) * 100);
            against = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball() - compute.getHostWin2Ball()) * 100);
        } else if (AWAY225.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() + compute.getDraw()
                    + compute.getHostWin1Ball() + compute.getHostWin2Ball()) * 100);
            against = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball() - compute.getHostWin2Ball()) * 100);
        } else if (AWAY25.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() + compute.getDraw()
                    + compute.getHostWin1Ball() + compute.getHostWin2Ball()) * 100);
            against = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball() - compute.getHostWin2Ball()) * 100);
        } else if (AWAY275.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() + compute.getDraw()
                    + compute.getHostWin1Ball() + compute.getHostWin2Ball()) * 100);
            against = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball() - compute.getHostWin2Ball()) * 100);
        } else if (AWAY3.equals(direction + ah)) {
            chance = String.format("%.1f", (compute.getAwayWin() + compute.getDraw()
                    + compute.getHostWin1Ball() + compute.getHostWin2Ball()) * 100);
            against = String.format("%.1f", (compute.getHostWin() - compute.getHostWin1Ball() - compute.getHostWin2Ball()) * 100);
        }

        return Pair.of(chance, against);
    }
}
