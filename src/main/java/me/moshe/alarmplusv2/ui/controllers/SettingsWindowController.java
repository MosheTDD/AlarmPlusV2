package me.moshe.alarmplusv2.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import me.moshe.alarmplusv2.ui.Interface;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsWindowController implements Initializable {

    @FXML AnchorPane anchorPane;
    @FXML ChoiceBox<String> passLengthChoiceBox;
    @FXML Label currentAlarm;

    /* TODO #1 create a readable settings YML file that the settings
            will save and read from it.
            #2 Add a label which confirms the settings have benn saved.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentAlarm.setText(UnlockWindowController.audioFile.getName());
        passLengthChoiceBox.setValue("4");
        passLengthChoiceBox.getItems().addAll("4", "5", "6", "7");
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

    @FXML private void saveSettings(){
        UnlockWindowController.passLength = Integer.parseInt(passLengthChoiceBox.getValue());
    }

    @FXML private void uploadAlarm(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Alarm");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Audio files (*.wav)", "*.wav");
        fileChooser.getExtensionFilters().add(extFilter);
        File temp = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        if(temp != null){
            UnlockWindowController.audioFile = temp;
            currentAlarm.setText(UnlockWindowController.audioFile.getName());
        }
    }

    @FXML private void switchToMainScene() {
        switchScene("/view/MainWindow.fxml", 1024, 768);
    }

}
