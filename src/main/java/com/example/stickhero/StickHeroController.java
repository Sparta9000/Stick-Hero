package com.example.stickhero;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class StickHeroController implements Initializable {
    private static Scene scene;
    @FXML
    public static Label cherryScore;
    @FXML
    private Label titleText;
    @FXML
    private static Pane pane;
    @FXML
    private Button playButton;
    @FXML
    private ImageView stickman;
    @FXML
    private static Rectangle startingPillar;
    @FXML
    private static Label scoreLabel;
    @FXML
    private ImageView cherryImg;

    public static final Random rand = new Random();

    private static double defaultX;
    private static double defaultY;
    private static double defaultPillarY;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleText.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                scene = titleText.getScene();
                defaultX = StickHeroApplication.getWidth() / 6.33333;
                defaultY = StickHeroApplication.getHeight() / 1.35964;
                defaultPillarY = defaultY + stickman.getFitHeight();
                pane = (Pane) scene.lookup("#pane");
                scoreLabel = (Label) scene.lookup("#scoreLabel");
                startingPillar = (Rectangle) scene.lookup("#startingPillar");
                cherryScore = (Label) scene.lookup("#cherryScore");
                StickHero.setInstance(stickman);
                Pillar.setCurrentPillar(startingPillar);
                playButton.setOnAction(actionEvent -> startGame());
            }
        });
    }

    public void startGame() {
        playButton.setVisible(false);
        titleText.setVisible(false);
        scoreLabel.setVisible(true);
        cherryImg.setVisible(true);
        cherryScore.setVisible(true);
        playButton.setOnAction(actionEvent -> {});
        runStartAnimation();
    }

    public static void nextLevel() {
        removeMouseInput();
        Timeline timeline = null;
        double diff = StickHero.getInstance().getLayoutX() - defaultX;
        for (Node n:
             pane.getChildren()) {
            if ((n.getId() == null) || (!n.getId().equals("backgroundImg") && !n.getId().equals("scoreLabel") && !n.getId().equals("cherryImg") && !n.getId().equals("cherryScore"))) {
                timeline = MoveMechanics.diffTranslationMove(n, -diff, 0, 200);
            }
        }
        StickHeroApplication.setScore(StickHeroApplication.getScore() + 1);
        scoreLabel.setText(String.valueOf(StickHeroApplication.getScore()));
        Pillar.setCurrentPillar(Pillar.getNextPillar());
        if (timeline != null) {
            timeline.setOnFinished(event -> Pillar.generateNextPillar());
        }
    }

    public void runStartAnimation() {
        MoveMechanics.translationMove(StickHero.getInstance(), defaultX, defaultY, 100);
        MoveMechanics.translationMove(Pillar.getCurrentPillar(), defaultX + 40 - Pillar.getCurrentPillar().getWidth(), defaultPillarY, 100, actionEvent -> Pillar.generateNextPillar());
    }

    public static Pane getPane() {
        return pane;
    }

    public static double getDefaultPillarY() {
        return defaultPillarY;
    }

    public static double getDefaultX() {
        return defaultX;
    }

    public static double getDefaultY() {
        return defaultY;
    }

    public static void addMouseInput() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long l) {
                long now = System.currentTimeMillis();
                if (now - lastUpdate >= 10) {
                    Bridge.makeBridge();
                    lastUpdate = now;
                }
            }
        };

        scene.setOnMousePressed(mouseEvent -> timer.start());

        scene.setOnMouseReleased(mouseEvent -> {
            timer.stop();
            addFlipInput();
            Bridge.drop();
        });
    }

    public static void addFlipInput() {
        scene.setOnMousePressed(mouseEvent -> StickHero.flip());
        scene.setOnMouseReleased(mouseEvent -> {});
    }

    public static void removeMouseInput() {
        scene.setOnMousePressed(mouseEvent -> {});
        scene.setOnMouseReleased(mouseEvent -> {});
    }

    public static void updateCherries() {
        cherryScore.setText(String.valueOf(StickHeroApplication.getCherries()));
    }

    public static void revive() {
        updateCherries();
        StickHero.reset();
        pane.getChildren().remove(Bridge.getBridge());
        Bridge.resetBridge();
        StickHero.setPos(defaultX, defaultY);
        addMouseInput();
    }

    public static void gameOver() {
        removeMouseInput();
        if (StickHeroApplication.getScore() > StickHeroApplication.getHighScore()) StickHeroApplication.setHighScore(StickHeroApplication.getScore());
        FXMLLoader loader = new FXMLLoader(StickHeroApplication.class.getResource("gameover.fxml"));
        try {
            Parent popup = loader.load();
            pane.getChildren().add(popup);
            ((Label)popup.lookup("#score")).setText(String.valueOf(StickHeroApplication.getScore()));
            ((Label)popup.lookup("#highScore")).setText(String.valueOf(StickHeroApplication.getHighScore()));
            ((Button)popup.lookup("#home")).setOnAction(mouseEvent -> StickHeroApplication.reload());
            Button reviveButton = ((Button)popup.lookup("#revive"));
            if (StickHeroApplication.getCherries() < StickHeroApplication.getReviveThreshold()) reviveButton.setDisable(true);
            reviveButton.setOnAction(actionEvent -> {
                pane.getChildren().remove(popup);
                StickHeroApplication.setCherries(StickHeroApplication.getCherries() - StickHeroApplication.getReviveThreshold());
                revive();
            });
            ((Button)popup.lookup("#restart")).setOnAction(mouseEvent -> {
                pane.getChildren().remove(popup);
                restart();
            });
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void restart() {
        StickHero.reset();
        StickHeroApplication.setScore(0);
        scoreLabel.setText(String.valueOf(0));
        pane.getChildren().removeIf(n -> n.getId() == null);
        Pillar.setCurrentPillar(startingPillar);
        startingPillar.setLayoutY(defaultPillarY);
        startingPillar.setLayoutX(defaultX + StickHero.getInstance().getFitWidth() - startingPillar.getWidth());
        StickHero.setPos(defaultX, defaultY);
        Pillar.generateNextPillar();
    }

    public static void spawnCherry() {
        if (Pillar.getNextPillar().getLayoutX() - Pillar.getCurrentPillar().getLayoutX() - Pillar.getCurrentPillar().getWidth() > 3 * StickHero.getInstance().getFitWidth()){
            //spawn cherry
            Cherry.spawnCherry((Pillar.getNextPillar().getLayoutX() + Pillar.getCurrentPillar().getLayoutX() + Pillar.getCurrentPillar().getWidth() - Cherry.getSize()) / 2, defaultY + (double) Cherry.getSize());
        }
        else {
            Cherry.reset();
        }
    }
}