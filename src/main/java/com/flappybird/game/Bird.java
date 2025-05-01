package com.flappybird.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bird extends Circle {
    private double velocityY;
    
    public Bird(double x, double y) {
        super(15);
        setTranslateX(x);
        setTranslateY(y);
        setFill(Color.YELLOW);
        velocityY = 0;
    }
    
    public double getVelocityY() {
        return velocityY;
    }
    
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
} 