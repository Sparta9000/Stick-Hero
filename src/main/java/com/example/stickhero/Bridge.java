package com.example.stickhero;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Bridge extends Rectangle{
    // composition design pattern
    private static Bridge bridge;
    private static final double width = 4f;
    private static final double heightIncrement = 8f;

    private Bridge(double width, int i) {
        super(width, i);
    }

    public static Bridge getBridge() {
        return bridge;
    }

    public static void makeBridge() {
        if (bridge == null) {
            bridge = new Bridge(width, 0);
            bridge.setX(StickHeroController.getDefaultX() + StickHero.getInstance().getFitWidth() - width / 2);
            bridge.setY(StickHeroController.getDefaultY() + StickHero.getInstance().getFitHeight());
            StickHeroController.getPane().getChildren().add(bridge);
        }
        bridge.setHeight(bridge.getHeight() + heightIncrement);
        bridge.setY(bridge.getY() - heightIncrement);
    }

    public static void drop() {
        Rotate rotate = new Rotate();
        rotate.setPivotX(bridge.getX() + bridge.getWidth()/2);
        rotate.setPivotY(bridge.getY() + bridge.getHeight());

        bridge.getTransforms().add(rotate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(rotate.angleProperty(), 90))
        );

        timeline.setOnFinished(actionEvent -> {
            StickHero.walkBridge();
            StickHero.setPillarCollisionHandler();
        });

        timeline.play();
    }

    public static void resetBridge() {
        bridge = null;
    }
}
