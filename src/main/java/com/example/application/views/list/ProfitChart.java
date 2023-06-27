package com.example.application.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProfitChart extends VerticalLayout {
    private final List<LocalDate> xAxis;
    private final HashMap<String, List<Float>> profits;

    public ProfitChart(List<LocalDate> xAxis, HashMap<String, List<Float>> profits) {
        this.xAxis = xAxis;
        this.profits = profits;

        add(getChart());
    }

    protected Component getChart() {
        Chart chart = new Chart();
        chart.setSizeFull();

        Configuration configuration = chart.getConfiguration();
        configuration.getChart().setType(ChartType.LINE);

        configuration.getTitle().setText("Asian Handicap Profit Estimation");

        configuration.getxAxis().setCategories(xAxis.stream().map(LocalDate::toString).collect(Collectors.toList()).toArray(new String[0]));

        YAxis yAxis = configuration.getyAxis();

        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.getDataLabels().setEnabled(true);
        configuration.setPlotOptions(plotOptions);

        Legend legend = configuration.getLegend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setAlign(HorizontalAlign.RIGHT);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setBorderWidth(0);

        ListSeries ls = new ListSeries();
        for (String key : profits.keySet()) {
            ls.setName(key);
            ls.setData(profits.get(key).toArray(new Number[0]));
            configuration.addSeries(ls);
        }

        chart.drawChart();
        return chart;
    }
}
