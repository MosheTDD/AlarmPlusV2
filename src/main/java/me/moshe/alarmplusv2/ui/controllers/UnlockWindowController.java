package me.moshe.alarmplusv2.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import me.moshe.alarmplusv2.Main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UnlockWindowController implements Initializable {

    int passLength = Main.getDataFile().getItem().getPassLength();
    File audioFile = new File(Main.getDataFile().getItem().getAlarmSoundPath());
    int password = 0;
    Media media;
    MediaPlayer mediaPlayer;


    @FXML AnchorPane anchorPane;

    @FXML Label passwordLabel, checkLabel;

    @FXML Button submitButton;

    @FXML TextField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playAlarm();
        checkLabel.setVisible(false);
        generatePassword(getMaximum(passLength),1000);
        passwordLabel.setText(String.valueOf(password));
    }

    private void generatePassword(int maximum, int minimum) {
        password = ((int) (Math.random() * (maximum - minimum))) + minimum;
    }

    private void playAlarm(){
        media = new Media(audioFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    private int getMaximum(int digits){
        switch (digits){
            case 5:
                return 100000;
            case 6:
                return 1000000;
            case 7:
                return 10000000;
            default:
                return 10000;
        }
    }

    @FXML private void checkPassword(){
        checkLabel.setVisible(true);
        String pass = String.valueOf(password);
        if(passwordField.getText().equals(pass)){
            mediaPlayer.stop();
            checkLabel.getScene().getWindow().hide();
        }
        else checkLabel.setText("Incorrect!");
    }

    @FXML public void focus() {
        anchorPane.requestFocus();
    }
}
