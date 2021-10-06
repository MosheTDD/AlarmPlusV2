package me.moshe.alarmplusv2.ui.controllers;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import me.border.utilities.scheduler.AsyncTasker;
import me.border.utilities.scheduler.async.AsyncTaskBuilder;
import me.moshe.alarmplusv2.Main;
import me.moshe.alarmplusv2.ui.Interface;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class SettingsWindowController implements Initializable {

    @FXML AnchorPane anchorPane;
    @FXML ChoiceBox<String> passLengthChoiceBox;
    @FXML Label currentAlarm;
    @FXML StackPane stackPane;

    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    JFXDialog dialog;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentAlarm.setText(new File(Main.getDataFile().getItem().getAlarmSoundPath()).getName());
        passLengthChoiceBox.setValue(String.valueOf(Main.getDataFile().getItem().getPassLength()));
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

    private void createNotification(String description) {
        dialogLayout.setBody(new Text(description));
        dialogLayout.setHeading(new Text("Saved"));
        dialogLayout.setStyle("-fx-background-color:  #dedede;");
        dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        dialog.show();
        AsyncTaskBuilder.builder()
                .after(1500, TimeUnit.MILLISECONDS)
                .task(() -> Platform.runLater(this::closeNotification))
                .build();
    }

    private void closeNotification() {
        dialog.close();
    }

    @FXML private void saveSettings(){
        Main.getDataFile().getItem().setPassLength(Integer.parseInt(passLengthChoiceBox.getValue()));
        createNotification("Settings saved!");
    }

    @FXML private void uploadAlarm(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Alarm");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Audio files (*.wav)", "*.wav");
        fileChooser.getExtensionFilters().add(extFilter);
        File temp = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        if(temp != null){
            Main.getDataFile().getItem().setAlarmSoundPath(temp.getPath());
            AsyncTasker.runTaskAsync(() -> Main.getDataFile().save());
            currentAlarm.setText(temp.getName());
        }
    }

    @FXML private void switchToMainScene() {
        switchScene("/view/MainWindow.fxml", 1024, 768);
    }

}
