package com.example.stickhero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cherry {
    private static ImageView cherry;
    private static final int size = 40;

    public static void spawnCherry(double x, double y) {
        cherry = new ImageView(new Image(String.valueOf(Cherry.class.getResource("cherry.png"))));
        cherry.setLayoutX(x);
        cherry.setLayoutY(y);
        cherry.setFitHeight(size);
        cherry.setFitWidth(size);
        StickHeroController.getPane().getChildren().add(cherry);
        StickHero.setCherryCollisionHandler();
    }

    public static ImageView getCherry() {
        return cherry;
    }

    public static int getSize() {
        return size;
    }

    public static void reset() {
        cherry = null;
    }
}
