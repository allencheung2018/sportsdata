package com.example.application.views.list;

import com.example.application.data.entity.GameInfo;
import com.example.application.data.entity.RecordAH;
import com.example.application.data.repository.RecordAHRepository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.sql.Date;

import static com.example.application.data.entity.ProbabilityGame.AsianHandicap;

@PageTitle("RecordAH")
@Route(value = "", layout = MainLayout.class)
@Slf4j
public class RecordAHLayout extends VerticalLayout {

    private final RecordAHRepository repository;
    private DatePicker datePickerBegin = new DatePicker("Begin");
    private DatePicker datePickerEnd = new DatePicker("End");
    private ComboBox<String> comboBoxAH = new ComboBox("AHCh");

    public RecordAHLayout(RecordAHRepository repository) {
        this.repository = repository;
        setWidthFull();
        addDateAndOdds();

        comboBoxAH.setValue(AsianHandicap.get(1));
    }

    private void addDateAndOdds() {
        datePickerBegin.setValue(LocalDate.parse("2023-01-01"));
        datePickerEnd.setValue(LocalDate.now());
        comboBoxAH.setItems(AsianHandicap);
        HorizontalLayout horizontalLayout = new HorizontalLayout(datePickerBegin, datePickerEnd, comboBoxAH);

        Label hostDwon3 = new Label();
        Label hostDwon2 = new Label();
        HorizontalLayout hostLayout = new HorizontalLayout(hostDwon3, hostDwon2);
        Label awayDown3 = new Label();
        Label awayDown2 = new Label();
        HorizontalLayout awayLayout = new HorizontalLayout(awayDown3, awayDown2);

        comboBoxAH.addValueChangeListener(event -> {
            List<GameInfo> list = repository.getRecordByAh(Float.valueOf(comboBoxAH.getValue()),
                    Date.valueOf(datePickerBegin.getValue()), Date.valueOf(datePickerEnd.getValue()));
            log.info("count = " + list.size());
        });

        add(horizontalLayout, hostLayout, awayLayout);
    }
}
