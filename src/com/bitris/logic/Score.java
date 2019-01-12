/*Handles the score and level change*/
package com.bitris.logic;

import com.bitris.graphics.GameOver;
import javafx.fxml.FXML;

public class Score{
    
    @FXML
    private static int score;
    private static int levelSpeed = 400;
    private static int levelNumber = 1;
        
    public static void nextLevel(){
        levelNumber++;
        GameOver.nextLevel();
        levelSpeed = levelSpeed - 10;

        if(levelNumber == 40){
            Gameplay.gameOver();
        }
                
    }
    
    public static int getLevelNumber()      { return levelNumber; }
    public static int getLevelSpeed()       { return levelSpeed; }
    public static void setLevelSpeed(int x) { levelSpeed = x; }
    
    public static void resetLevel(){
        levelNumber = 1;
        levelSpeed = 400;
    }

    public static void initScore()  { score = 0; }
    public static int getScore()    { return score; }
    
    public static void increaseScore(){
       score++;
       GameOver.updateScore();
    }

}