package com.example.stickhero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StickHeroApplication extends Application {
    private static final double width = 380;
    private static final double height = 775;
    private static int score = 0;
    private static int highScore = 0;
    private static int cherries = 0;
    private static final int reviveThreshold = 3;
    private static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StickHeroApplication.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Stick Hero");
        stage.setScene(scene);
        stage.show();
        StickHeroApplication.stage = stage;
    }

    public static void reload() {
        score = 0;
        FXMLLoader fxmlLoader = new FXMLLoader(StickHeroApplication.class.getResource("home.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            stage.setScene(scene);
        }
        catch (Exception e) {
            System.out.println(e);
        }
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