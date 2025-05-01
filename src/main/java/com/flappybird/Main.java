package com.flappybird;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.flappybird.ui.GamePane;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GamePane gamePane = new GamePane();
        Scene scene = new Scene(gamePane, 800, 600);
        
        primaryStage.setTitle("Neural Flappy Bird");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        gamePane.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 