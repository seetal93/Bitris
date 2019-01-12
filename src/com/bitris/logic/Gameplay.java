package com.bitris.logic;

import com.bitris.graphics.GameOver;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Gameplay {
    
    private static Timeline timeline;
    private static int playing;
    
    
    
    private static final int MAX_LEVEL_NUMBER = 40;
    public static final int PAUSE = 0;
    public static final int RESUME = 1;
    public static final int GAMEOVER = 2;
    
    public static void newGame() {
        
        timeline = new Timeline(new KeyFrame(Duration.millis(Score.getLevelSpeed()), ae -> Level.constantMove()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        GameOver.newGame();
        play();
        
    }
    
    public static void stop()       { timeline.stop(); playing = PAUSE; }
    public static void play()       { timeline.play(); playing = RESUME; }
    public static void levelUp()    { GameOver.nextLevel();}
    public static void scoreUp()    { GameOver.updateScore();}
    public static void gameOver()   { timeline.stop(); playing = GAMEOVER; GameOver.gameOver(); }
    public static int getStatus()   { return playing; }
}
