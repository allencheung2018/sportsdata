package com.example.application.views.list;

import com.example.application.data.entity.ProbabilityGame;
import com.example.application.data.entity.TeamInfo;
import com.example.application.data.repository.E_0Repository;
import com.example.application.data.service.DataService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.util.Pair;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@PageTitle("E0 | Sports")
@Route(value = "", layout = MainLayout.class)
@Slf4j
public class ListViewE0 extends ListView {

    final static private String nameLeague = "England Premier League";
    final private E_0Repository e0Repository;
    private final DataService dataService;
    Pair<Date, Date> pair;
    List<String> teamNames;

    private float ah = 0;
    private ProbabilityGameCompute pgCompute = new ProbabilityGameCompute();


    public ListViewE0(E_0Repository e0Repository, DataService dataService) {
        super();
        addLeagueName();
        this.e0Repository = e0Repository;
        this.dataService = dataService;
        initTeamsAndDate();
        updateToolbar(teamNames, pair);

//        add(getTestButton());
    }

    private void addLeagueName() {
        Label label = new Label(nameLeague);
        addComponentAsFirst(label);
    }

    private void initTeamsAndDate() {
        pair = dateSeason.get("2223");
        teamNames = e0Repository.getTeamBetweenDate(pair.getFirst(), pair.getSecond());
    }

    public TeamInfo searchTeamInfo(String name, Date begin, Date end) {
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setName(name);
        teamInfo.setGame(e0Repository.getCountGames(name, begin, end));
        teamInfo.setHost(e0Repository.getCountHost(name, begin, end));
        teamInfo.setHostWin(e0Repository.getCountHostWin(name, begin, end));
        teamInfo.setHostDraw(e0Repository.getCountHostDraw(name, begin, end));
        teamInfo.setHostUp(e0Repository.getCountHostUp(name, begin, end));
        teamInfo.setHostUpWin(e0Repository.getCountHostUpWin(name, begin, end));
        teamInfo.setHostUpDraw(e0Repository.getCountHostUpDraw(name, begin, end));
        teamInfo.setHostDown(e0Repository.getCountHostDown(name, begin, end));
        teamInfo.setHostDownWin(e0Repository.getCountHostDownWin(name, begin, end));
        teamInfo.setHostDownDraw(e0Repository.getCountHostDownDraw(name, begin, end));
        teamInfo.setHostTie(e0Repository.getCountHostTie(name, begin, end));
        teamInfo.setHostTieWin(e0Repository.getCountHostTieWin(name, begin, end));
        teamInfo.setAway(e0Repository.getCountAway(name, begin, end));
        teamInfo.setAwayWin(e0Repository.getCountAwayWin(name, begin, end));
        teamInfo.setAwayDraw(e0Repository.getCountAwayDraw(name, begin, end));
        teamInfo.setAwayUp(e0Repository.getCountAwayUp(name, begin, end));
        teamInfo.setAwayUpWin(e0Repository.getCountAwayUpWin(name, begin, end));
        teamInfo.setAwayUpDraw(e0Repository.getCountAwayUpDraw(name, begin, end));
        teamInfo.setAwayDown(e0Repository.getCountAwayDown(name, begin, end));
        teamInfo.setAwayDownWin(e0Repository.getCountAwayDownWin(name, begin, end));
        teamInfo.setAwayDownDraw(e0Repository.getCountAwayDownDraw(name, begin, end));
        teamInfo.setAwayTie(e0Repository.getCountAwayTie(name, begin, end));
        teamInfo.setAwayTieWin(e0Repository.getCountAwayTieWin(name, begin, end));
        teamInfo.setHostOddsWin(teamInfo.getHostUpWin() + teamInfo.getHostDownWin());
        teamInfo.setAwayOddsWin(teamInfo.getAwayUpWin() + teamInfo.getAwayDownWin());
        return teamInfo;
    }

    @Override
    public List<TeamInfo> searchAgainstTeams(Object home, Object away, LocalDate begin, LocalDate end) {
        List<TeamInfo> list = Lists.newArrayList();
        list.add(searchTeamInfo((String) home, Date.valueOf(begin), Date.valueOf(end)));
        list.add(searchTeamInfo((String) away, Date.valueOf(begin), Date.valueOf(end)));
        return list;
    }

    @Override
    public List<TeamInfo> getAllTeamsInfo(Date begin, Date end) {
        List<TeamInfo> list = Lists.newArrayList();
        teamNames.forEach(team -> list.add(searchTeamInfo(team, begin, end)));
        return list;
    }

    @Override
    public List<ProbabilityGame> getProbabilityGameAH(float ah, LocalDate begin, LocalDate end) {
        this.ah = ah;
        ProbabilityGame probabilityGame = new ProbabilityGame();
        int total = e0Repository.getAHCount(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setTotalMatch(String.valueOf(total));
        int hostWin = e0Repository.getAHCountHostWin(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setHostWin(String.valueOf(hostWin));
        int hostWin2Ball = e0Repository.getAHCountHostWin2Ball(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setHostWin2Ball(String.valueOf(hostWin2Ball));
        int hostWin1Ball = e0Repository.getAHCountHostWin1Ball(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setHostWin1Ball(String.valueOf(hostWin1Ball));
        int draw = e0Repository.getAHCountDraw(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setDraw(String.valueOf(draw));
        int awayWin = e0Repository.getAHCountAwayWin(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setAwayWin(String.valueOf(awayWin));
        int awayWin1Ball = e0Repository.getAHCountAwayWin1Ball(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setAwayWin1Ball(String.valueOf(awayWin1Ball));
        int awayWin2Ball = e0Repository.getAHCountAwayWin2Ball(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setAwayWin2Ball(String.valueOf(awayWin2Ball));

        ProbabilityGame probabilityPercentage = new ProbabilityGame();
        float totalMatch = (float) total / total;
        probabilityPercentage.setTotalMatch(String.format("%.1f", totalMatch * 100));
        pgCompute.setTotalMatch(totalMatch);
        float hostWinRate = (float) hostWin / total;
        probabilityPercentage.setHostWin(String.format("%.1f", hostWinRate * 100));
        pgCompute.setHostWin(hostWinRate);
        float hostWin1BallRate = (float) hostWin1Ball / total;
        probabilityPercentage.setHostWin1Ball(String.format("%.1f", hostWin1BallRate * 100));
        pgCompute.setHostWin1Ball(hostWin1BallRate);
        float hostWin2BallRate = (float) hostWin2Ball / total;
        probabilityPercentage.setHostWin2Ball(String.format("%.1f", hostWin2BallRate * 100));
        pgCompute.setHostWin2Ball(hostWin2BallRate);
        float drawRate = (float) draw / total;
        probabilityPercentage.setDraw(String.format("%.1f", drawRate * 100));
        pgCompute.setDraw(drawRate);
        float awayWinRate = (float) awayWin / total;
        probabilityPercentage.setAwayWin(String.format("%.1f", awayWinRate * 100));
        pgCompute.setAwayWin(awayWinRate);
        float awayWin1BallRate = (float) awayWin1Ball / total;
        probabilityPercentage.setAwayWin1Ball(String.format("%.1f", awayWin1BallRate * 100));
        pgCompute.setAwayWin1Ball(awayWin1BallRate);
        float awayWin2BallRate = (float) awayWin2Ball / total;
        probabilityPercentage.setAwayWin2Ball(String.format("%.1f", awayWin2BallRate * 100));
        pgCompute.setAwayWin2Ball(awayWin2BallRate);

        return List.of(probabilityGame, probabilityPercentage);
    }

    @Override
    public ChanceGame getChance(String ah) {
        if (ah.equals("0")) {
            return getChance_0(pgCompute);
        } else if (ah.equals("-0.25")) {
            return getChance_025(pgCompute);
        } else if (ah.equals("0.25")) {
            return getChance025(pgCompute);
        } else if (ah.equals("-0.75")) {
            return getChance_075(pgCompute);
        } else if (ah.equals("0.75")) {
            return getChance075(pgCompute);
        } else if (ah.equals("-1")) {
            return getChance_10(pgCompute);
        } else if (ah.equals("1")) {
            return getChance10(pgCompute);
        } else if (ah.equals("-1.25")) {
            return getChance_125(pgCompute);
        } else if (ah.equals("1.25")) {
            return getChance125(pgCompute);
        }
        return new ChanceGame();
    }

    public static ChanceGame getChance_0(ProbabilityGameCompute pgCompute) {
        ChanceGame chanceGame = new ChanceGame();

        chanceGame.setUpChance(pgCompute.getHostWin());
        chanceGame.setUp_1_Chance(pgCompute.getHostWin());
        chanceGame.setUp_2_Chance(pgCompute.getHostWin()  + pgCompute.getDraw());
        chanceGame.setUp_3_Chance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setDownChance(pgCompute.getAwayWin());
        chanceGame.setDown_1_Chance(pgCompute.getAwayWin());
        chanceGame.setDown_2_Chance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setDown_3_Chance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostDrawChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setAwayDrawChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostAwayChance(pgCompute.getAwayWin() + pgCompute.getHostWin());
        return chanceGame;
    }

    public static ChanceGame getChance_025(ProbabilityGameCompute pgCompute) {
        ChanceGame chanceGame = new ChanceGame();

        chanceGame.setUpChance(pgCompute.getHostWin());
        chanceGame.setUp_1_Chance(pgCompute.getHostWin());
        chanceGame.setUp_2_Chance(pgCompute.getHostWin());
        chanceGame.setUp_3_Chance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setDownChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setDown_1_Chance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setDown_2_Chance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setDown_3_Chance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostDrawChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setAwayDrawChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostAwayChance(pgCompute.getAwayWin() + pgCompute.getHostWin());
        return chanceGame;
    }

    public static ChanceGame getChance025(ProbabilityGameCompute pgCompute) {
        ChanceGame chanceGame = new ChanceGame();

        chanceGame.setUpChance(pgCompute.getAwayWin());
        chanceGame.setUp_1_Chance(pgCompute.getAwayWin());
        chanceGame.setUp_2_Chance(pgCompute.getAwayWin());
        chanceGame.setUp_3_Chance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setDownChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setDown_1_Chance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setDown_2_Chance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setDown_3_Chance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setHostDrawChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setAwayDrawChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostAwayChance(pgCompute.getAwayWin() + pgCompute.getHostWin());
        return chanceGame;
    }

    public static ChanceGame getChance_05(ProbabilityGameCompute pgCompute) {
        ChanceGame chanceGame = new ChanceGame();

        chanceGame.setUpChance(pgCompute.getHostWin());
        chanceGame.setUp_1_Chance(pgCompute.getHostWin());
        chanceGame.setUp_2_Chance(pgCompute.getHostWin());
        chanceGame.setUp_3_Chance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setDownChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setDown_1_Chance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setDown_2_Chance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setDown_3_Chance(pgCompute.getAwayWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setHostDrawChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setAwayDrawChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostAwayChance(pgCompute.getAwayWin() + pgCompute.getHostWin());
        return chanceGame;
    }

    public static ChanceGame getChance05(ProbabilityGameCompute pgCompute) {
        ChanceGame chanceGame = new ChanceGame();

        chanceGame.setUpChance(pgCompute.getAwayWin());
        chanceGame.setUp_1_Chance(pgCompute.getAwayWin());
        chanceGame.setUp_2_Chance(pgCompute.getAwayWin());
        chanceGame.setUp_3_Chance(pgCompute.getAwayWin());
        chanceGame.setDownChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setDown_1_Chance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setDown_2_Chance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setDown_3_Chance(pgCompute.getHostWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setHostDrawChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setAwayDrawChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostAwayChance(pgCompute.getAwayWin() + pgCompute.getHostWin());
        return chanceGame;
    }

    public static ChanceGame getChance_075(ProbabilityGameCompute pgCompute) {
        ChanceGame chanceGame = new ChanceGame();

        chanceGame.setUpChance(pgCompute.getHostWin());
        chanceGame.setUp_1_Chance(pgCompute.getHostWin());
        chanceGame.setUp_2_Chance(pgCompute.getHostWin());
        chanceGame.setUp_3_Chance(pgCompute.getHostWin());
        chanceGame.setDownChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setDown_1_Chance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setDown_2_Chance(pgCompute.getAwayWin() + pgCompute.getDraw() + pgCompute.getHostWin1Ball());
        chanceGame.setDown_3_Chance(pgCompute.getAwayWin() + pgCompute.getDraw() + pgCompute.getHostWin1Ball());
        chanceGame.setHostDrawChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setAwayDrawChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostAwayChance(pgCompute.getAwayWin() + pgCompute.getHostWin());
        return chanceGame;
    }

    public static ChanceGame getChance075(ProbabilityGameCompute pgCompute) {
        ChanceGame chanceGame = new ChanceGame();

        chanceGame.setUpChance(pgCompute.getAwayWin());
        chanceGame.setUp_1_Chance(pgCompute.getAwayWin());
        chanceGame.setUp_2_Chance(pgCompute.getAwayWin());
        chanceGame.setUp_3_Chance(pgCompute.getAwayWin());
        chanceGame.setDownChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setDown_1_Chance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setDown_2_Chance(pgCompute.getHostWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setDown_3_Chance(pgCompute.getHostWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setHostDrawChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setAwayDrawChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostAwayChance(pgCompute.getAwayWin() + pgCompute.getHostWin());
        return chanceGame;
    }

    public static ChanceGame getChance_10(ProbabilityGameCompute pgCompute) {
        ChanceGame chanceGame = new ChanceGame();

        chanceGame.setUpChance(pgCompute.getHostWin() - pgCompute.getHostWin1Ball());
        chanceGame.setUp_1_Chance(pgCompute.getHostWin());
        chanceGame.setUp_2_Chance(pgCompute.getHostWin());
        chanceGame.setUp_3_Chance(pgCompute.getHostWin());
        chanceGame.setDownChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setDown_1_Chance(pgCompute.getAwayWin() + pgCompute.getDraw() + pgCompute.getHostWin1Ball());
        chanceGame.setDown_2_Chance(pgCompute.getAwayWin() + pgCompute.getDraw() + pgCompute.getHostWin1Ball());
        chanceGame.setDown_3_Chance(pgCompute.getAwayWin() + pgCompute.getDraw() + pgCompute.getHostWin1Ball());
        chanceGame.setHostDrawChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setAwayDrawChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostAwayChance(pgCompute.getAwayWin() + pgCompute.getHostWin());
        return chanceGame;
    }

    public static ChanceGame getChance10(ProbabilityGameCompute pgCompute) {
        ChanceGame chanceGame = new ChanceGame();

        chanceGame.setUpChance(pgCompute.getAwayWin() - pgCompute.getAwayWin1Ball());
        chanceGame.setUp_1_Chance(pgCompute.getAwayWin());
        chanceGame.setUp_2_Chance(pgCompute.getAwayWin());
        chanceGame.setUp_3_Chance(pgCompute.getAwayWin());
        chanceGame.setDownChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setDown_1_Chance(pgCompute.getHostWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setDown_2_Chance(pgCompute.getHostWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setDown_3_Chance(pgCompute.getHostWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setHostDrawChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setAwayDrawChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostAwayChance(pgCompute.getAwayWin() + pgCompute.getHostWin());
        return chanceGame;
    }

    public static ChanceGame getChance_125(ProbabilityGameCompute pgCompute) {
        ChanceGame chanceGame = new ChanceGame();

        chanceGame.setUpChance(pgCompute.getHostWin());
        chanceGame.setUp_1_Chance(pgCompute.getHostWin());
        chanceGame.setUp_2_Chance(pgCompute.getHostWin());
        chanceGame.setUp_3_Chance(pgCompute.getHostWin());
        chanceGame.setDownChance(pgCompute.getAwayWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setDown_1_Chance(pgCompute.getAwayWin() + pgCompute.getDraw() + + pgCompute.getAwayWin1Ball());
        chanceGame.setDown_2_Chance(pgCompute.getAwayWin() + pgCompute.getDraw() + + pgCompute.getAwayWin1Ball());
        chanceGame.setDown_3_Chance(pgCompute.getAwayWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setHostDrawChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setAwayDrawChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostAwayChance(pgCompute.getAwayWin() + pgCompute.getHostWin());
        return chanceGame;
    }

    public static ChanceGame getChance125(ProbabilityGameCompute pgCompute) {
        ChanceGame chanceGame = new ChanceGame();

        chanceGame.setUpChance(pgCompute.getAwayWin() - pgCompute.getAwayWin1Ball());
        chanceGame.setUp_1_Chance(pgCompute.getAwayWin() - pgCompute.getAwayWin1Ball());
        chanceGame.setUp_2_Chance(pgCompute.getAwayWin());
        chanceGame.setUp_3_Chance(pgCompute.getAwayWin());
        chanceGame.setDownChance(pgCompute.getHostWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setDown_1_Chance(pgCompute.getHostWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setDown_2_Chance(pgCompute.getHostWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setDown_3_Chance(pgCompute.getHostWin() + pgCompute.getDraw() + pgCompute.getAwayWin1Ball());
        chanceGame.setHostDrawChance(pgCompute.getHostWin() + pgCompute.getDraw());
        chanceGame.setAwayDrawChance(pgCompute.getAwayWin() + pgCompute.getDraw());
        chanceGame.setHostAwayChance(pgCompute.getAwayWin() + pgCompute.getHostWin());
        return chanceGame;
    }

    @Override
    public String getUpProfit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return getUpProfit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getDownProfit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return getDownProfit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getUp_1_Profit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return getUp_1_Profit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getUp_2_Profit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return getUp_2_Profit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getUp_3_Profit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return getUp_3_Profit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getDown_1_Profit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return getDown_1_Profit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getDown_2_Profit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return getDown_2_Profit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getDown_3_Profit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return getDown_3_Profit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getHostDrawProfit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return getHostDrawProfit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getAwayDrawProfit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return getAwayDrawProfit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getHostAwayProfit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return getHostAwayProfit(ah, Float.parseFloat(odds), pgCompute);
    }

    public static String getUpProfit(String ah, float odds, ProbabilityGameCompute pgCompute) {
        double result = 0;
        if (ah.equals("0")) {
            result = odds * pgCompute.getHostWin() - 1 * pgCompute.getAwayWin();
        } else if (ah.equals("-0.25")) {
            result = odds * pgCompute.getHostWin() - 0.5 * pgCompute.getDraw() - 1.0 * pgCompute.getAwayWin();
        } else if (ah.equals("0.25")) {
            result = odds * pgCompute.getAwayWin() - 0.5 * pgCompute.getDraw() - 1.0 * pgCompute.getHostWin();
        } else if (ah.equals("-0.5")) {
            result = odds * pgCompute.getHostWin() - 1 * pgCompute.getDraw() - 1.0 * pgCompute.getAwayWin();
        } else if (ah.equals("0.5")) {
            result = odds * pgCompute.getAwayWin() - 1 * pgCompute.getDraw() - 1.0 * pgCompute.getHostWin();
        } else if (ah.equals("-0.75")) {
            result = odds * pgCompute.getHostWin() - 0.5 * odds * pgCompute.getHostWin1Ball()
                    - 1 * pgCompute.getDraw() - 1.0 * pgCompute.getAwayWin();
        } else if (ah.equals("0.75")) {
            result = odds * pgCompute.getAwayWin() - 0.5 * odds * pgCompute.awayWin1Ball
                    - 0.5 * pgCompute.getDraw() - 1.0 * pgCompute.getAwayWin();
        } else if (ah.equals("-1")) {
            result = odds * (pgCompute.getHostWin() - pgCompute.hostWin1Ball)
                    - 1.0 * pgCompute.getDraw() - 1.0 * pgCompute.getAwayWin();
        } else if (ah.equals("1")) {
            result = odds * (pgCompute.getAwayWin() - pgCompute.awayWin1Ball)
                    - 1.0 * pgCompute.getDraw() - 1.0 * pgCompute.getHostWin();
        }
        return String.format("%.3f", result);
    }

    public static String getUp_1_Profit(String ah, float odds, ProbabilityGameCompute pgCompute) {
        double result = 0;
        if (ah.equals("0")) {
            result = odds * pgCompute.getHostWin() + odds * 0.5 * pgCompute.getDraw() - 1 * pgCompute.getAwayWin();
        } else if (ah.equals("-0.25")) {
            result = odds * pgCompute.getHostWin() - 0.5 * pgCompute.getDraw() - 1.0 * pgCompute.getAwayWin();
        }
        return String.format("%.3f", result);
    }

    public static String getUp_2_Profit(String ah, float odds, ProbabilityGameCompute pgCompute) {
        double result = 0;
        if (ah.equals("0")) {
            result = odds * (pgCompute.getHostWin() + pgCompute.getDraw()) - 1 * pgCompute.getAwayWin();
        } else if (ah.equals("-0.25")) {
            result = odds * pgCompute.getHostWin() + odds * 0.5 * pgCompute.getDraw() - 1.0 * pgCompute.getAwayWin();
        }
        return String.format("%.3f", result);
    }


    public static String getUp_3_Profit(String ah, float odds, ProbabilityGameCompute pgCompute) {
        double result = 0;
        if (ah.equals("0")) {
            result = odds * (pgCompute.getHostWin() + pgCompute.getDraw())
                    - 0.5 * pgCompute.getAwayWin1Ball() - 1 * (pgCompute.getAwayWin() - pgCompute.getAwayWin1Ball());
        } else if (ah.equals("-0.25")) {
            result = odds * pgCompute.getHostWin() + odds * pgCompute.getDraw() - 1.0 * pgCompute.getAwayWin();
        }
        return String.format("%.3f", result);
    }

    public static String getDownProfit(String ah, float odds, ProbabilityGameCompute pgCompute) {
        double result = 0;
        if (ah.equals("0")) {
            result = odds * pgCompute.getAwayWin() - 1 * pgCompute.getHostWin();
        } else if (ah.equals("-0.25")) {
            result = odds * pgCompute.getAwayWin() + 0.5 * pgCompute.getDraw() - 1.0 * pgCompute.getHostWin();
        }
        return String.format("%.3f", result);
    }

    public static String getDown_1_Profit(String ah, float odds, ProbabilityGameCompute pgCompute) {
        double result = 0;
        if (ah.equals("0")) {
            result = odds * pgCompute.getAwayWin() + odds * 0.5 * pgCompute.getDraw()
                    - 1 * pgCompute.getHostWin();
        } else if (ah.equals("-0.25")) {
            result = odds * pgCompute.getAwayWin() - 1.0 * pgCompute.getHostWin();
        }
        return String.format("%.3f", result);
    }

    public static String getDown_2_Profit(String ah, float odds, ProbabilityGameCompute pgCompute) {
        double result = 0;
        if (ah.equals("0")) {
            result = odds * pgCompute.getAwayWin() + odds * pgCompute.getDraw()
                    - 1 * pgCompute.getHostWin();
        } else if (ah.equals("-0.25")) {
            result = odds * pgCompute.getAwayWin() + odds * 0.5 * pgCompute.getDraw() - 1.0 * pgCompute.getHostWin();
        }
        return String.format("%.3f", result);
    }

    public static String getDown_3_Profit(String ah, float odds, ProbabilityGameCompute pgCompute) {
        double result = 0;
        if (ah.equals("0")) {
            result = odds * pgCompute.getAwayWin() + odds * pgCompute.getDraw()
                    - 0.5 * pgCompute.getHostWin1Ball() - 1 * (pgCompute.getHostWin() - pgCompute.getHostWin1Ball());
        } else if (ah.equals("-0.25")) {
            result = odds * pgCompute.getAwayWin() + odds * pgCompute.getDraw() - 1.0 * pgCompute.getHostWin();
        }
        return String.format("%.3f", result);
    }

    public static String getHostDrawProfit(String ah, float odds, ProbabilityGameCompute pgCompute) {
        double result = odds * (pgCompute.getHostWin() + pgCompute.getDraw()) - 1.0 * pgCompute.getAwayWin();

        return String.format("%.3f", result);
    }

    public static String getAwayDrawProfit(String ah, float odds, ProbabilityGameCompute pgCompute) {
        double result = odds * (pgCompute.getAwayWin() + pgCompute.getDraw()) - 1.0 * pgCompute.getHostWin();

        return String.format("%.3f", result);
    }

    public static String getHostAwayProfit(String ah, float odds, ProbabilityGameCompute pgCompute) {
        double result = odds * (pgCompute.getHostWin() + pgCompute.getAwayWin()) - 1.0 * pgCompute.getDraw();

        return String.format("%.3f", result);
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class ProbabilityGameCompute {
        private float totalMatch;
        private float hostWin2Ball;
        private float hostWin1Ball;
        private float hostWin;
        private float draw;
        private float awayWin;
        private float awayWin1Ball;
        private float awayWin2Ball;
    }

    @Getter
    @Setter
    public static class ChanceGame {
        private double upChance;
        private double up_1_Chance;
        private double up_2_Chance;
        private double up_3_Chance;
        private double downChance;
        private double down_1_Chance;
        private double down_2_Chance;
        private double down_3_Chance;
        private double hostDrawChance;
        private double awayDrawChance;
        private double hostAwayChance;
    }

    @Override
    public Button getTestButton() {
        Button button = new Button("Test Connection");
        button.addClickListener(event -> {
            log.debug("count = " + e0Repository.getAHCount(-0.25, pair.getFirst(), pair.getSecond()));
            log.debug("Team:" + e0Repository.getTeamBetweenDate(Date.valueOf("2022-8-1"), Date.valueOf("2023-5-31")));
        });
        return button;
    }
}