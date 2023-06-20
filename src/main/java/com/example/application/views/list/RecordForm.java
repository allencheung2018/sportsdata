package com.example.application.views.list;

import com.example.application.data.entity.RecordAH;
import com.example.application.data.entity.RecordInfo;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.sql.Date;
import java.time.LocalDate;

import static com.example.application.data.entity.ProbabilityGame.AsianHandicap;
import static com.example.application.data.entity.ProbabilityGame.GoalLine;

public class RecordForm extends FormLayout {

    private DatePicker matchDate = new DatePicker(LocalDate.now());
    private DatePicker betDate = new DatePicker(LocalDate.now());
    private TextField league = new TextField("League");
    private TextField profit = new TextField("Profit");
    private TextField homeTeam = new TextField("HomeTeam");
    private TextField homeGoal = new TextField("HG");
    private TextField direction = new TextField("Direction");
    private TextField awayGoal = new TextField("AG");
    private ComboBox<String> ah = new ComboBox<>("AH");
    private ComboBox<String> ahBet = new ComboBox<>("BetAH");
    private ComboBox<String> goalLine = new ComboBox<>("GoalLine");
    private ComboBox<String> betGL = new ComboBox<>("BetGL");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button cancel = new Button("Cancel");
    private Binder<RecordInfo> binder = new BeanValidationBinder<>(RecordInfo.class);

    public RecordForm() {
        addClassName("record-form");
        binder.bindInstanceFields(this);

        setSizeUndefined();

        ah.setItems(AsianHandicap);
        ahBet.setItems(AsianHandicap);
        goalLine.setItems(GoalLine);
        betGL.setItems(GoalLine);

        matchDate.setWidth("35mm");
        betDate.setWidth("35mm");
        homeTeam.setWidth("40mm");
        profit.setWidth("30mm");
        league.setWidth("22mm");
        homeGoal.setWidth("22mm");
        awayGoal.setWidth("22mm");
        direction.setWidth("22mm");
        ah.setWidth("22mm");
        ahBet.setWidth("22mm");
        goalLine.setWidth("35mm");
        betGL.setWidth("35mm");
        VerticalLayout layout = new VerticalLayout(
                new HorizontalLayout(matchDate, betDate),
                new HorizontalLayout(homeTeam, profit),
                new HorizontalLayout(league, homeGoal, awayGoal),
                new HorizontalLayout(direction, ah, ahBet),
                new HorizontalLayout(goalLine, betGL),
                createButtonsLayout());
        layout.setSizeFull();
        add(layout);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public void setRecordInfo(RecordInfo recordInfo) {
        binder.setBean(recordInfo);
    }

    public static abstract class RecordFormEvent extends ComponentEvent<RecordForm> {
        private RecordInfo recordInfo;

        protected RecordFormEvent(RecordForm source, RecordInfo recordInfo) {
            super(source, false);
            this.recordInfo = recordInfo;
        }

        public RecordInfo getRecordInfo() {
            return recordInfo;
        }

        public RecordAH getRecordAH() {
            RecordAH recordAH = new RecordAH();
            recordAH.setId(recordInfo.getId());
            recordAH.setGameDate(Date.valueOf(recordInfo.getMatchDate()));
            recordAH.setBetDate(Date.valueOf(recordInfo.getBetDate()));
            recordAH.setLeague(recordInfo.getLeague());
            recordAH.setHomeTeam(recordInfo.getHomeTeam());
            recordAH.setHostGoal(recordInfo.getHomeGoal());
            recordAH.setAwayGoal(recordInfo.getAwayGoal());
            recordAH.setAh(recordInfo.getAh());
            recordAH.setAhBet(recordInfo.getAhBet());
            recordAH.setDirection(recordInfo.getDirection());
            recordAH.setProfit(recordInfo.getProfit());
            recordAH.setGoalLine(recordInfo.getGoalLine());
            recordAH.setBetGL(recordInfo.getBetGL());
            return recordAH;
        }
    }

    public static class SaveEvent extends RecordFormEvent {
        SaveEvent(RecordForm source, RecordInfo recordInfo) {
            super(source, recordInfo);
        }
    }

    public static class CloseEvent extends RecordFormEvent {
        protected CloseEvent(RecordForm source) {
            super(source, null);
        }
    }

    public static class DeleteEvent extends RecordFormEvent {
        DeleteEvent(RecordForm source, RecordInfo recordInfo) {
            super(source, recordInfo);
        }
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addDeletedListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addCloseEvnet(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}
