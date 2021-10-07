package me.moshe.alarmplusv2;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.border.utilities.scheduler.async.AsyncTask;
import me.border.utilities.scheduler.async.AsyncTaskBuilder;
import me.moshe.alarmplusv2.ui.controllers.UnlockWindowController;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class Alarm implements Serializable {

    transient AlarmManager alarmManager = Main.getAlarmManager();
    private final int alarmHour;
    private final int alarmMin;
    private transient AsyncTask task;

    public Alarm(int alarmHour, int alarmMin){
        this.alarmHour = alarmHour;
        this.alarmMin = alarmMin;
        alarmTrigger();
    }

    public boolean compareAlarm(Alarm alarm){
        return alarmHour == alarm.getAlarmHour() && alarmMin == alarm.getAlarmMin();
    }

    public boolean compareAlarm(int hour, int min){
        return alarmHour == hour && alarmMin == min;
    }

    public void alarmTrigger(){
        LocalTime timePressed = LocalTime.now();
        LocalTime alarm = LocalTime.of(alarmHour, alarmMin);
        long untilAlarm = timePressed.until(alarm, ChronoUnit.MILLIS);
        if(untilAlarm < 0) {
            untilAlarm -= untilAlarm - 8.64e+7 - untilAlarm;
        }
        Alarm thisAlarm = this;
        task = AsyncTaskBuilder.builder()
                .after(untilAlarm, TimeUnit.MILLISECONDS)
                .task(new AsyncTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            openUnlockWindow();
                            alarmManager.deleteAlarm(thisAlarm);
                        });
                    }
                })
                .build();
    }

    private void openUnlockWindow(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UnlockWindow.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setOnCloseRequest(Event::consume);
            UnlockWindowController unlockWindowController = loader.getController();
            stage.setTitle("Wake up!");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.setIconified(false);
            stage.show();
            unlockWindowController.focus();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void cancelAlarm() {
        task.closeSilently();
    }

    public int getAlarmHour() {
        return alarmHour;
    }

    public int getAlarmMin() {
        return alarmMin;
    }

    @Override
    public String toString() {
        String fixedHour = String.valueOf(alarmHour), fixedMin = String.valueOf(alarmMin);
        if(alarmHour < 10)  fixedHour = "0" + alarmHour;
        if (alarmMin < 10) fixedMin = "0" + alarmMin;
        return fixedHour + ":" + fixedMin;
    }
}
