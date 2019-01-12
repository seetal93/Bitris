/*Loads FXML Files*/
package com.bitris.controllers;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Navigator {

    public static final String MAIN    = "/com/bitris/resources.styles/main.fxml";
    public static final String MENU = "/com/bitris/resources.styles/menu.fxml";
    public static final String GAME = "/com/bitris/resources.styles/game.fxml";
    public static final String TUTO = "/com/bitris/resources.styles/tuto.fxml";
    public static final String LEADER = "/com/bitris/resources.styles/leaderboard.fxml";
    
    private static MediaPlayer retro;

    private static MainController mainController;

    public static void setMainController(MainController mainController) {
        
        Navigator.mainController = mainController;
    }

    public static void playMusic() {
        
        retro =new MediaPlayer(new Media(
                Navigator.class.getResource("/com/bitris/resources.audio/retro.wav").toExternalForm()));
        retro.setOnEndOfMedia(() -> {
            retro.seek(Duration.ZERO);
        });
        retro.setVolume(0.8f);
        retro.play();
    }
    
    public static void loadVista(String fxml) {
        
        try {
            mainController.setVista(FXMLLoader.load(Navigator.class.getResource(fxml)));
        } catch (IOException e) {
        }
    }
}