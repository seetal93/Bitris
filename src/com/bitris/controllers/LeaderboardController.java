package com.bitris.controllers;

import com.bitris.graphics.GameOver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LeaderboardController {

    private static final int MAX_ROW = 10;
    private static final int MAX_COL = 2;
    
    private final AudioClip BUTTON_SOUND = new AudioClip(getClass().getResource("/com/bitris/resources.audio/button.wav").toExternalForm());
    
    @FXML
    private GridPane leaderBoard;
    
    @FXML
    void menuPane(ActionEvent event) {
       BUTTON_SOUND.play();
       Navigator.loadVista(Navigator.MENU);
    }
    
    @FXML
    private void initialize() {
        
        GameOver.readFile();
        leaderBoard.setPrefSize(400, 400);
       

        for (int i = 0; i < MAX_ROW; i++) {
            for (int j = 0; j < MAX_COL + 1; j++) {
                Label LBText;
                if (j == 0) {
                    LBText = new Label("" + (i + 1));
                } else {
                    LBText = new Label(GameOver.getLeaderBoardIJ(i, j - 1));
                }
                LBText.setFont(Font.font("Karmatic Arcade", 20));
                LBText.setTextFill(Color.WHITE);
                LBText.setTranslateY(-5);
                leaderBoard.add(LBText, j, i);
            }
        }
        
    }
    
}
