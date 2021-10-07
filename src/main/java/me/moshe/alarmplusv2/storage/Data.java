package me.moshe.alarmplusv2.storage;

import me.moshe.alarmplusv2.Alarm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Data implements Serializable {

    int passLength;
    String alarmSoundPath;
    List<Alarm> alarmList = new ArrayList<>();

    public void setAlarmSoundPath(String alarmSoundPath) {
        this.alarmSoundPath = alarmSoundPath;
    }

    public void setPassLength(int passLength) {
        this.passLength = passLength;
    }

    public int getPassLength() {
        return passLength;
    }

    public String getAlarmSoundPath() {
        return alarmSoundPath;
    }

    public List<Alarm> getAlarmList() {
        return alarmList;
    }
}
