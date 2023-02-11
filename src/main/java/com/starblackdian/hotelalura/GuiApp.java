package com.starblackdian.hotelalura;
import javafx.application.Application;
import javafx.stage.Stage;

public class GuiApp extends Application {
    
    public static void ejecutarApp(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hotel Alura - Sistema de Gestión");
        primaryStage.setResizable(false);
        
        primaryStage.show();
    }
}
