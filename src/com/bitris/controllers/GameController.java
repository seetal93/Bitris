package com.bitris.controllers;

import com.bitris.data.Game;
import com.bitris.graphics.Display;
import com.bitris.logic.Level;
import com.bitris.data.NextBrick;
import com.bitris.logic.Gameplay;
import com.bitris.graphics.GameOver;
import com.bitris.logic.Score;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;

public class GameController {

    private static final int    BRICK_BIT_LENGTH = Game.getBrickBitLength(),
                                BOARD_WIDTH = Game.getBoardWidth(),
                                BOARD_HEIGHT = Game.getBoardHeight();
    
    private static int firstGame = 1;
    
    private static final AudioClip
            BEEP = new AudioClip(GameController.class.
                    getResource("/com/bitris/resources.audio/beep.wav").toExternalForm()),
            NEW_GAME_SOUND = new AudioClip(GameController.class.
                    getResource("/com/bitris/resources.audio/newGame.wav").toExternalForm()),
            BUTTON_SOUND = new AudioClip(GameController.class.
                    getResource("/com/bitris/resources.audio/button.wav").toExternalForm());

    @FXML
    private GridPane gamePanel, nextBrickPanel, targetPanel;
    
    @FXML
    private Button pauseBtn;
    
    @FXML
    private Label gameOverText, nextLevelText, scoreText;
    
    @FXML
    private static GridPane scorePanel;
    
    
    private void initialiseBoard() {
        
        int i,j;
        
        Display.initialiseBoard();

        for (i = 0; i < BOARD_HEIGHT; i++) {
            for (j = 0; j < BOARD_WIDTH; j++) {
                gamePanel.add(Display.getDisplay(i, j), j, i);
                gamePanel.add(Display.getDigits(i, j), j, i);
            }
        }

        for (i = 0; i < BRICK_BIT_LENGTH; i++) {
            for (j = 0; j < BRICK_BIT_LENGTH; j++) {
                nextBrickPanel.add(Display.getNextBrick(i, j), j, i);
            }
        }

        for (i = 0; i < BOARD_HEIGHT; i++) {
            targetPanel.add(Display.getTarget(i, 0), 0, i);
            targetPanel.add(Display.getNumbers(i), 0, i);
        }
        
        Score.initScore();
        
        gamePanel.setFocusTraversable(true);
        nextBrickPanel.setFocusTraversable(true);
        targetPanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        
        gamePanel.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.UP) {
                Level.moveUp();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.DOWN) {
                Level.moveDown();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.RIGHT) {
                Level.moveAcross();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.SPACE) {
                Level.rotate();
                keyEvent.consume();
            }
        });
    }
    
    private void renewBoard() {
        
        Display.renewBoard();
        Score.initScore();

        
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        
        gamePanel.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.UP) {
                Level.moveUp();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.DOWN) {
                Level.moveDown();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.RIGHT) {
                Level.moveAcross();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.SPACE) {
                Level.rotate();
                keyEvent.consume();
            }
        });
    }

    /*handles events when "new game" clicked */
    @FXML
    void startGame(ActionEvent event) {
        
        BEEP.setVolume(0.5f);
        NEW_GAME_SOUND.setVolume(0.2f);
        NEW_GAME_SOUND.play();
        NextBrick.next();
        pauseBtn.setText("Pause");
        
        if(Score.getLevelSpeed() < 400){
            Score.setLevelSpeed(400);
        }
        Score.initScore();
        Score.resetLevel();
        
        if (firstGame == 1) {
        firstGame = 0;
        initialiseBoard();
        Level.addNextPiece();
        }
        else {
            Gameplay.stop();
            Level.resetCounter();
            renewBoard();
            Level.addNextPiece();
        }
        Gameplay.newGame();
        Gameplay.levelUp();
        Gameplay.scoreUp();
    }
    
    @FXML
    void pauseGame(ActionEvent event) {
        
        BUTTON_SOUND.play();
        switch (Gameplay.getStatus()) {
           case Gameplay.RESUME:
               Gameplay.stop();
               pauseBtn.setText("Resume");
               break;
           case Gameplay.PAUSE:
               Gameplay.play();
               gamePanel.requestFocus();
               pauseBtn.setText("Pause");
               break;
           case Gameplay.GAMEOVER:
               break;
           default:
               break;
       }
    }
    
    @FXML
    void previousPane(ActionEvent event) {
        
       BUTTON_SOUND.play();
       if (Gameplay.getStatus() == 1) { Gameplay.stop(); }
       Navigator.loadVista(Navigator.MENU);
    }
    
    @FXML
    private void initialize() {
        
        initialiseBoard();
        GameOver.setup(gameOverText);
        GameOver.nextLevelSetup(nextLevelText);
        GameOver.scoreSetup(scoreText);
    }
    
}