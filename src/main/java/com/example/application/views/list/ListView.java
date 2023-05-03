package com.example.application.views.list;

import com.example.application.data.entity.GameInfo;
import com.example.application.data.entity.League;
import com.example.application.data.entity.ProbabilityGame;
import com.example.application.data.entity.TeamInfo;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.util.Pair;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.application.data.entity.ProbabilityGame.AsianHandicap;
import static com.example.application.data.entity.TeamInfo.nameColumns;


@PageTitle("list")
@Slf4j
public abstract class ListView extends VerticalLayout {

    private Grid<TeamInfo> againstGrid;
    private Grid<TeamInfo> mainGrid;
    private ComboBox comboBoxHome = new ComboBox<>("Home");
    private ComboBox comboBoxAway = new ComboBox<>("Away");
    private DatePicker datePickerBegin = new DatePicker("Begin");
    private DatePicker datePickerEnd = new DatePicker("End");
    private Button searchButton = new Button("Search");
    private ComboBox<String> comboBoxAH = new ComboBox("AHCh");
    private Button buttonGetChance = new Button("Get Chance");
    private Grid<ProbabilityGame> probabilityGameGrid;
    private TextField upRate = new TextField("上盘");
    private TextField upChance = new TextField("概率");
    private TextField up_1_Rate = new TextField("上盘降1");
    private TextField up_1_Chance = new TextField("概率");
    private TextField up_2_Rate = new TextField("上盘降2");
    private TextField up_2_Chance = new TextField("概率");
    private TextField up_3_Rate = new TextField("上盘降3");
    private TextField up_3_Chance = new TextField("概率");
    private TextField downRate = new TextField("下盘");
    private TextField downChance = new TextField("概率");
    private TextField down_1_Rate = new TextField("下盘降1");
    private TextField down_1_Chance = new TextField("概率");
    private TextField down_2_Rate = new TextField("下盘降2");
    private TextField down_2_Chance = new TextField("概率");
    private TextField down_3_Rate = new TextField("下盘降3");
    private TextField down_3_Chance = new TextField("概率");
    private TextField hostDraw = new TextField("主平");
    private TextField hostDrawChance = new TextField("概率");
    private TextField awayDraw = new TextField("客平");
    private TextField awayDrawChance = new TextField("概率");
    private TextField hostAway = new TextField("主客");
    private TextField hostAwayChance = new TextField("概率");
    protected Map<String, Pair<Date, Date>> dateSeason = new HashMap<>();
    private List<TeamInfo> mainDataList = null;

    public ListView() {
        setWidthFull();
        configureDate();
        add(getEstimationArea());
        probabilityGameGrid = getChanceGrid("probabilityGrid");
        add(probabilityGameGrid);
        add(getGameListLayout());
        add(getToolBar());
        add(new Label("Against teams"));
        againstGrid = configureGrid("againstGrid");
        againstGrid.setHeight("200px");
        add(againstGrid);
        add(getMainGridTitleBar());
        mainGrid = configureGrid("mainGrid");
        add(mainGrid);
//        updateMainGrid();
    }

    private void configureDate() {
        dateSeason.put("2223", Pair.of(Date.valueOf("2022-8-1"), Date.valueOf("2023-5-31")));
    }


    private Grid configureGrid(String name) {
        Grid<TeamInfo> grid = new Grid<>(TeamInfo.class);
        grid.addClassName(name);
        grid.setSizeUndefined();
        grid.setColumns(nameColumns);
        grid.getColumnByKey("name").setAutoWidth(true);
        grid.getColumnByKey("game").setHeader("G").setAutoWidth(true);
        grid.getColumnByKey("host").setHeader("H").setAutoWidth(true);
        grid.getColumnByKey("hostWin").setHeader("W").setAutoWidth(true);
        grid.getColumnByKey("hostDraw").setHeader("D").setAutoWidth(true);
        grid.getColumnByKey("hostOddsWin").setHeader("OW").setAutoWidth(true);
        grid.getColumnByKey("hostUp").setHeader("U").setAutoWidth(true);
        grid.getColumnByKey("hostUpWin").setHeader("UW").setAutoWidth(true);
        grid.getColumnByKey("hostUpDraw").setHeader("UD").setAutoWidth(true);
        grid.getColumnByKey("hostDown").setHeader("D").setAutoWidth(true);
        grid.getColumnByKey("hostDownWin").setHeader("DW").setAutoWidth(true);
        grid.getColumnByKey("hostDownDraw").setHeader("DD").setAutoWidth(true);
        grid.getColumnByKey("hostTie").setHeader("T").setAutoWidth(true);
        grid.getColumnByKey("hostTieWin").setHeader("TW").setAutoWidth(true);
        grid.getColumnByKey("away").setHeader("A").setAutoWidth(true);
        grid.getColumnByKey("awayWin").setHeader("W").setAutoWidth(true);
        grid.getColumnByKey("awayDraw").setHeader("D").setAutoWidth(true);
        grid.getColumnByKey("awayOddsWin").setHeader("OW").setAutoWidth(true);
        grid.getColumnByKey("awayUp").setHeader("U").setAutoWidth(true);
        grid.getColumnByKey("awayUpWin").setHeader("UW").setAutoWidth(true);
        grid.getColumnByKey("awayUpDraw").setHeader("UD").setAutoWidth(true);
        grid.getColumnByKey("awayDown").setHeader("DO").setAutoWidth(true);
        grid.getColumnByKey("awayDownWin").setHeader("DW").setAutoWidth(true);
        grid.getColumnByKey("awayDownDraw").setHeader("DD").setAutoWidth(true);
        grid.getColumnByKey("awayTie").setHeader("T").setAutoWidth(true);
        grid.getColumnByKey("awayTieWin").setHeader("TW").setAutoWidth(true);

        return grid;
    }

    public VerticalLayout getEstimationArea() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        HorizontalLayout buttonArea = new HorizontalLayout(datePickerBegin, datePickerEnd, comboBoxAH, buttonGetChance);
        buttonGetChance.addClickListener(event -> {
           upChance.setValue(getUpProfit(comboBoxAH.getValue(), upRate.getValue()));
           up_1_Chance.setValue(getUp_1_Profit(comboBoxAH.getValue(), up_1_Rate.getValue()));
           up_2_Chance.setValue(getUp_2_Profit(comboBoxAH.getValue(), up_2_Rate.getValue()));
           up_3_Chance.setValue(getUp_3_Profit(comboBoxAH.getValue(), up_3_Rate.getValue()));
           downChance.setValue(getDownProfit(comboBoxAH.getValue(), downRate.getValue()));
           down_1_Chance.setValue(getDown_1_Profit(comboBoxAH.getValue(), down_1_Rate.getValue()));
           down_2_Chance.setValue(getDown_2_Profit(comboBoxAH.getValue(), down_2_Rate.getValue()));
           down_3_Chance.setValue(getDown_3_Profit(comboBoxAH.getValue(), down_3_Rate.getValue()));
           hostDrawChance.setValue(getHostDrawProfit(comboBoxAH.getValue(), hostDraw.getValue()));
           awayDrawChance.setValue(getAwayDrawProfit(comboBoxAH.getValue(), awayDraw.getValue()));
           hostAwayChance.setValue(getHostAwayProfit(comboBoxAH.getValue(), hostAway.getValue()));
        });
        comboBoxAH.setItems(AsianHandicap);
        comboBoxAH.addValueChangeListener(event -> {
            probabilityGameGrid.setItems(getProbabilityGameAH(Float.valueOf(comboBoxAH.getValue()), datePickerBegin.getValue(),
                    datePickerEnd.getValue()));
            updateChance(event.getValue());
        });
        buttonArea.setDefaultVerticalComponentAlignment(Alignment.END);

        HorizontalLayout upArea = new HorizontalLayout();
        upRate.setValue("0");
        upRate.setWidth(20, Unit.MM);
        upChance.setReadOnly(true);
        upChance.setValue("0");
        upChance.setWidth(20, Unit.MM);
        up_1_Rate.setValue("0");
        up_1_Rate.setWidth(20, Unit.MM);
        up_1_Chance.setReadOnly(true);
        up_1_Chance.setValue("0");
        up_1_Chance.setWidth(20, Unit.MM);
        up_2_Rate.setValue("0");
        up_2_Rate.setWidth(20, Unit.MM);
        up_2_Chance.setReadOnly(true);
        up_2_Chance.setValue("0");
        up_2_Chance.setWidth(20, Unit.MM);
        up_3_Rate.setValue("0");
        up_3_Rate.setWidth(20, Unit.MM);
        up_3_Chance.setReadOnly(true);
        up_3_Chance.setValue("0");
        up_3_Chance.setWidth(20, Unit.MM);
        upArea.add(upRate, upChance, up_1_Rate, up_1_Chance, up_2_Rate, up_2_Chance, up_3_Rate, up_3_Chance);

        HorizontalLayout downArea = new HorizontalLayout();
        downRate.setValue("0");
        downRate.setWidth(20, Unit.MM);
        downChance.setReadOnly(true);
        downChance.setValue("0");
        downChance.setWidth(20, Unit.MM);
        down_1_Rate.setValue("0");
        down_1_Rate.setWidth(20, Unit.MM);
        down_1_Chance.setReadOnly(true);
        down_1_Chance.setValue("0");
        down_1_Chance.setWidth(20, Unit.MM);
        down_2_Rate.setValue("0");
        down_2_Rate.setWidth(20, Unit.MM);
        down_2_Chance.setReadOnly(true);
        down_2_Chance.setValue("0");
        down_2_Chance.setWidth(20, Unit.MM);
        down_3_Rate.setValue("0");
        down_3_Rate.setWidth(20, Unit.MM);
        down_3_Chance.setReadOnly(true);
        down_3_Chance.setValue("0");
        down_3_Chance.setWidth(20, Unit.MM);
        downArea.add(downRate, downChance, down_1_Rate, down_1_Chance, down_2_Rate, down_2_Chance, down_3_Rate, down_3_Chance);

        HorizontalLayout otherArea = new HorizontalLayout();
        hostDraw.setValue("0");
        hostDraw.setWidth(20, Unit.MM);
        hostDrawChance.setReadOnly(true);
        hostDrawChance.setValue("0");
        hostDrawChance.setWidth(20, Unit.MM);
        awayDraw.setValue("0");
        awayDraw.setWidth(20, Unit.MM);
        awayDrawChance.setReadOnly(true);
        awayDrawChance.setValue("0");
        awayDrawChance.setWidth(20, Unit.MM);
        hostAway.setValue("0");
        hostAway.setWidth(20, Unit.MM);
        hostAwayChance.setReadOnly(true);
        hostAwayChance.setValue("0");
        hostAwayChance.setWidth(20, Unit.MM);
        otherArea.add(hostDraw, hostDrawChance, awayDraw, awayDrawChance, hostAway, hostAwayChance);

        VerticalLayout verticalLayout1 = new VerticalLayout(upArea, downArea, otherArea);
        verticalLayout1.setVisible(false);

        verticalLayout.add(buttonArea, verticalLayout1);
        return verticalLayout;
    }

    private ComboBox<String> comboBoxTeams = new ComboBox("Team");
    private VerticalLayout getGameListLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        ComboBox<String> comboBox = new ComboBox("AHCh");
        List<String> list = Lists.newArrayList();
        list.addAll(AsianHandicap);
        list.add(0, "All");
        comboBox.setItems(list);
        comboBox.setValue(list.get(0));
        comboBox.addValueChangeListener(event -> {

        });
        Button button = new Button("GetGames");

        HorizontalLayout buttonLayout = new HorizontalLayout(comboBoxTeams, comboBox, button);
        buttonLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        Grid<GameInfo> leagueGrid = new Grid<>(GameInfo.class);
        leagueGrid.setColumns(League.nameColumns);
        FooterRow footer = leagueGrid.prependFooterRow();
        FooterRow.FooterCell footerCellCost = footer.getCell(leagueGrid.getColumnByKey("date"));
        footerCellCost.setText("0");

        comboBoxTeams.addValueChangeListener(event -> {

        });
        button.addClickListener(event -> {
            List<GameInfo> list1 = getGameInfo(comboBoxTeams.getValue(), comboBox.getValue(),
                    datePickerBegin.getValue(), datePickerEnd.getValue());
            leagueGrid.setItems(list1);
            footerCellCost.setText("Rows: " + list1.size());
        });

        verticalLayout.add(buttonLayout, leagueGrid);

        return verticalLayout;
    }


    private void updateChance(String ah) {
        ListViewE0.ChanceGame chanceGame = getChance(ah);

        upChance.setLabel(String.format("%.1f", chanceGame.getUpChance()*100));
        up_1_Chance.setLabel(String.format("%.1f", chanceGame.getUp_1_Chance()*100));
        up_2_Chance.setLabel(String.format("%.1f", chanceGame.getUp_2_Chance()*100));
        up_3_Chance.setLabel(String.format("%.1f", chanceGame.getUp_3_Chance()*100));
        downChance.setLabel(String.format("%.1f", chanceGame.getDownChance()*100));
        down_1_Chance.setLabel(String.format("%.1f", chanceGame.getDown_1_Chance()*100));
        down_2_Chance.setLabel(String.format("%.1f", chanceGame.getDown_2_Chance()*100));
        down_3_Chance.setLabel(String.format("%.1f", chanceGame.getDown_3_Chance()*100));
        hostDrawChance.setLabel(String.format("%.1f", chanceGame.getHostDrawChance()*100));
        awayDrawChance.setLabel(String.format("%.1f", chanceGame.getAwayDrawChance()*100));
        hostAwayChance.setLabel(String.format("%.1f", chanceGame.getHostAwayChance()*100));
    }

    public Grid getChanceGrid(String name){
        Grid<ProbabilityGame> grid = new Grid<>(ProbabilityGame.class);
        grid.setClassName(name);
        grid.setHeight("320px");
        grid.setColumns("ah", "totalMatch", "hostWin2Ball", "hostWin1Ball", "hostWin", "draw", "awayWin", "awayWin1Ball", "awayWin2Ball");
        grid.getColumnByKey("totalMatch").setHeader("Total");
        grid.getColumnByKey("ah").setHeader("AHCh");
        grid.getColumnByKey("hostWin2Ball").setHeader("HW2B");
        grid.getColumnByKey("hostWin1Ball").setHeader("HW1B");
        grid.getColumnByKey("hostWin").setHeader("HWin");
        grid.getColumnByKey("draw").setHeader("Draw");
        grid.getColumnByKey("awayWin").setHeader("AWin");
        grid.getColumnByKey("awayWin1Ball").setHeader("AW1B");
        grid.getColumnByKey("awayWin2Ball").setHeader("AW2B");

        return grid;
    }

    public HorizontalLayout getToolBar() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(comboBoxHome, comboBoxAway, searchButton);
        searchButton.setHeightFull();
        searchButton.addClickListener(event -> searchEvent());
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return horizontalLayout;
    }

    public HorizontalLayout getMainGridTitleBar() {
        Label label = new Label("All Teams");
        Button button = new Button("Refresh");
        button.addClickListener(event -> {
            if (null != mainDataList) {
                mainGrid.setItems(mainDataList);
            }
        });
        HorizontalLayout horizontalLayout = new HorizontalLayout(label, button);
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        return horizontalLayout;
    }

    public void updateMainGrid() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> {
            Pair<Date, Date> pair = dateSeason.get("2223");
            List<TeamInfo> list = getAllTeamsInfo(pair.getFirst(), pair.getSecond());
            getUI().ifPresent(ui -> ui.access(() -> mainGrid.setItems(list)));
        };
        service.schedule(task, 3000, TimeUnit.MILLISECONDS);
        service.shutdown();
    }

    public void updateToolbar(List<String> homes, Pair<Date, Date> pair) {
        comboBoxHome.setItems(homes);
        comboBoxHome.setValue(homes.get(0));
        comboBoxAway.setItems(homes);
        comboBoxAway.setValue(homes.get(0));
        datePickerBegin.setValue(pair.getFirst().toLocalDate());
        datePickerEnd.setValue(pair.getSecond().toLocalDate());

        List<String> list = Lists.newArrayList();
        list.addAll(homes);
        list.add(0, "All");
        comboBoxTeams.setItems(list);
        comboBoxTeams.setValue(list.get(0));

        comboBoxAH.setValue(AsianHandicap.get(1));
    }

    public void updateProbabilityArea() {

    }

    private void searchEvent() {
        if (null == comboBoxHome.getValue() || null == comboBoxAway.getValue() || null == datePickerBegin.getValue()
                || null == datePickerEnd.getValue()) {
            return;
        }
        againstGrid.setItems(searchAgainstTeams(comboBoxHome.getValue(), comboBoxAway.getValue(),
                datePickerBegin.getValue(), datePickerEnd.getValue()));
    }

    public abstract List<TeamInfo> searchAgainstTeams(Object home, Object away, LocalDate begin, LocalDate end);

    public abstract List<TeamInfo> getAllTeamsInfo(Date begin, Date end);

    public abstract List<ProbabilityGame> getProbabilityGameAH(float ah, LocalDate begin, LocalDate end);

    public abstract ListViewE0.ChanceGame getChance(String ah);

    public abstract String getUpProfit(String ah, String odds);
    public abstract String getUp_1_Profit(String ah, String odds);
    public abstract String getUp_2_Profit(String ah, String odds);
    public abstract String getUp_3_Profit(String ah, String odds);
    public abstract String getDownProfit(String ah, String odds);
    public abstract String getDown_1_Profit(String ah, String odds);
    public abstract String getDown_2_Profit(String ah, String odds);
    public abstract String getDown_3_Profit(String ah, String odds);
    public abstract String getHostDrawProfit(String ah, String odds);
    public abstract String getAwayDrawProfit(String ah, String odds);
    public abstract String getHostAwayProfit(String ah, String odds);
    public abstract List<GameInfo> getGameInfo(String nameTeam, String ah, LocalDate begin, LocalDate end);

    public abstract Button getTestButton();

}
