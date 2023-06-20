package com.example.application.views.list;

import com.example.application.data.entity.RecordInfo;
import com.example.application.data.repository.RecordAHRepository;
import com.example.application.views.MainLayout;
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
import java.util.List;

import static com.example.application.data.entity.ProbabilityGame.AsianHandicap;
import static com.example.application.data.entity.ProbabilityGame.GoalLine;
import static com.example.application.data.entity.RecordInfo.nameColumns;

@PageTitle("RecordAH")
@Route(value = "", layout = MainLayout.class)
@Slf4j
public class RecordAHLayout extends VerticalLayout {

    public static final String ALL = "All";
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
    private Label awayDown3 = new Label();
    private Label countAD3 = new Label();
    private Label awayDown2 = new Label();
    private Label countAD2 = new Label();
    private Label awayDown1 = new Label();
    private Label countAD1 = new Label();
    private Label goalOver3 = new Label();
    private Label countGO3 = new Label();
    private Label goalOver2 = new Label();
    private Label countGO2 = new Label();
    private Label goalOver1 = new Label();
    private Label countGO1 = new Label();
    private Label goalUnder3 = new Label();
    private Label countGU3 = new Label();
    private Label goalUnder2 = new Label();
    private Label countGU2 = new Label();
    private Label goalUnder1 = new Label();
    private Label countGU1 = new Label();
    private FooterRow.FooterCell footerCellCost;
    private HorizontalLayout hostLayout;
    private HorizontalLayout awayLayout;
    private HorizontalLayout goalOverLayout;
    private HorizontalLayout goalUnderLayout;

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
        HorizontalLayout horizontalLayout = new HorizontalLayout(datePickerBegin, datePickerEnd, comboBoxAH, comboBoxGL,
                newButton, collapseButton);
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        hostLayout = new HorizontalLayout(hostDwon3, countHD3, hostDwon2, countHD2, hostDwon1, countHD1);
        awayLayout = new HorizontalLayout(awayDown3, countAD3, awayDown2, countAD2, awayDown1, countAD1);
        goalOverLayout = new HorizontalLayout(goalOver3, countGO3, goalOver2, countGO2, goalOver1, countGO1);
        goalUnderLayout = new HorizontalLayout(goalUnder3, countGU3, goalUnder2, countGU2, goalUnder1, countGU1);

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
        ProfitEstimationAH profit = getProfit(list, ah, "Host-3");
        hostDwon3.setText("主降3= " + String.format("%.1f", profit.getProfit()));
        hostDwon3.setWidth("25mm");
        countHD3.setText("W=" + profit.getWin() + " HW=" + profit.hWin + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countHD3.setWidth("60mm");
        profit = getProfit(list, ah, "Host-2");
        hostDwon2.setText("主降2= " + String.format("%.1f", profit.getProfit()));
        hostDwon2.setWidth("25mm");
        countHD2.setText("W=" + profit.getWin()
                + " HW=" + profit.hWin + " P=" + profit.getDraw() + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countHD2.setWidth("60mm");
        profit = getProfit(list, ah, "Host-1");
        hostDwon1.setText("主降1= " + String.format("%.1f", profit.getProfit()));
        hostDwon1.setWidth("25mm");
        countHD1.setText("W=" + profit.getWin()
                + " HW=" + profit.getHWin() + " P=" + profit.getDraw() + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countHD1.setWidth("60mm");
        profit = getProfit(list, ah, "Away-3");
        awayDown3.setText("客降3= " + String.format("%.1f", profit.getProfit()));
        awayDown3.setWidth("25mm");
        countAD3.setText("W=" + profit.getWin()
                + " HW=" + profit.hWin + " P=" + profit.getDraw() + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countAD3.setWidth("60mm");
        profit = getProfit(list, ah, "Away-2");
        awayDown2.setText("客降2= " + String.format("%.1f", profit.getProfit()));
        awayDown2.setWidth("25mm");
        countAD2.setText("W=" + profit.getWin()
                + " HW=" + profit.hWin + " P=" + profit.getDraw() + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countAD2.setWidth("60mm");
        profit = getProfit(list, ah, "Away-1");
        awayDown1.setText("客降1= " + String.format("%.1f", profit.getProfit()));
        awayDown1.setWidth("25mm");
        countAD1.setText("W=" + profit.getWin()
                + " HW=" + profit.getHWin() + " P=" + profit.getDraw() + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countAD1.setWidth("60mm");

        profit = getGoalLineEstimation(list, gl - 3 * 0.25f, "GO-3");
        goalOver3.setText("大-3= " + String.format("%.1f", profit.getProfit()));
        goalOver3.setWidth("25mm");
        countGO3.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGO3.setWidth("60mm");
        profit = getGoalLineEstimation(list, gl - 2 * 0.25f, "GO-2");
        goalOver2.setText("大-2= " + String.format("%.1f", profit.getProfit()));
        goalOver2.setWidth("25mm");
        countGO2.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGO2.setWidth("60mm");
        profit = getGoalLineEstimation(list, gl - 0.25f, "GO-1");
        goalOver1.setText("大-1= " + String.format("%.1f", profit.getProfit()));
        goalOver1.setWidth("25mm");
        countGO1.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGO1.setWidth("60mm");
        profit = getGoalLineEstimation(list, gl + 3 * 0.25f, "GU-3");
        goalUnder3.setText("小-3= " + String.format("%.1f", profit.getProfit()));
        goalUnder3.setWidth("25mm");
        countGU3.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGU3.setWidth("60mm");
        profit = getGoalLineEstimation(list, gl + 2 * 0.25f, "GU-2");
        goalUnder2.setText("小-2= " + String.format("%.1f", profit.getProfit()));
        goalUnder2.setWidth("25mm");
        countGU2.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGU2.setWidth("60mm");
        profit = getGoalLineEstimation(list, gl + 0.25f, "GU-1");
        goalUnder1.setText("小-1= " + String.format("%.1f", profit.getProfit()));
        goalUnder1.setWidth("25mm");
        countGU1.setText("W=" + profit.getWin() + " HW=" + profit.getHWin() + " P=" + profit.getDraw()
                + " HL=" + profit.getHLose() + " L=" + profit.getLose());
        countGU1.setWidth("60mm");
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

    private ProfitEstimationAH getProfit(List<RecordInfo> records, float ah, String direction) {
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
            if (direction.equals("Host-3")) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    downAH = ah + 3 * 0.25f;
                    diff = record.getHomeGoal() - record.getAwayGoal() + downAH;
                }
            } else if (direction.equals("Host-2")) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    downAH = ah + 2 * 0.25f;
                    diff = record.getHomeGoal() - record.getAwayGoal() + downAH;
                }
            } else if (direction.equals("Host-1")) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    downAH = ah + 0.25f;
                    diff = record.getHomeGoal() - record.getAwayGoal() + downAH;
                }
            } else if (direction.equals("Away-3")) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    downAH = -ah + 3 * 0.25f;
                    diff = record.getAwayGoal() - record.getHomeGoal() + downAH;
                }

            } else if (direction.equals("Away-2")) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    downAH = -ah + 2 * 0.25f;
                    diff = record.getAwayGoal() - record.getHomeGoal() + downAH;
                }
            } else if (direction.equals("Away-1")) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    downAH = -ah + 0.25f;
                    diff = record.getAwayGoal() - record.getHomeGoal() + downAH;
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
        }
        if (direction.equals("Host-3") || direction.equals("Away-3")) {
            profit = 0.35f * win + 0.175f * hWin - 0.5f * hLose - lose;
        } else if (direction.equals("Host-2") || direction.equals("Away-2")) {
            profit = 0.5f * win + 0.25f * hWin - 0.5f * hLose - lose;
        } else if (direction.equals("Host-1") || direction.equals("Away-1")) {
            profit = 0.65f * win + 0.325f * hWin - 0.5f * hLose - lose;
        }
        return new ProfitEstimationAH(win, hWin, draw, hLose, lose, profit);
    }

    public ProfitEstimationAH getGoalLineEstimation(List<RecordInfo> records, float goalLine, String direction) {
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
            if (direction.equals("GO-3")) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goal - goalLine;
                }
            } else if (direction.equals("GU-3")) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goalLine - goal;
                }
            } else if (direction.equals("GO-2")) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goal - goalLine;
                }
            } else if (direction.equals("GU-2")) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goalLine - goal;
                }
            } else if (direction.equals("GO-1")) {
                if (null != record.getHomeGoal() && null != record.getAwayGoal()) {
                    goal = record.getHomeGoal() + record.getAwayGoal();
                    diff = goal - goalLine;
                }
            } else if (direction.equals("GU-1")) {
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
        }
        if (direction.equals("GO-3") || direction.equals("GU-3")) {
            profit = 0.35f * win + 0.175f * hWin - 0.5f * hLose - lose;
        } else if (direction.equals("GO-2") || direction.equals("GU-2")) {
            profit = 0.5f * win + 0.25f * hWin - 0.5f * hLose - lose;
        } else if (direction.equals("GO-1") || direction.equals("GU-1")) {
            profit = 0.65f * win + 0.325f * hWin - 0.5f * hLose - lose;
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
