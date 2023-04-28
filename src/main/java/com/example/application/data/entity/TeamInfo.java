package com.example.application.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TeamInfo {

    public static final String[] nameColumns = {"name", "game", "host", "hostWin", "hostDraw", "hostOddsWin", "hostUp",
            "hostUpWin", "hostUpDraw", "hostDown", "hostDownWin", "hostDownDraw", "hostTie", "hostTieWin", "away",
            "awayWin", "awayDraw", "awayOddsWin", "awayUp", "awayUpWin", "awayUpDraw", "awayDown", "awayDownWin",
            "awayDownDraw", "awayTie", "awayTieWin"};

    private String name;
    private int game;
    private int host;
    private int hostWin;
    private int hostDraw;
    private int hostOddsWin;
    private int hostUp;
    private int hostUpWin;
    private int hostUpDraw;
    private int hostDown;
    private int hostDownWin;
    private int hostDownDraw;
    private int hostTie;
    private int hostTieWin;
    private int away;
    private int awayWin;
    private int awayDraw;
    private int awayOddsWin;
    private int awayUp;
    private int awayUpWin;
    private int awayUpDraw;
    private int awayDown;
    private int awayDownWin;
    private int awayDownDraw;
    private int awayTie;
    private int awayTieWin;
}
