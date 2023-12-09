package com.example.stickhero;

import javafx.scene.shape.Rectangle;

public class Pillar {
    private static Rectangle currentPillar;
    private static Rectangle nextPillar;

    public static void setCurrentPillar(Rectangle currentPillar) {
        Pillar.currentPillar = currentPillar;
    }

    public static Rectangle getCurrentPillar() {
        return currentPillar;
    }

    public static Rectangle getNextPillar() {
        return nextPillar;
    }

    public static void generateNextPillar() {
        double x = StickHeroController.rand.nextDouble(currentPillar.getLayoutX() + currentPillar.getWidth() + 10, StickHeroApplication.getWidth()-10);
        double width = StickHeroController.rand.nextDouble(10, StickHeroApplication.getWidth() - x);

        nextPillar = new Rectangle();
        nextPillar.setLayoutX(StickHeroApplication.getWidth() + 10);
        nextPillar.setLayoutY(StickHeroController.getDefaultPillarY());
        nextPillar.setWidth(width);
        nextPillar.setHeight(currentPillar.getHeight());
        nextPillar.setStroke(currentPillar.getStroke());
        nextPillar.setArcHeight(currentPillar.getArcHeight());
        nextPillar.setArcWidth(currentPillar.getArcWidth());
        nextPillar.setStrokeType(currentPillar.getStrokeType());

        StickHeroController.getPane().getChildren().add(nextPillar);

        MoveMechanics.translationMove(nextPillar, x, StickHeroController.getDefaultPillarY(), 100, actionEvent -> {
            StickHeroController.addMouseInput();
            Bridge.resetBridge();
            StickHeroController.spawnCherry();
        });
    }
}
