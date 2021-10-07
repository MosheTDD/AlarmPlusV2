package me.moshe.alarmplusv2;

import me.moshe.alarmplusv2.storage.DataFile;
import me.moshe.alarmplusv2.ui.Interface;


public class Main {
    private static Interface inf = new Interface();
    private static DataFile dataFile = new DataFile();
    private static AlarmManager alarmManager = new AlarmManager();

    public static void main(String[] args) {
        dataFile.setup();
        for (Alarm a : dataFile.getItem().getAlarmList()){
            a.alarmTrigger();
            alarmManager.addAlarm(a);
        }
        inf.start(args);
    }

    public static DataFile getDataFile() {
        return dataFile;
    }

    public static AlarmManager getAlarmManager() {
        return alarmManager;
    }
}
