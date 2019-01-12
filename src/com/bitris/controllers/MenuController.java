package com.bitris.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.AudioClip;

public class MenuController {
    
    private final AudioClip BUTTON_SOUND = new AudioClip(getClass().getResource("/com/bitris/resources.audio/button.wav").toExternalForm());

    @FXML
    void nextPane(ActionEvent event) {
        
        BUTTON_SOUND.play();
        Navigator.loadVista(Navigator.GAME);
    }

    @FXML
    void tutoPane(ActionEvent event) {
        
        BUTTON_SOUND.play();
        Navigator.loadVista(Navigator.TUTO);
    }
    
    @FXML
    void menuPane(ActionEvent event) {
       
       BUTTON_SOUND.play();
       Navigator.loadVista(Navigator.MENU);
    }
    
    @FXML
    void leaderPane(ActionEvent event) {
        
        BUTTON_SOUND.play();
        Navigator.loadVista(Navigator.LEADER);
    }
    
}