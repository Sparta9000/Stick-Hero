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

    public static double getNextPillarX() {
        return StickHeroController.rand.nextDouble(currentPillar.getLayoutX() + currentPillar.getWidth() + 10, StickHeroApplication.getWidth()-10);
    }

    public static double getNextPillarXCoord() {
        return StickHeroController.rand.nextDouble(10, StickHeroApplication.getWidth());
    }

    public static double getNextPillarWidth(double x) {
        return StickHeroController.rand.nextDouble(10, StickHeroApplication.getWidth() - x);
    }

    public static void generateNextPillar() {
        double x = getNextPillarX();
        double width = getNextPillarWidth(x);

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
