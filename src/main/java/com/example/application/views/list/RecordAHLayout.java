package com.example.application.views.list;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

import static com.example.application.data.entity.ProbabilityGame.AsianHandicap;

@PageTitle("RecordAH")
@Route(value = "", layout = MainLayout.class)
public class RecordAHLayout extends VerticalLayout {

    private DatePicker datePickerBegin = new DatePicker("Begin");
    private DatePicker datePickerEnd = new DatePicker("End");
    private ComboBox<String> comboBoxAH = new ComboBox("AHCh");

    public RecordAHLayout() {
        setWidthFull();
        addDateAndOdds();
    }

    private void addDateAndOdds() {
        datePickerBegin.setValue(LocalDate.parse("2023-01-01"));
        datePickerEnd.setValue(LocalDate.now());
        comboBoxAH.setItems(AsianHandicap);
        HorizontalLayout horizontalLayout = new HorizontalLayout(datePickerBegin, datePickerEnd, comboBoxAH);
        add(horizontalLayout);
    }
}
