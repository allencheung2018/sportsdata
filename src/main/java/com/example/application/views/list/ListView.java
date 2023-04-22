package com.example.application.views.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import lombok.extern.slf4j.Slf4j;

@PageTitle("list")
@Slf4j
public abstract class ListView extends VerticalLayout {

    public ListView() {


        add(getTestButton());
    }

    public abstract Grid getTeamInfoGrid();

    public abstract Button getTestButton();

}
