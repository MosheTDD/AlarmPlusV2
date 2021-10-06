package me.moshe.alarmplusv2;

import java.util.ArrayList;
import java.util.List;

public class AlarmManager {

    List<Alarm> alarmList = new ArrayList<>();
    boolean alarmExists = false;

    public void addAlarm(int hour, int min){
        if(alarmList.size() == 0) {
            alarmList.add(new Alarm(hour, min));
            return;
        }
        for (Alarm a : alarmList) alarmExists = a.compareAlarm(hour, min);
        if(!alarmExists) {
            Alarm alarm = new Alarm(hour, min);
            alarmList.add(alarm);
        }
    }

    public List<Alarm> getAlarmList() {
        return alarmList;
    }

    public boolean getAlarmExists() {return alarmExists;}
}
