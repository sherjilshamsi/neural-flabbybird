package com.flappybird.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pipe extends Rectangle {
    public Pipe(double x, double y, double width, double height) {
        super(width, height);
        setTranslateX(x);
        setTranslateY(y);
        setFill(Color.GREEN);
    }
} 