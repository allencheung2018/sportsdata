package com.example.application.views.list;

import com.example.application.data.repository.E_0Repository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;

@PageTitle("E0 | Sports")
@Route(value = "", layout = MainLayout.class)
@Slf4j
public class ListViewE0 extends ListView{

    @Autowired
    private E_0Repository e0Repository;

    public ListViewE0() {
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
            log.debug("count = " + e0Repository.count());
            log.debug("Team:" + e0Repository.getTeamBetweenDate(Date.valueOf("2022-8-1"), Date.valueOf("2023-5-31")));
        });
        return button;
    }
}
