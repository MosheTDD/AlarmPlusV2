package me.moshe.alarmplusv2;

import me.moshe.alarmplusv2.storage.DataFile;
import me.moshe.alarmplusv2.ui.Interface;


public class Main {
    private static Interface inf = new Interface();
    private static DataFile dataFile = new DataFile();
    public static void main(String[] args) {
        dataFile.setup();
        inf.start(args);
    }

    public static DataFile getDataFile() {
        return dataFile;
    }
}
