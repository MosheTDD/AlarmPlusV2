package me.moshe.alarmplusv2.storage;

import me.border.utilities.file.AbstractSerializedFile;

import java.io.File;

public class DataFile extends AbstractSerializedFile<Data> {

    public static final File userHome = new File(System.getProperty("user.home"));

    public DataFile() {
        super("SettingFile", new File(userHome + File.separator + "AlarmPlusV2"), new Data());
    }
}
