package com.example.application.views.list;

import com.example.application.data.repository.D_1Repository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;

@PageTitle("D1 | Sports")
@Route(value = "D1", layout = MainLayout.class)
@Slf4j
public class ListViewD1 extends ListView{

    @Autowired
    private D_1Repository d1Repository;

    public ListViewD1() {
        super();
    }

    @Override
    public Grid getTeamInfoGrid() {
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
