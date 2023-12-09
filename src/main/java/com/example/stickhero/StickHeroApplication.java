package com.example.stickhero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.media.Media;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class StickHeroApplication extends Application {
    private static final double width = 380;
    private static final double height = 775;
    private static int score = 0;
    private static int highScore = 0;
    private static int cherries = 0;
    private static final int reviveThreshold = 3;
    private static Stage stage;
    private static final ArrayList<ClearBackground> threads = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StickHeroApplication.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);

        Media media = new Media(Objects.requireNonNull(StickHeroApplication.class.getResource("bgm.mp3")).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        mediaPlayer.play();

        stage.setTitle("Stick Hero");
        stage.setScene(scene);
        stage.show();
        StickHeroApplication.stage = stage;
    }

    @Override
    public void stop() throws Exception {
        for(ClearBackground t: threads) {
            t.stopExecution();
        }
        super.stop();
    }

    public static void reload() {
        score = 0;
        FXMLLoader fxmlLoader = new FXMLLoader(StickHeroApplication.class.getResource("home.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            stage.setScene(scene);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addThread(ClearBackground t) {
        threads.add(t);
    }

    public static double getWidth() {
        return width;
    }

    public static double getHeight() {
        return height;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        StickHeroApplication.score = score;
    }

    public static int getHighScore() {
        return highScore;
    }

    public static void setHighScore(int highScore) {
        StickHeroApplication.highScore = highScore;
    }

    public static int getCherries() {
        return cherries;
    }

    public static void setCherries(int cherries) {
        StickHeroApplication.cherries = cherries;
    }

    public static int getReviveThreshold() {
        return reviveThreshold;
    }

    public static void main(String[] args) {
        launch();
    }
}