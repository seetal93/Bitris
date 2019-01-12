package com.bitris.graphics;

import com.bitris.data.Game;
import com.bitris.data.Board;
import com.bitris.data.Bricks;
import com.bitris.data.NextBrick;
import com.bitris.data.Targets;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Display {
    
    private static final int    BRICK_SIZE = Game.getBrickSize(),
                                BRICK_BIT_LENGTH = Game.getBrickBitLength(),
                                BOARD_WIDTH = Game.getBoardWidth(),
                                BOARD_HEIGHT = Game.getBoardHeight();
    
    private static Rectangle[][] displayMatrix, nextBrickMatrix, targetMatrix;
    
    private static Label[][] digitMatrix;
    
    private static Label[] numbersMatrix;
    
    private static final Color TARGETCOLOUR = Color.WHITE;

    private static Color getColor(int data) {
        
        switch (data) {
            case 1: return Color.web("#DC6D37");
            case 2: return Color.web("#FB9845");
            case -2: return Color.DARKGREY;
            case -1: return Color.GREY;
            default: return Color.TRANSPARENT;
        }
    }
    
    public static Rectangle getDisplay(int i, int j)    { return displayMatrix[i][j]; }
    public static Rectangle getNextBrick(int i, int j)  { return nextBrickMatrix[i][j]; }
    public static Rectangle getTarget(int i, int j)     { return targetMatrix[i][j]; } 
    public static Label getDigits(int i, int j)         { return digitMatrix[i][j]; }
    public static Label getNumbers(int i)               { return numbersMatrix[i]; }
    
    public static void refreshBoard() {
        
        int i, j;
        
        for (i = 0; i < BOARD_HEIGHT; i++)
        {
            for (j = 0; j < BOARD_WIDTH; j++)
            {
                displayMatrix[i][j].setFill(getColor(Board.getData(i, j)));
                digitMatrix[i][j].setText("  " + Board.convertData(i, j));
            }
            targetMatrix[i][0].setFill(TARGETCOLOUR);
            numbersMatrix[i].setText("" + Targets.getTarget(i));
        }
    }
    
    public static void initialiseBoard() {
        
        int i,j;
        
        displayMatrix = new Rectangle[BOARD_HEIGHT][BOARD_WIDTH];
        nextBrickMatrix = new Rectangle[BRICK_BIT_LENGTH][BRICK_BIT_LENGTH];
        targetMatrix = new Rectangle[BOARD_HEIGHT][1];
        digitMatrix = new Label[BOARD_HEIGHT][BOARD_WIDTH];
        numbersMatrix = new Label[BOARD_HEIGHT];
        
        for (i = 0; i < BOARD_HEIGHT; i++) {
            Label target = new Label();
            target.setFont(Font.font("Karmatic Arcade", 25));
            target.setTextFill(Color.web("#111641"));
            target.setTranslateY(-5);
            numbersMatrix[i] = target;
            Targets.newTarget(i);
            for (j = 0; j < BOARD_WIDTH; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                Label digit = new Label();
                digit.setFont(Font.font("Karmatic Arcade", 25));
                digit.setTextFill(Color.web("#111641"));
                digit.setTranslateY(-5);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setArcHeight(14);
                rectangle.setArcWidth(14);
                displayMatrix[i][j] = rectangle;
                digitMatrix[i][j] = digit;
                Board.addData(i, j, 0);
            }
        }
        
        for (i = 0; i < BRICK_BIT_LENGTH; i++) {
            for (j = 0; j < BRICK_BIT_LENGTH; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setArcHeight(14);
                rectangle.setArcWidth(14);
                nextBrickMatrix[i][j] = rectangle;
            }
        }
        
        for (i = 0; i < BOARD_HEIGHT; i++) {
            Rectangle rectangle = new Rectangle(80, BRICK_SIZE*0.8);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setArcHeight(14);
            rectangle.setArcWidth(14);
            targetMatrix[i][0] = rectangle;
        }
    }
    
    public static void renewBoard() {
        
        int i,j;
        
        for (i = 0; i < BOARD_HEIGHT; i++) {
            Targets.newTarget(i);
            for (j = 0; j < BOARD_WIDTH; j++) {
                displayMatrix[i][j].setFill(Color.TRANSPARENT);
                Board.addData(i, j, 0);
            }
        }
    }
    
    public static void displayNextBrick() {
        
        int i, j;
        
        for (i = 0; i < BRICK_BIT_LENGTH; i++) {
            for (j = 0; j < BRICK_BIT_LENGTH; j++) {
                if (Bricks.getBlock(NextBrick.getNext())[i][j] == 3) {
                    nextBrickMatrix[i][j].setFill(Color.WHITE);
                }
                else {
                    nextBrickMatrix[i][j].setFill(Color.TRANSPARENT);
                }
            }
        }
    }
}
