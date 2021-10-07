package me.moshe.alarmplusv2.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.moshe.alarmplusv2.ui.controllers.MainWindowController;

import java.io.IOException;

public class Interface extends Application {
    public static Stage window;
    private static MainWindowController mainWindowController;

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
        window.getIcons().add(new Image("Imgs/Icon.png"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        Parent root = loader.load();
        mainWindowController = loader.getController();
        window.setScene(new Scene(root, 1024, 768));
        window.show();
        mainWindowController.focus();
    }

    public static MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public static void setMainWindowController(MainWindowController mainWindowController) {
        Interface.mainWindowController = mainWindowController;
    }
}
