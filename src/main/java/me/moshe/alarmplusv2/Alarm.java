package me.moshe.alarmplusv2;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.border.utilities.scheduler.async.AsyncTask;
import me.border.utilities.scheduler.async.AsyncTaskBuilder;
import me.moshe.alarmplusv2.ui.controllers.MainWindowController;
import me.moshe.alarmplusv2.ui.controllers.UnlockWindowController;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class Alarm {

    AlarmManager alarmManager = MainWindowController.alarmManager;
    int alarmHour;
    int alarmMin;

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

    private void alarmTrigger(){
        LocalTime timePressed = LocalTime.now();
        LocalTime alarm = LocalTime.of(alarmHour, alarmMin);
        long untilAlarm = timePressed.until(alarm, ChronoUnit.MILLIS);
        if(untilAlarm < 0) {
            untilAlarm -= untilAlarm - 8.64e+7 - untilAlarm;
        }
        AsyncTaskBuilder.builder()
                .after(untilAlarm, TimeUnit.MILLISECONDS)
                .task(() -> Platform.runLater(() -> {
                   openUnlockWindow();
                    for (int i = 0; i < alarmManager.getAlarmList().size(); i++)
                        if (alarmManager.getAlarmList().get(i).compareAlarm(alarmHour, alarmMin)) alarmManager.getAlarmList().remove(i);
                }))
                .build();
    }

    private void openUnlockWindow(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UnlockWindow.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
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

    public void setAlarmMin(int alarmMin) {
        this.alarmMin = alarmMin;
    }

    public void setAlarmHour(int alarmHour) {
        this.alarmHour = alarmHour;
    }

    public int getAlarmHour() {
        return alarmHour;
    }

    public int getAlarmMin() {
        return alarmMin;
    }

    @Override
    public String toString() {
        return "Alarm is set to: " + alarmHour + ":" + alarmMin;
    }
}
