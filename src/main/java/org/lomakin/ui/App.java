package org.lomakin.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

import static java.util.Objects.requireNonNull;

public class App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL resource = getClass().getClassLoader().getResource("root.fxml");
        Parent root = FXMLLoader.load(requireNonNull(resource));
        primaryStage.setTitle(AppContext.APP_TITLE);
        Scene scene = new Scene(root, AppContext.WIDTH, AppContext.HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
