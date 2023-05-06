package com.example.application.views.list;

import com.example.application.data.entity.GameInfo;
import com.example.application.data.entity.ProbabilityGame;
import com.example.application.data.entity.TeamInfo;
import com.example.application.data.repository.E_1Repository;
import com.example.application.data.service.DataService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.util.Pair;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.application.views.list.ListViewE0.*;


@PageTitle("E2 | Sports")
@Route(value = "E2", layout = MainLayout.class)
@Slf4j
public class ListViewE1 extends ListView {

    final static private String nameLeague = "England Championship";
    final private E_1Repository repository;
    Pair<Date, Date> pair;
    List<String> teamNames;

    private float ah = 0;
    private ProbabilityGameCompute pgCompute = new ProbabilityGameCompute();


    public ListViewE1(E_1Repository repository, DataService dataService) {
        super();
        this.repository = repository;
        addLeagueName();
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
        teamNames = repository.getTeamBetweenDate(pair.getFirst(), pair.getSecond());
    }

    public TeamInfo searchTeamInfo(String name, Date begin, Date end) {
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setName(name);
        teamInfo.setGame(repository.getCountGames(name, begin, end));
        teamInfo.setHost(repository.getCountHost(name, begin, end));
        teamInfo.setHostWin(repository.getCountHostWin(name, begin, end));
        teamInfo.setHostDraw(repository.getCountHostDraw(name, begin, end));
        teamInfo.setHostUp(repository.getCountHostUp(name, begin, end));
        teamInfo.setHostUpWin(repository.getCountHostUpWin(name, begin, end));
        teamInfo.setHostUpDraw(repository.getCountHostUpDraw(name, begin, end));
        teamInfo.setHostDown(repository.getCountHostDown(name, begin, end));
        teamInfo.setHostDownWin(repository.getCountHostDownWin(name, begin, end));
        teamInfo.setHostDownDraw(repository.getCountHostDownDraw(name, begin, end));
        teamInfo.setHostTie(repository.getCountHostTie(name, begin, end));
        teamInfo.setHostTieWin(repository.getCountHostTieWin(name, begin, end));
        teamInfo.setAway(repository.getCountAway(name, begin, end));
        teamInfo.setAwayWin(repository.getCountAwayWin(name, begin, end));
        teamInfo.setAwayDraw(repository.getCountAwayDraw(name, begin, end));
        teamInfo.setAwayUp(repository.getCountAwayUp(name, begin, end));
        teamInfo.setAwayUpWin(repository.getCountAwayUpWin(name, begin, end));
        teamInfo.setAwayUpDraw(repository.getCountAwayUpDraw(name, begin, end));
        teamInfo.setAwayDown(repository.getCountAwayDown(name, begin, end));
        teamInfo.setAwayDownWin(repository.getCountAwayDownWin(name, begin, end));
        teamInfo.setAwayDownDraw(repository.getCountAwayDownDraw(name, begin, end));
        teamInfo.setAwayTie(repository.getCountAwayTie(name, begin, end));
        teamInfo.setAwayTieWin(repository.getCountAwayTieWin(name, begin, end));
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

        List<ProbabilityGame> list = Lists.newArrayList();
        list.addAll(getProbabilityGameByAHCh(ah - 0.25, begin, end));
        list.addAll(getProbabilityGameByAHCh(ah, begin, end));
        list.addAll(getProbabilityGameByAHCh(ah + 0.25, begin, end));

        return list;
    }

    public List<ProbabilityGame> getProbabilityGameByAHCh(double ah, LocalDate begin, LocalDate end) {

        Object o = repository.getGameInfoByAHCh(ah, Date.valueOf(begin), Date.valueOf(end));
        List<String> list = Arrays.stream(((Object[])o)).map(String::valueOf).collect(Collectors.toList());
        ProbabilityGame probabilityGame = new ProbabilityGame(list.get(0), list.get(1), list.get(2), list.get(3),
                list.get(4), list.get(5), list.get(6), list.get(7));
        probabilityGame.setAh(String.valueOf(ah));

        float total = Float.parseFloat(probabilityGame.getTotalMatch());
        ProbabilityGame probabilityPercentage = new ProbabilityGame();
        float totalMatch = total / total;
        probabilityPercentage.setTotalMatch(String.format("%.1f", totalMatch * 100));
        float hostWinRate = Float.parseFloat(probabilityGame.getHostWin()) / total;
        probabilityPercentage.setHostWin(String.format("%.1f", hostWinRate * 100));
        float hostWin1BallRate = Float.parseFloat(probabilityGame.getHostWin1Ball()) / total;
        probabilityPercentage.setHostWin1Ball(String.format("%.1f", hostWin1BallRate * 100));
        float hostWin2BallRate = Float.parseFloat(probabilityGame.getHostWin2Ball())  / total;
        probabilityPercentage.setHostWin2Ball(String.format("%.1f", hostWin2BallRate * 100));
        float drawRate = Float.parseFloat(probabilityGame.getDraw())  / total;
        probabilityPercentage.setDraw(String.format("%.1f", drawRate * 100));
        float awayWinRate = Float.parseFloat(probabilityGame.getAwayWin())  / total;
        probabilityPercentage.setAwayWin(String.format("%.1f", awayWinRate * 100));
        float awayWin1BallRate = Float.parseFloat(probabilityGame.getAwayWin1Ball())  / total;
        probabilityPercentage.setAwayWin1Ball(String.format("%.1f", awayWin1BallRate * 100));
        float awayWin2BallRate = Float.parseFloat(probabilityGame.getAwayWin2Ball())  / total;
        probabilityPercentage.setAwayWin2Ball(String.format("%.1f", awayWin2BallRate * 100));

        if (ah == this.ah) {
            pgCompute.setTotalMatch(totalMatch);
            pgCompute.setHostWin(hostWinRate);
            pgCompute.setHostWin1Ball(hostWin1BallRate);
            pgCompute.setHostWin2Ball(hostWin2BallRate);
            pgCompute.setDraw(drawRate);
            pgCompute.setAwayWin(awayWinRate);
            pgCompute.setAwayWin1Ball(awayWin1BallRate);
            pgCompute.setAwayWin2Ball(awayWin2BallRate);
        }

        return List.of(probabilityGame, probabilityPercentage);
    }

    @Override
    public List<GameInfo> getGameInfo(String nameTeam, String ah, LocalDate begin, LocalDate end) {
        if (nameTeam.equals("All") && ah.equals("All")) {
            return repository.getGamesByDate(Date.valueOf(begin), Date.valueOf(end));
        } else if (nameTeam.equals("All")) {
            return repository.getGamesByAHCh(Double.parseDouble(ah), Date.valueOf(begin), Date.valueOf(end));
        } else if (ah.equals("All")) {
            return repository.getGamesByTeam(nameTeam, Date.valueOf(begin), Date.valueOf(end));
        }
        return repository.getGamesByTeamAndAHCh(nameTeam, Double.parseDouble(ah), Date.valueOf(begin), Date.valueOf(end));
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
            log.debug("count = " + repository.getAHCount(-0.25, pair.getFirst(), pair.getSecond()));
            log.debug("Team:" + repository.getTeamBetweenDate(Date.valueOf("2022-8-1"), Date.valueOf("2023-5-31")));
        });
        return button;
    }
}
