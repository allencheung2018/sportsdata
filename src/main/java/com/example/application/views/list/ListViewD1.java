package com.example.application.views.list;

import com.example.application.data.entity.GameInfo;
import com.example.application.data.entity.ProbabilityGame;
import com.example.application.data.entity.TeamInfo;
import com.example.application.data.repository.D_1Repository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.util.Pair;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static com.example.application.views.list.ListViewE0.*;

@PageTitle("D1 | Sports")
@Route(value = "D1", layout = MainLayout.class)
@Slf4j
public class ListViewD1 extends ListView{

    final static private String nameLeague = "Germany Bundesliga I";
    final private D_1Repository d1Repository;
    Pair<Date, Date> pair;
    List<String> teamNames;
    private float ah = 0;
    private ListViewE0.ProbabilityGameCompute pgCompute = new ListViewE0.ProbabilityGameCompute();

    public ListViewD1(D_1Repository d1Repository) {
        super();
        this.d1Repository = d1Repository;
        addLeagueName();
        initTeamsAndDate();
        updateToolbar(teamNames, pair);
    }

    private void addLeagueName() {
        Label label = new Label(nameLeague);
        addComponentAsFirst(label);
    }

    private void initTeamsAndDate() {
        pair = dateSeason.get("2223");
        teamNames = d1Repository.getTeamBetweenDate(pair.getFirst(), pair.getSecond());
    }

    public TeamInfo searchTeamInfo(String name, Date begin, Date end) {
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setName(name);
        teamInfo.setGame(d1Repository.getCountGames(name, begin, end));
        teamInfo.setHost(d1Repository.getCountHost(name, begin, end));
        teamInfo.setHostWin(d1Repository.getCountHostWin(name, begin, end));
        teamInfo.setHostDraw(d1Repository.getCountHostDraw(name, begin, end));
        teamInfo.setHostUp(d1Repository.getCountHostUp(name, begin, end));
        teamInfo.setHostUpWin(d1Repository.getCountHostUpWin(name, begin, end));
        teamInfo.setHostUpDraw(d1Repository.getCountHostUpDraw(name, begin, end));
        teamInfo.setHostDown(d1Repository.getCountHostDown(name, begin, end));
        teamInfo.setHostDownWin(d1Repository.getCountHostDownWin(name, begin, end));
        teamInfo.setHostDownDraw(d1Repository.getCountHostDownDraw(name, begin, end));
        teamInfo.setHostTie(d1Repository.getCountHostTie(name, begin, end));
        teamInfo.setHostTieWin(d1Repository.getCountHostTieWin(name, begin, end));
        teamInfo.setAway(d1Repository.getCountAway(name, begin, end));
        teamInfo.setAwayWin(d1Repository.getCountAwayWin(name, begin, end));
        teamInfo.setAwayDraw(d1Repository.getCountAwayDraw(name, begin, end));
        teamInfo.setAwayUp(d1Repository.getCountAwayUp(name, begin, end));
        teamInfo.setAwayUpWin(d1Repository.getCountAwayUpWin(name, begin, end));
        teamInfo.setAwayUpDraw(d1Repository.getCountAwayUpDraw(name, begin, end));
        teamInfo.setAwayDown(d1Repository.getCountAwayDown(name, begin, end));
        teamInfo.setAwayDownWin(d1Repository.getCountAwayDownWin(name, begin, end));
        teamInfo.setAwayDownDraw(d1Repository.getCountAwayDownDraw(name, begin, end));
        teamInfo.setAwayTie(d1Repository.getCountAwayTie(name, begin, end));
        teamInfo.setAwayTieWin(d1Repository.getCountAwayTieWin(name, begin, end));
        teamInfo.setHostOddsWin(teamInfo.getHostUpWin() + teamInfo.getHostDownWin());
        teamInfo.setAwayOddsWin(teamInfo.getAwayUpWin() + teamInfo.getAwayDownWin());
        return teamInfo;
    }

    public ListDataProvider<TeamInfo> getListData() {
        List<String> names = d1Repository.getTeamBetweenDate(Date.valueOf("2022-8-1"), Date.valueOf("2023-5-31"));
        List<TeamInfo> list = Lists.newArrayList();
        names.forEach(name -> {
            TeamInfo teamInfo = new TeamInfo();
            teamInfo.setName(name);
            Pair p = dateSeason.get("2223");
            teamInfo.setGame(d1Repository.getCountGames(name, (Date) p.getFirst(), (Date) p.getSecond()));
            list.add(teamInfo);
        });
        ListDataProvider listDataProvider = new ListDataProvider<>(list);
        return listDataProvider;
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
        int total = d1Repository.getAHCount(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setTotalMatch(String.valueOf(total));
        int hostWin = d1Repository.getAHCountHostWin(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setHostWin(String.valueOf(hostWin));
        int hostWin2Ball = d1Repository.getAHCountHostWin2Ball(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setHostWin2Ball(String.valueOf(hostWin2Ball));
        int hostWin1Ball = d1Repository.getAHCountHostWin1Ball(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setHostWin1Ball(String.valueOf(hostWin1Ball));
        int draw = d1Repository.getAHCountDraw(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setDraw(String.valueOf(draw));
        int awayWin = d1Repository.getAHCountAwayWin(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setAwayWin(String.valueOf(awayWin));
        int awayWin1Ball = d1Repository.getAHCountAwayWin1Ball(ah, Date.valueOf(begin), Date.valueOf(end));
        probabilityGame.setAwayWin1Ball(String.valueOf(awayWin1Ball));
        int awayWin2Ball = d1Repository.getAHCountAwayWin2Ball(ah, Date.valueOf(begin), Date.valueOf(end));
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
    public List<GameInfo> getGameInfo(String nameTeam, String ah, LocalDate begin, LocalDate end) {
//        if (nameTeam.equals("All") && ah.equals("All")) {
//            return d1Repository.getGamesByDate(Date.valueOf(begin), Date.valueOf(end));
//        } else if (nameTeam.equals("All")) {
//            return d1Repository.getGamesByAHCh(Double.parseDouble(ah), Date.valueOf(begin), Date.valueOf(end));
//        } else if (ah.equals("All")) {
//            return d1Repository.getGamesByTeam(nameTeam, Date.valueOf(begin), Date.valueOf(end));
//        }
//
//        return d1Repository.getGamesByTeamAndAHCh(nameTeam, Double.parseDouble(ah), Date.valueOf(begin), Date.valueOf(end));
        return null;
    }

    @Override
    public ListViewE0.ChanceGame getChance(String ah) {
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

    @Override
    public String getUpProfit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return ListViewE0.getUpProfit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getUp_1_Profit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return ListViewE0.getUp_1_Profit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getUp_2_Profit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return ListViewE0.getUp_2_Profit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getUp_3_Profit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return ListViewE0.getUp_3_Profit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getDownProfit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return ListViewE0.getDownProfit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getDown_1_Profit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return ListViewE0.getDown_1_Profit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getDown_2_Profit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return ListViewE0.getDown_2_Profit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getDown_3_Profit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return ListViewE0.getDown_3_Profit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getHostDrawProfit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return ListViewE0.getHostDrawProfit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getAwayDrawProfit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return ListViewE0.getAwayDrawProfit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public String getHostAwayProfit(String ah, String odds) {
        if (odds.isEmpty() || odds.equals("0")) {
            return "void";
        }
        return ListViewE0.getHostAwayProfit(ah, Float.parseFloat(odds), pgCompute);
    }

    @Override
    public Button getTestButton() {
        Button button = new Button("Test Connection");
        button.addClickListener(event -> {
            log.debug("count = " + d1Repository.count());
            log.debug("Team:" + d1Repository.getTeamBetweenDate(Date.valueOf("2022-8-1"), Date.valueOf("2023-5-31")));
        });
        return button;
    }
}
