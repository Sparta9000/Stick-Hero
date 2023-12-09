package com.example.stickhero;

import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class StickHero {
    private static ImageView instance;
    private static boolean flipped = false;
    private static Timeline currentTimeline;
    private static Timeline cherryCollisionHandler;
    private static Timeline pillarCollisionHandler;

    // singleton design pattern
    public static void setInstance(ImageView i) {
        instance = i;
    }

    public static ImageView getInstance() {
        return instance;
    }

    public static void walkBridge() {
        Bridge b = Bridge.getBridge();
        Rectangle nextPillar = Pillar.getNextPillar();
        if ((b.getHeight() >= nextPillar.getLayoutX() - b.getX()) && (b.getHeight() <= nextPillar.getLayoutX() + nextPillar.getWidth() - b.getX() - b.getWidth())) {
            currentTimeline = MoveMechanics.translationMoveX(instance, nextPillar.getLayoutX() + nextPillar.getWidth() - instance.getFitWidth(), 1000, actionEvent -> StickHeroController.nextLevel());
        } else {
            currentTimeline = MoveMechanics.translationMoveX(instance, instance.getLayoutX() + b.getHeight() + instance.getFitWidth(), 500, actionEvent -> MoveMechanics.translationMove(instance, instance.getLayoutX(), StickHeroApplication.getHeight() + instance.getFitHeight() + 10, 500, actionEvent1 -> StickHeroController.gameOver()));
        }
    }

    public static void setPos(double x, double y) {
        instance.setLayoutX(x);
        instance.setLayoutY(y);
    }

    public static boolean isFlipped() {
        return flipped;
    }

    public static void stopCurrentTimeline() {
        currentTimeline.stop();
    }

    public static void setPillarCollisionHandler() {
        if (pillarCollisionHandler != null) {
            pillarCollisionHandler.stop();
        }
        StickHero.pillarCollisionHandler = MoveMechanics.setCollisionHandler(Pillar.getNextPillar(), () -> {
           stopCurrentTimeline();
           pillarCollisionHandler.stop();
           pillarCollisionHandler = null;
           MoveMechanics.translationMove(instance, instance.getLayoutX(), StickHeroApplication.getHeight() + instance.getFitHeight() + 10, 500, actionEvent1 -> StickHeroController.gameOver());
        });
    }

    public static void setCherryCollisionHandler() {
        if (cherryCollisionHandler != null) {
            cherryCollisionHandler.stop();
        }
        StickHero.cherryCollisionHandler = MoveMechanics.setCollisionHandler(Cherry.getCherry(), () -> {
            cherryCollisionHandler.stop();
            cherryCollisionHandler = null;
            Cherry.getCherry().setVisible(false);
            StickHeroApplication.setCherries(StickHeroApplication.getCherries() + 1);
            StickHeroController.updateCherries();
        });
    }

    public static void flip() {
        if (!flipped) {
            instance.setLayoutY(instance.getLayoutY() + instance.getFitHeight());
            instance.setScaleY(-1);
            flipped = true;
        }
        else {
            instance.setLayoutY(instance.getLayoutY() - instance.getFitHeight());
            instance.setScaleY(1);
            flipped = false;
        }
    }

    public static void reset() {
        if (flipped) {
            flip();
        }
    }
}
