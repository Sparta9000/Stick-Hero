package com.example.stickhero;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MoveMechanics {
    public static Timeline translationMove(Node node, double x, double y, int duration) {
        Timeline timeline;
        if (node instanceof Bridge) {
            timeline = new Timeline(new KeyFrame(Duration.millis(duration),
                    new KeyValue(((Bridge) node).xProperty(), x),
                    new KeyValue(((Bridge) node).yProperty(), y))
            );
        }
        else {
            timeline = new Timeline(new KeyFrame(Duration.millis(duration),
                    new KeyValue(node.layoutXProperty(), x),
                    new KeyValue(node.layoutYProperty(), y))
            );
        }

        timeline.play();

        return timeline;
    }

    public static Timeline translationMove(Node node, double x, double y, int duration, EventHandler<ActionEvent> function) {
        Timeline timeline = translationMove(node, x, y, duration);
        timeline.setOnFinished(function);
        return timeline;
    }

    public static Timeline translationMoveX(Node node, double x, int duration) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(duration),
                new KeyValue(node.layoutXProperty(), x)));
        timeline.play();
        return timeline;
    }

    public static Timeline translationMoveX(Node node, double x, int duration, EventHandler<ActionEvent> function) {
        Timeline timeline = translationMoveX(node, x, duration);
        timeline.setOnFinished(function);
        return timeline;
    }

    public static Timeline diffTranslationMove(Node node, double x, double y, int duration) {
        Timeline timeline;
        if (node instanceof Bridge) {
            timeline = new Timeline(new KeyFrame(Duration.millis(duration),
                    new KeyValue(((Bridge) node).xProperty(), ((Bridge)node).getX() + y),
                    new KeyValue(((Bridge) node).yProperty(), ((Bridge)node).getY() - x))
            );
        }
        else {
            timeline = new Timeline(new KeyFrame(Duration.millis(duration),
                    new KeyValue(node.layoutXProperty(), node.getLayoutX() + x),
                    new KeyValue(node.layoutYProperty(), node.getLayoutY() + y))
            );
        }

        timeline.play();

        return timeline;
    }

    public static Timeline setCollisionHandler(Node n, AnonymousFunction func) {
        int checkEvery = 10;
        Timeline collisionCheck = new Timeline(new KeyFrame(Duration.millis(checkEvery), actionEvent -> {
            if (StickHero.getInstance().getLayoutX() <= n.getLayoutX() && StickHero.getInstance().getLayoutX() + StickHero.getInstance().getFitWidth() >= n.getLayoutX()) {
                if (StickHero.isFlipped()) {
                    func.function();
                }
            }
        }));
        collisionCheck.setCycleCount(Animation.INDEFINITE);
        collisionCheck.play();

        return collisionCheck;
    }
}
