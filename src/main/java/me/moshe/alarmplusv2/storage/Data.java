package me.moshe.alarmplusv2.storage;

import java.io.Serializable;

public class Data implements Serializable {

    int passLength;
    String alarmSoundPath;

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

}
