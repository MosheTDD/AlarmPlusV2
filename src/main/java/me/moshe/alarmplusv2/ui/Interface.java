package me.moshe.alarmplusv2.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.moshe.alarmplusv2.ui.controllers.MainWindowController;

import java.io.IOException;

public class Interface extends Application {
    public static Stage window;

    public void start(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        openMainWindow();
    }

    private void openMainWindow() throws IOException {
        window.setTitle("AlarmPlusV2");
        window.setResizable(false);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        Parent root = loader.load();
        MainWindowController alarmPlusV2MainController = loader.getController();
        window.setScene(new Scene(root, 1024, 768));
        window.show();
        alarmPlusV2MainController.focus();
    }
}
