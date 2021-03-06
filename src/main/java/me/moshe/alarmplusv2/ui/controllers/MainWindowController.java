package me.moshe.alarmplusv2.ui.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import me.border.utilities.scheduler.AsyncTasker;
import me.border.utilities.scheduler.async.AsyncTask;
import me.border.utilities.scheduler.async.AsyncTaskBuilder;
import me.moshe.alarmplusv2.Alarm;
import me.moshe.alarmplusv2.AlarmManager;
import me.moshe.alarmplusv2.Main;
import me.moshe.alarmplusv2.ui.Interface;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class MainWindowController implements Initializable {
    private AlarmManager alarmManager = Main.getAlarmManager();
    private SimpleDateFormat timeFormat;
    private String time;

    @FXML public AnchorPane anchorPane;

    @FXML public Label currentTimeLabel, setHourLabel, setMinLabel, alarmSetLabel;

    @FXML public Slider setHourSlider, setMinSlider;

    @FXML public ListView<Alarm> alarmListView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Interface.setMainWindowController(this);
        if(Main.getDataFile().getItem().getPassLength() == 0){
            Main.getDataFile().getItem().setPassLength(4);
            AsyncTasker.runTaskAsync(() -> Main.getDataFile().save());
        }
        else if(Main.getDataFile().getItem().getAlarmSoundPath() == null){
            Main.getDataFile().getItem().setAlarmSoundPath("defaultAlarm.wav");
            AsyncTasker.runTaskAsync(() -> Main.getDataFile().save());
        }
        getCurrentTime();
        fixSlider(setHourSlider, setMinSlider);
        fixLabels();
        alarmListView.getItems().addAll(alarmManager.getAlarmList());
    }

    @FXML private void setAlarm() {
        int hour = (int) setHourSlider.getValue();
        int min = (int) setMinSlider.getValue();
        alarmManager.addAlarm(hour, min);
        setAlarmLabel(hour, min);
        if(!checkAlarmListExists(hour, min))
            alarmListView.getItems().add(alarmManager.getAlarmList().get(alarmManager.getAlarmList().size() - 1));
    }

    private boolean checkAlarmListExists(int hour, int min){
        boolean check = false;
        for (Alarm a : alarmListView.getItems()){
            if(a.compareAlarm(hour, min)){
                check = true;
                break;
            }
        }
        return check;
    }

    private void setAlarmLabel(int hour, int min){
        if(alarmManager.getAlarmExists()){
            alarmSetLabel.textProperty().setValue("Alarm already exists!");
            clearAlarmSetLabel();
            return;
        }
        String newHour = String.valueOf(hour), newMin = String.valueOf(min);
        if(hour <= 9) newHour = "0" + hour;
        if(min <= 9) newMin = "0" + min;
        alarmSetLabel.textProperty().setValue("Alarm for: " + newHour + ":" + newMin + " was added!");
        clearAlarmSetLabel();
    }

    private void switchScene(String fxmlFile, int width, int height){
        try{
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root, width, height);
            Interface.window.setScene(scene);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void clearAlarmSetLabel(){
        AsyncTaskBuilder.builder()
                .after(2, TimeUnit.SECONDS)
                .task(new AsyncTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> alarmSetLabel.textProperty().setValue(""));
                    }
                })
                .build();
    }

    public ListView<Alarm> getAlarmListView() {
        return alarmListView;
    }

    @FXML private void removeAlarmFromList() {
        Alarm alarm = alarmListView.getSelectionModel().getSelectedItem();
        if(alarm == null) return;
        alarmManager.deleteAlarm(alarm);
    }

    @FXML private void switchToSettingsScene() {
        switchScene("/view/SettingsWindow.fxml",1024, 768);
    }

    @FXML private void getCurrentTime() {
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1), e -> {
                    time = timeFormat.format(Calendar.getInstance().getTime());
                    currentTimeLabel.setText(time);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML private void fixLabels(){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1), e -> {
                    if(String.valueOf(setHourSlider.valueProperty().asString("%.0f").getValue()).length() == 1)
                        setHourLabel.textProperty().bind((setHourSlider.valueProperty().asString("0%.0f")));
                    else
                        setHourLabel.textProperty().bind((setHourSlider.valueProperty().asString("%.0f")));
                    if(String.valueOf(setMinSlider.valueProperty().asString("%.0f").getValue()).length() == 1)
                        setMinLabel.textProperty().bind((setMinSlider.valueProperty().asString("0%.0f")));
                    else
                        setMinLabel.textProperty().bind(setMinSlider.valueProperty().asString("%.0f"));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML private void fixSlider(Slider s1, Slider s2){
        s1.valueProperty().addListener((obs, oldval, newVal) ->
                s1.setValue(newVal.intValue()));
        s2.valueProperty().addListener((obs, oldval, newVal) ->
                s2.setValue(newVal.intValue()));
    }

    @FXML public void focus() {
        anchorPane.requestFocus();
    }

}

