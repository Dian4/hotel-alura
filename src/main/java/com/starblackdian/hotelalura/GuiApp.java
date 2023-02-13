package com.starblackdian.hotelalura;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiApp extends Application {

    public static Stage PRIMARY_STAGE;
    
    public static void ejecutarApp(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PRIMARY_STAGE = primaryStage;

        primaryStage.setTitle("Hotel Alura - Sistema de Gestión");
        primaryStage.setResizable(false);
        
        final Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));

        final Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
