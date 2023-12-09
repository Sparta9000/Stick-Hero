package com.example.stickhero;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;

public class ClearBackground extends Thread{
    private boolean running = true;
    @Override
    public void run() {
        Pane pane = StickHeroController.getPane();
        while (pane == null) {
            pane = StickHeroController.getPane();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        while(running) {
            for (Node node: StickHeroController.getPane().getChildren()) {
                double x;
                double y;
                double w = 0;
                double h = 0;
                x = node.getLayoutX();
                y = node.getLayoutY();
                if (node instanceof Bridge) {
                    x = ((Bridge)node).getX();
                    y = ((Bridge)node).getY();
                    w = ((Bridge)node).getHeight();
                    h = ((Bridge)node).getWidth();
                }
                else if (node instanceof ImageView) {
                    w = ((ImageView)node).getFitWidth();
                    h = ((ImageView)node).getFitHeight();
                }
                else if (node instanceof Rectangle) {
                    w = ((Rectangle)node).getWidth();
                    h = ((Rectangle)node).getHeight();
                }
                else if (node instanceof Button) {
                    w = ((Button)node).getWidth();
                    h = ((Button)node).getHeight();
                }
                else if (node instanceof Label) {
                    w = ((Label)node).getWidth();
                    h = ((Label)node).getHeight();
                }

                if ((x + w < 0) && (y + h < 0)) {
                    pane.getChildren().remove(node);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stopExecution() {
        running = false;
    }
}
