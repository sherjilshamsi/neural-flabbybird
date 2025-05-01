package com.flappybird.ui;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.flappybird.game.Bird;
import com.flappybird.game.Pipe;
import com.flappybird.ai.NeuralNetwork;

public class GamePane extends Pane {
    private static final double GRAVITY = 0.5;
    private static final double JUMP_FORCE = -10;
    private static final double PIPE_SPEED = 2;
    private static final double PIPE_SPAWN_INTERVAL = 2000; // milliseconds
    
    private Bird bird;
    private NeuralNetwork neuralNetwork;
    private AnimationTimer gameLoop;
    private long lastPipeSpawn;
    
    public GamePane() {
        setPrefSize(800, 600);
        setStyle("-fx-background-color: #87CEEB;");
        
        bird = new Bird(100, 300);
        neuralNetwork = new NeuralNetwork();
        getChildren().add(bird);
        
        lastPipeSpawn = System.currentTimeMillis();
    }
    
    public void startGame() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();
    }
    
    private void update() {
        // Update bird position
        bird.setVelocityY(bird.getVelocityY() + GRAVITY);
        bird.setTranslateY(bird.getTranslateY() + bird.getVelocityY());
        
        // Spawn pipes
        if (System.currentTimeMillis() - lastPipeSpawn > PIPE_SPAWN_INTERVAL) {
            spawnPipe();
            lastPipeSpawn = System.currentTimeMillis();
        }
        
        // Move pipes
        getChildren().stream()
            .filter(node -> node instanceof Pipe)
            .forEach(pipe -> {
                pipe.setTranslateX(pipe.getTranslateX() - PIPE_SPEED);
                if (pipe.getTranslateX() < -50) {
                    getChildren().remove(pipe);
                }
            });
        
        // AI decision making
        makeAIDecision();
        
        // Check collisions
        checkCollisions();
    }
    
    private void spawnPipe() {
        double height = Math.random() * 300 + 100;
        Pipe topPipe = new Pipe(800, 0, 50, height);
        Pipe bottomPipe = new Pipe(800, height + 150, 50, 600 - height - 150);
        
        getChildren().addAll(topPipe, bottomPipe);
    }
    
    private void makeAIDecision() {
        // Get input features for the neural network
        double[] inputs = new double[] {
            bird.getTranslateY() / 600, // Normalized bird position
            bird.getVelocityY() / 20,   // Normalized bird velocity
            // Add more features as needed
        };
        
        // Get neural network output
        double[] output = neuralNetwork.predict(inputs);
        
        // If output suggests jumping, make the bird jump
        if (output[0] > 0.5) {
            bird.setVelocityY(JUMP_FORCE);
        }
    }
    
    private void checkCollisions() {
        // Check if bird hits the ground or ceiling
        if (bird.getTranslateY() <= 0 || bird.getTranslateY() >= 600) {
            resetGame();
        }
        
        // Check pipe collisions
        getChildren().stream()
            .filter(node -> node instanceof Pipe)
            .forEach(pipe -> {
                if (bird.getBoundsInParent().intersects(pipe.getBoundsInParent())) {
                    resetGame();
                }
            });
    }
    
    private void resetGame() {
        bird.setTranslateY(300);
        bird.setVelocityY(0);
        getChildren().removeIf(node -> node instanceof Pipe);
        lastPipeSpawn = System.currentTimeMillis();
    }
} 