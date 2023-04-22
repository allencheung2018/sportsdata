package com.example.application.views.list;

import com.example.application.data.repository.E0Repository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("list")
@Route(value = "")
@Slf4j
public class ListView extends VerticalLayout {
    @Autowired
    private E0Repository e0Repository;

    public ListView() {


        Button button = new Button("Test Connection");
        button.addClickListener(event -> {
            log.debug("count = "+e0Repository.count());
        });

        add(button);
    }


}
