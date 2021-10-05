package me.moshe.alarmplusv2.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UnlockWindowController implements Initializable {

    static int passLength = 4;
    static File audioFile = new File("C:\\Users\\Drago\\IdeaProjects\\AlarmPlusV2\\src\\main\\defaultAlarm.wav");
    int password = 0;
    Clip clip;
    AudioInputStream audioInputStream;


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
        try {
            audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
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
            clip.stop();
            checkLabel.getScene().getWindow().hide();
        }
        else checkLabel.setText("Incorrect!");
    }

    @FXML public void focus() {
        anchorPane.requestFocus();
    }
}
