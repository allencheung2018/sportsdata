package com.example.application.views.list;

import com.example.application.data.entity.RecordInfo;
import com.example.application.data.repository.RecordAHRepository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static com.example.application.data.entity.ProbabilityGame.AsianHandicap;
import static com.example.application.data.entity.ProbabilityGame.GoalLine;
import static com.example.application.data.entity.RecordInfo.nameColumns;

@PageTitle("RecordAH")
@Route(value = "", layout = MainLayout.class)
@Slf4j
public class RecordAHLayout extends VerticalLayout {

    public static final String ALL = "All";
    public static final String H_3 = "H-3";
    public static final String H_2 = "H-2";
    public static final String H_1 = "H-1";
    public static final String H_0 = "H-0";
    public static final String A_3 = "A-3";
    public static final String A_2 = "A-2";
    public static final String A_1 = "A-1";
    public static final String A_0 = "A-0";
    public static final String O_3 = "O-3";
    public static final String O_2 = "O-2";
    public static final String O_1 = "O-1";
    public static final String O_0 = "O-0";
    public static final String U_3 = "U-3";
    public static final String U_2 = "U-2";
    public static final String U_1 = "U-1";
    public static final String U_0 = "U-0";
    private final RecordAHRepository repository;
    private DatePicker datePickerBegin = new DatePicker("Begin");
    private DatePicker datePickerEnd = new DatePicker("End");
    private ComboBox<String> comboBoxAH = new ComboBox("AHCh");
    private ComboBox<String> comboBoxGL = new ComboBox("GL");
    private Grid<RecordInfo> recordInfoGrid;
    private RecordForm form;

    public RecordAHLayout(RecordAHRepository repository) {
        this.repository = repository;
        setSizeFull();
        configureForm();
        addDateAndOdds();

        comboBoxAH.setValue(AsianHandicap.get(1));
        closeEditor();
    }

    private Label hostDwon3 = new Label();
    private Label countHD3 = new Label();
    private Label hostDwon2 = new Label();
    private Label countHD2 = new Label();
    private Label hostDwon1 = new Label();
    private Label countHD1 = new Label();
    private Label host0 = new Label();
    private Label countH0 = new Label();
    private Label awayDown3 = new Label();
    private Label countAD3 = new Label();
    private Label awayDown2 = new Label();
    private Label countAD2 = new Label();
    private Label awayDown1 = new Label();
    private Label countAD1 = new Label();
    private Label away0 = new Label();
    private Label countA0 = new Label();
    private Label goalOver3 = new Label();
    private Label countGO3 = new Label();
    private Label goalOver2 = new Label();
    private Label countGO2 = new Label();
    private Label goalOver1 = new Label();
    private Label countGO0 = new Label();
    private Label goalOver0 = new Label();
    private Label countGO1 = new Label();
    private Label goalUnder3 = new Label();
    private Label countGU3 = new Label();
    private Label goalUnder2 = new Label();
    private Label countGU2 = new Label();
    private Label goalUnder1 = new Label();
    private Label countGU1 = new Label();
    private Label goalUnder0 = new Label();
    private Label countGU0 = new Label();
    private FooterRow.FooterCell footerCellCost;
    private HorizontalLayout hostLayout;
    private HorizontalLayout awayLayout;
    private HorizontalLayout goalOverLayout;
    private HorizontalLayout goalUnderLayout;
    private ProfitChart profitChart;

    private void addDateAndOdds() {
        datePickerBegin.setValue(LocalDate.parse("2023-01-01"));
        datePickerBegin.setWidth("40mm");
        datePickerBegin.addValueChangeListener(event -> updateData());
        datePickerEnd.setValue(LocalDate.now());
        datePickerEnd.setWidth("40mm");
        datePickerEnd.addValueChangeListener(event -> updateData());

        Collection<String> collection = new ArrayList<>();
        collection.addAll(AsianHandicap);
        collection.add(ALL);
        comboBoxAH.setItems(collection);
        comboBoxAH.setWidth("30mm");
        Collection<String> gls = new ArrayList<>();
        gls.addAll(GoalLine);
        gls.add(ALL);
        comboBoxGL.setItems(gls);
        comboBoxGL.setWidth("30mm");
        Button newButton = new Button("New");
        newButton.setWidth("20mm");
        newButton.addClickListener(event -> addRecord());
        Button collapseButton = new Button("Collapse");
        collapseButton.addClickListener(event -> showEstimationArea(collapseButton));
        Button chartPfButton = new Button("Profit Trends");
        chartPfButton.addClickListener(event -> showChartProfitTrends());
        HorizontalLayout horizontalLayout = new HorizontalLayout(datePickerBegin, datePickerEnd, comboBoxAH, comboBoxGL,
                newButton, collapseButton, chartPfButton);
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        hostLayout = new HorizontalLayout(hostDwon3, countHD3, hostDwon2, countHD2, hostDwon1, countHD1, host0, countH0);
        awayLayout = new HorizontalLayout(awayDown3, countAD3, awayDown2, countAD2, awayDown1, countAD1, away0, countA0);
        goalOverLayout = new HorizontalLayout(goalOver3, countGO3, goalOver2, countGO2, goalOver1, countGO1, goalOver0, countGO0);
        goalUnderLayout = new HorizontalLayout(goalUnder3, countGU3, goalUnder2, countGU2, goalUnder1, countGU1, goalUnder0, countGU0);

        recordInfoGrid = createRecordInfoGrid("RecordGrid");
        FooterRow footer = recordInfoGrid.prependFooterRow();
        footerCellCost = footer.getCell(recordInfoGrid.getColumnByKey("matchDate"));

        comboBoxAH.addValueChangeListener(event -> {
            if (null == comboBoxGL.getValue()) {
                comboBoxGL.setValue(ALL);
            }
            updateData();
        });
        comboBoxGL.addValueChangeListener(event -> updateData());
        add(horizontalLayout, hostLayout, awayLayout, goalOverLayout, goalUnderLayout);

        HorizontalLayout content = new HorizontalLayout(recordInfoGrid, form);
        content.setFlexGrow(4, recordInfoGrid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();
        add(content);
    }

    public void showEstimationArea(Button button) {
        if (hostLayout.isVisible()) {
            hostLayout.setVisible(false);
            awayLayout.setVisible(false);
            goalOverLayout.setVisible(false);
            goalUnderLayout.setVisible(false);
            button.setText("Show");
        } else {
            hostLayout.setVisible(true);
            awayLayout.setVisible(true);
            goalOverLayout.setVisible(true);
            goalUnderLayout.setVisible(true);
            button.setText("Collapse");
        }
    }

    public void showChartProfitTrends() {
        profitChart.setWidth("80%");
        UI.getCurrent().add(profitChart);
    }

    private void updateData() {
        List<RecordInfo> list;
        float ah = 0;
        float gl = 2.5f;
        if (comboBoxAH.getValue().equals(ALL) && comboBoxGL.getValue().equals(ALL)) {
            list = repository.getRecordByDate(Date.valueOf(datePickerBegin.getValue()), Date.valueOf(datePickerEnd.getValue()));
            ah = 0;
        } else if (comboBoxAH.getValue().equals(ALL)) {
            gl = Float.parseFloat(comboBoxGL.getValue());
            list = repository.getRecordByGL(gl, Date.valueOf(datePickerBegin.getValue()), Date.valueOf(datePickerEnd.getValue()));
        } else if (comboBoxGL.getValue().equals(ALL)) {
            ah = Float.valueOf(comboBoxAH.getValue());
            list = repository.getRecordByAh(ah, Date.valueOf(datePickerBegin.getValue()), Date.valueOf(datePickerEnd.getValue()));
        } else {
            ah = Float.valueOf(comboBoxAH.getValue());
            gl = Float.parseFloat(comboBoxGL.getValue());
            list = repository.getRecordByAhAndGL(ah, gl, Date.valueOf(datePickerBegin.getValue()), Date.valueOf(datePickerEnd.getValue()));
        }
        recordInfoGrid.setItems(list);
        footerCellCost.setText("Rows : " + list.size());

        HashMap<String, List<Float>> profits = new HashMap<>();
        List<Float> values = new ArrayList<>();
        ProfitEstimationAH profit = getProfit(list, ah, H_3, values);
        hostDwon3.setText("主降3= " + String.format("%.1f", profit.getProfit()));
        hostDwon3.setWidth("25mm");
        countHD3.setText("W=" + profit.getWin() + " HW=" + profit.hWin + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countHD3.setWidth("60mm");
        profits.put(H_3, new ArrayList<>(values));

        values.clear();
        profit = getProfit(list, ah, H_2, values);
        hostDwon2.setText("主降2= " + String.format("%.1f", profit.getProfit()));
        hostDwon2.setWidth("25mm");
        countHD2.setText("W=" + profit.getWin()
                + " HW=" + profit.hWin + " P=" + profit.getDraw() + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countHD2.setWidth("60mm");
        profits.put(H_2, new ArrayList<>(values));

        values.clear();
        profit = getProfit(list, ah, H_1, values);
        hostDwon1.setText("主降1= " + String.format("%.1f", profit.getProfit()));
        hostDwon1.setWidth("25mm");
        countHD1.setText("W=" + profit.getWin()
                + " HW=" + profit.getHWin() + " P=" + profit.getDraw() + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countHD1.setWidth("60mm");
        profits.put(H_1, new ArrayList<>(values));

        values.clear();
        profit = getProfit(list, ah, H_0, values);
        host0.setText("主0= " + String.format("%.1f", profit.getProfit()));
        host0.setWidth("25mm");
        countH0.setText("W=" + profit.getWin()
                + " HW=" + profit.getHWin() + " P=" + profit.getDraw() + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countH0.setWidth("60mm");
        profits.put(H_0, new ArrayList<>(values));

        values.clear();
        profit = getProfit(list, ah, A_3, values);
        awayDown3.setText("客降3= " + String.format("%.1f", profit.getProfit()));
        awayDown3.setWidth("25mm");
        countAD3.setText("W=" + profit.getWin()
                + " HW=" + profit.hWin + " P=" + profit.getDraw() + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countAD3.setWidth("60mm");
        profits.put(A_3, new ArrayList<>(values));

        values.clear();
        profit = getProfit(list, ah, A_2, values);
        awayDown2.setText("客降2= " + String.format("%.1f", profit.getProfit()));
        awayDown2.setWidth("25mm");
        countAD2.setText("W=" + profit.getWin()
                + " HW=" + profit.hWin + " P=" + profit.getDraw() + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countAD2.setWidth("60mm");
        profits.put(A_2, new ArrayList<>(values));

        values.clear();
        profit = getProfit(list, ah, A_1, values);
        awayDown1.setText("客降1= " + String.format("%.1f", profit.getProfit()));
        awayDown1.setWidth("25mm");
        countAD1.setText("W=" + profit.getWin()
                + " HW=" + profit.getHWin() + " P=" + profit.getDraw() + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countAD1.setWidth("60mm");
        profits.put(A_1, new ArrayList<>(values));

        values.clear();
        profit = getProfit(list, ah, A_0, values);
        away0.setText("客0= " + String.format("%.1f", profit.getProfit()));
        away0.setWidth("25mm");
        countA0.setText("W=" + profit.getWin()
                + " HW=" + profit.getHWin() + " P=" + profit.getDraw() + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countA0.setWidth("60mm");
        profits.put(A_0, new ArrayList<>(values));

        values.clear();
        profit = getGoalLineEstimation(list, gl, O_3, values);
        goalOver3.setText("大-3= " + String.format("%.1f", profit.getProfit()));
        goalOver3.setWidth("25mm");
        countGO3.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGO3.setWidth("60mm");
        profits.put(O_3, new ArrayList<>(values));

        values.clear();
        profit = getGoalLineEstimation(list, gl, O_2, values);
        goalOver2.setText("大-2= " + String.format("%.1f", profit.getProfit()));
        goalOver2.setWidth("25mm");
        countGO2.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGO2.setWidth("60mm");
        profits.put(O_2, new ArrayList<>(values));

        values.clear();
        profit = getGoalLineEstimation(list, gl, O_1, values);
        goalOver1.setText("大-1= " + String.format("%.1f", profit.getProfit()));
        goalOver1.setWidth("25mm");
        countGO1.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGO1.setWidth("60mm");
        profits.put(O_1, new ArrayList<>(values));

        values.clear();
        profit = getGoalLineEstimation(list, gl, O_0, values);
        goalOver0.setText("大= " + String.format("%.1f", profit.getProfit()));
        goalOver0.setWidth("25mm");
        countGO0.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGO0.setWidth("60mm");
        profits.put(O_0, new ArrayList<>(values));

        values.clear();
        profit = getGoalLineEstimation(list, gl, U_3, values);
        goalUnder3.setText("小-3= " + String.format("%.1f", profit.getProfit()));
        goalUnder3.setWidth("25mm");
        countGU3.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGU3.setWidth("60mm");
        profits.put(U_3, new ArrayList<>(values));

        values.clear();
        profit = getGoalLineEstimation(list, gl, U_2, values);
        goalUnder2.setText("小-2= " + String.format("%.1f", profit.getProfit()));
        goalUnder2.setWidth("25mm");
        countGU2.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGU2.setWidth("60mm");
        profits.put(U_2, new ArrayList<>(values));

        values.clear();
        profit = getGoalLineEstimation(list, gl, U_1, values);
        goalUnder1.setText("小-1= " + String.format("%.1f", profit.getProfit()));
        goalUnder1.setWidth("25mm");
        countGU1.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGU1.setWidth("60mm");
        profits.put(U_1, new ArrayList<>(values));

        values.clear();
        profit = getGoalLineEstimation(list, gl, U_0, values);
        goalUnder0.setText("小= " + String.format("%.1f", profit.getProfit()));
        goalUnder0.setWidth("25mm");
        countGU0.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGU0.setWidth("60mm");
        profits.put(U_0, new ArrayList<>(values));

        profitChart = new ProfitChart(list.stream().map(RecordInfo::getMatchDate).toList(), profits);
    }

    private Grid<RecordInfo> createRecordInfoGrid(String name) {
        Grid<RecordInfo> grid = new Grid<>(RecordInfo.class);
        grid.addClassName(name);
//        grid.setHeightFull();
        grid.setSizeFull();
        grid.setColumns(nameColumns);
        grid.getColumnByKey("homeGoal").setHeader("HG").setAutoWidth(true);
        grid.getColumnByKey("awayGoal").setHeader("AG").setAutoWidth(true);
        grid.getColumnByKey("goalLine").setHeader("GL").setAutoWidth(true);

        grid.asSingleSelect().addValueChangeListener(event -> editRecord(event.getValue()));

        return grid;
    }

    private void configureForm() {
        form = new RecordForm();
//        form.setWidth("30mm");
        form.addSaveListener(this::saveRecord);
        form.addDeletedListener(this::deleteRecord);
        form.addCloseEvnet(e -> closeEditor());
    }

    private void editRecord(RecordInfo recordInfo) {
        if (recordInfo == null) {
            closeEditor();
        } else {
            form.setRecordInfo(recordInfo);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveRecord(RecordForm.SaveEvent event) {
        repository.save(event.getRecordAH());
        closeEditor();
        updateData();
    }

    private void deleteRecord(RecordForm.DeleteEvent event) {
        repository.delete(event.getRecordAH());
        closeEditor();
        updateData();
    }

    private void closeEditor() {
        form.setRecordInfo(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addRecord() {
        recordInfoGrid.asSingleSelect().clear();
        editRecord(new RecordInfo(LocalDate.now(), LocalDate.now()));
    }

    private ProfitEstimationAH getProfit(List<RecordInfo> records, float ah, String direction, List<Float> values) {
        float downAH = 0;
        int win = 0;
        int hWin = 0;
        int draw = 0;
        int hLose = 0;
        int lose = 0;
        float profit = 0;
        float diff = 0;

        for (int i = 0; i < records.size(); i++) {
            RecordInfo record = records.get(i);
            if (direction.equals(H_3)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    downAH = ah + 3 * 0.25f;
                    diff = record.getHomeGoal() - record.getAwayGoal() + downAH;
                }
            } else if (direction.equals(H_2)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    downAH = ah + 2 * 0.25f;
                    diff = record.getHomeGoal() - record.getAwayGoal() + downAH;
                }
            } else if (direction.equals(H_1)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    downAH = ah + 0.25f;
                    diff = record.getHomeGoal() - record.getAwayGoal() + downAH;
                }
            } else if (direction.equals(H_0)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    diff = record.getHomeGoal() - record.getAwayGoal() + ah;
                }
            } else if (direction.equals(A_3)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    downAH = -ah + 3 * 0.25f;
                    diff = record.getAwayGoal() - record.getHomeGoal() + downAH;
                }

            } else if (direction.equals(A_2)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    downAH = -ah + 2 * 0.25f;
                    diff = record.getAwayGoal() - record.getHomeGoal() + downAH;
                }
            } else if (direction.equals(A_1)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    downAH = -ah + 0.25f;
                    diff = record.getAwayGoal() - record.getHomeGoal() + downAH;
                }
            } else if (direction.equals(A_0)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    diff = record.getAwayGoal() - record.getHomeGoal() - ah;
                }
            } else {
                profit += record.getProfit();
            }

            if (diff > 0.25) {
                win += 1;
            } else if (diff == 0.25) {
                hWin += 1;
            } else if (diff == 0) {
                draw += 1;
            } else if (diff == -0.25) {
                hLose += 1;
            } else if (diff < -0.25) {
                lose += 1;
            }

            if (direction.equals(H_3) || direction.equals(A_3)) {
                profit = 0.35f * win + 0.175f * hWin - 0.5f * hLose - lose;
                values.add(profit);
            } else if (direction.equals(H_2) || direction.equals(A_2)) {
                profit = 0.5f * win + 0.25f * hWin - 0.5f * hLose - lose;
                values.add(profit);
            } else if (direction.equals(H_1) || direction.equals(A_1)) {
                profit = 0.65f * win + 0.325f * hWin - 0.5f * hLose - lose;
                values.add(profit);
            } else if (direction.equals(H_0) || direction.equals(A_0)) {
                profit = 0.925f * win + 0.4625f * hWin - 0.5f * hLose - lose;
                values.add(profit);
            }
        }
        return new ProfitEstimationAH(win, hWin, draw, hLose, lose, profit);
    }

    public ProfitEstimationAH getGoalLineEstimation(List<RecordInfo> records, float goalLine, String direction, List<Float> values) {
        int win = 0;
        int hWin = 0;
        int draw = 0;
        int hLose = 0;
        int lose = 0;
        float profit = 0;
        float goal;
        float diff = 0;

        for (int i = 0; i < records.size(); i++) {
            RecordInfo record = records.get(i);
            if (direction.equals(O_3)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goal - goalLine + 3 * 0.25f;
                }
            } else if (direction.equals(U_3)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goalLine - goal + 3 * 0.25f;
                }
            } else if (direction.equals(O_2)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goal - goalLine + 2 * 0.25f;
                }
            } else if (direction.equals(U_2)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goalLine - goal + 2 * 0.25f;
                }
            } else if (direction.equals(O_1)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goal - goalLine + 0.25f;
                }
            } else if (direction.equals(U_1)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goalLine - goal + 0.25f;
                }
            } else if (direction.equals(O_0)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goal - goalLine;
                }
            } else if (direction.equals(U_0)) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goalLine - goal;
                }
            }

            if (diff > 0.25) {
                win += 1;
            } else if (diff == 0.25) {
                hWin += 1;
            } else if (diff == 0) {
                draw += 1;
            } else if (diff == -0.25) {
                hLose += 1;
            } else if (diff < -0.25) {
                lose += 1;
            }

            if (direction.equals(O_3) || direction.equals(U_3)) {
                profit = 0.35f * win + 0.175f * hWin - 0.5f * hLose - lose;
                values.add(profit);
            } else if (direction.equals(O_2) || direction.equals(U_2)) {
                profit = 0.5f * win + 0.25f * hWin - 0.5f * hLose - lose;
                values.add(profit);
            } else if (direction.equals(O_1) || direction.equals(U_1)) {
                profit = 0.65f * win + 0.325f * hWin - 0.5f * hLose - lose;
                values.add(profit);
            } else if (direction.equals(O_0) || direction.equals(U_0)) {
                profit = 0.925f * win + 0.4625f * hWin - 0.5f * hLose - lose;
                values.add(profit);
            }
        }
        return new ProfitEstimationAH(win, hWin, draw, hLose, lose, profit);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class ProfitEstimationAH {
        private int win;
        private int hWin;
        private int draw;
        private int hLose;
        private int lose;
        private float profit;
    }
}
