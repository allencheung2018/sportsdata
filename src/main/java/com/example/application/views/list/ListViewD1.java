package com.example.application.views.list;

import com.example.application.data.entity.ProbabilityGame;
import com.example.application.data.entity.TeamInfo;
import com.example.application.data.repository.D_1Repository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.util.Pair;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@PageTitle("D1 | Sports")
@Route(value = "D1", layout = MainLayout.class)
@Slf4j
public class ListViewD1 extends ListView{

    final private D_1Repository d1Repository;

    public ListViewD1(D_1Repository d1Repository) {
        super();
        this.d1Repository = d1Repository;
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
        return null;
    }

    @Override
    public List<TeamInfo> getAllTeamsInfo(Date begin, Date end) {
        return null;
    }

    @Override
    public List<ProbabilityGame> getProbabilityGameAH(float ah, LocalDate begin, LocalDate end) {
        return null;
    }

    @Override
    public ListViewE0.ChanceGame getChance(String ah) {
        return null;
    }

    @Override
    public String getUpProfit(String ah, String odds) {
        return null;
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
