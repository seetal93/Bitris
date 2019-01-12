/*This class is NOT responsible for level change*/
/*Handles checks and the logic of the game*/
package com.bitris.logic;

import com.bitris.data.Game;
import com.bitris.data.Position;
import com.bitris.data.NextBrick;
import com.bitris.graphics.Display;
import com.bitris.data.Board;
import com.bitris.data.Bricks;
import com.bitris.data.Targets;
import java.util.Random;
import javafx.scene.media.AudioClip;

public class Level {
    
    private static final int    BRICK_BIT_LENGTH = Game.getBrickBitLength(),
                                BOARD_WIDTH = Game.getBoardWidth(),
                                BOARD_HEIGHT = Game.getBoardHeight(),
                                ADD_TOP = Game.getAddTopPos();
        
    private static int block_type = 0, cnt = 0;
    private static final Random RAND = new Random();
    
    private static final AudioClip
            BEEP = new AudioClip(Level.class.
                    getResource("/com/bitris/resources.audio/beep.wav").toExternalForm()),
            SCORE = new AudioClip(Level.class.
                    getResource("/com/bitris/resources.audio/score.wav").toExternalForm());
    
    public static void constantMove() {
        
        checkBlockDone();
        moveAcross();
    }
    
    public static void resetCounter() { cnt = 0; }
    
    private static void checkBlockDone() {
        
        if (checkHit())
        {
            BEEP.play();
            becomeWall();
            binaryCheck();
            addNextPiece();
        }
    }
    
    private static void binaryCheck() {
        
        int i, j, k, x;
        String aim;
        boolean targetFound;
        boolean found = false;
        
        for (i = 0; i < BOARD_HEIGHT; i++) {
            aim = Integer.toBinaryString(Targets.getTarget(i));
            for (j = 0; j < BOARD_WIDTH - aim.length() + 1; j++) {
                targetFound = true;
                for (k = j, x = 0; k < j + aim.length(); k++, x++) {
                    if (!Board.convertData(i, k).equals("" + aim.charAt(x))) {
                        targetFound = false;
                    }
                }
                if (targetFound) {
                    for (k = j; k < j + aim.length(); k++) {
                        Board.addData(i, k, 9);
                    }
                    found = true;
                    Targets.newTarget(i);
                    if (i != BOARD_HEIGHT - 1) { i++; }
                }
            }
        }
        if (found) {
            Gameplay.stop();
            SCORE.play();
            /*does not count the first hit*/
            Score.increaseScore();
            cnt++;
            /*resets it after 4 hits*/
            if (cnt == 2){
                Score.nextLevel();
                resetCounter();
            }
            
            try {
                Thread.sleep(500);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            for (i = 0; i < BOARD_HEIGHT; i++) {
                for (j = 0; j < BOARD_WIDTH; j++) {
                    if (Board.getData(i, j) == 9) {
                        Board.addData(i, j, 0);
                    }
                }
            }
            Gameplay.play();
        }
    }

    public static void addNextPiece() {
        
        Position.changeBTOP(ADD_TOP);
        Position.changeBBTM(ADD_TOP + (BRICK_BIT_LENGTH - 1));
        Position.changeBSIDE(BRICK_BIT_LENGTH - 1);
        
        block_type = NextBrick.getNext();
        NextBrick.next();
        Display.displayNextBrick();
        
        if (!checkGameOver()) {
            int i, j, x, y;
            
            for (i = Position.getBTOP(), x = 0; i < Position.getBTOP() + BRICK_BIT_LENGTH; i++, x++) {
                for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1), y = 0; j <= Position.getBSIDE(); j++, y++) {
                    Board.addData(i, j, Bricks.getBlock(block_type)[x][y]);
                }
            }
            randomiseBlockBits();
        }
        else {
            Gameplay.gameOver();
        }
        Display.refreshBoard();
    }
    
    private static void randomiseBlockBits() {
        
        int i, j;
        
        for (i = Position.getBTOP(); i < Position.getBTOP() + BRICK_BIT_LENGTH; i++) {
            for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1); j <= Position.getBSIDE(); j++) {
                if (Board.getData(i, j) == 3) {
                    Board.addData(i, j, (Math.abs(RAND.nextInt()) % 2) + 1);
                }
            }
        }
    }
    
    private static void clearBlock() {
        
        int i, j;
        
        for (i = 0; i < BOARD_HEIGHT; i++) {
            for (j = 0; j < BOARD_WIDTH; j++) {
                if (Board.getData(i, j) > 0) {
                    Board.addData(i, j, 0);
                }
            }
        }
    }
    
    private static int[][] copyBrickData() {
        
        int i, j, I, J;
        int[][] copy = new int[BRICK_BIT_LENGTH][BRICK_BIT_LENGTH];
        
        for (i = Position.getBTOP(), I = 0; i < Position.getBTOP() + BRICK_BIT_LENGTH; i++, I++) {
            for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1), J = 0; j <= Position.getBSIDE(); j++, J++) {
                copy[I][J] = Board.getData(i, j);
            }
        }
        
        return copy;
    }
    
    public static void moveAcross() {
        
        checkBlockDone();
        
        if (!canMoveAcross()) { return; }
        
        int[][] copy = copyBrickData();
        
        int i, j, I, J, empty = 1;

        clearBlock();
        
        for (i = 0; i < BRICK_BIT_LENGTH; i++) {
            if (copy[i][BRICK_BIT_LENGTH - 1] != 0) {
                empty = 0;
            }
        }
        
        if (empty == 1) {
            for (i = Position.getBTOP(), I = 0; i < Position.getBTOP() + BRICK_BIT_LENGTH; i++, I++) {
                for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1), J = 0; j < Position.getBSIDE(); j++, J++) {
                    if (copy[I][J] > 0) {
                        Board.addData(i, j + 1, copy[I][J]);
                    }
                }
            }
            Display.refreshBoard();
            return;
        }
        Position.changeBSIDE(Position.getBSIDE() + 1);
        
        for (i = Position.getBTOP(), I = 0; i < Position.getBTOP() + BRICK_BIT_LENGTH; i++, I++) {
            for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1), J = 0; j <= Position.getBSIDE(); j++, J++) {
                if (copy[I][J] > 0) {
                    Board.addData(i, j, copy[I][J]);
                }
            }
        }
        
        Display.refreshBoard();
    }
    
    private static boolean canMoveAcross() {
        
        int i, j;
        
        if (Position.getBTOP() > 0) {
            for (i = Position.getBTOP(); i < Position.getBTOP() + BRICK_BIT_LENGTH; i++) {
                for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1); j <= Position.getBSIDE(); j++) {
                    if (Board.getData(i, j) > 0 && Board.getData(i, j + 1) < 0) {
                        return false;
                    }
                }
            }
        }
        else {
            for (i = Position.getBTOP(); i < Position.getBTOP() + BRICK_BIT_LENGTH; i++) {
                if (Board.getData(i, BOARD_WIDTH - 1) > 0) {
                    return false;
                }
            }
            for (i = Position.getBTOP(); i < Position.getBTOP() + BRICK_BIT_LENGTH; i++) {
                for (j = BOARD_WIDTH - BRICK_BIT_LENGTH; j < BOARD_WIDTH - 1; j++) {
                    if (Board.getData(i, j) > 0 && Board.getData(i, j + 1) < 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public static void moveUp() {
        
        if (!canMoveUp()) { return; }
        
        int[][] copy = copyBrickData();
        
        int i, j, I, J, empty = 1;

        clearBlock();
        
        for (i = 0; i < BRICK_BIT_LENGTH; i++) {
            if (copy[0][i] != 0) {
                empty = 0;
            }
        }
        
        if (empty == 1) {
            for (i = Position.getBTOP() + 1, I = 1; i < Position.getBTOP() + BRICK_BIT_LENGTH; i++, I++) {
                for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1), J = 0; j <= Position.getBSIDE(); j++, J++) {
                    if (copy[I][J] > 0) {
                        Board.addData(i - 1, j, copy[I][J]);
                    }
                }
            }
            Display.refreshBoard();
            return;
        }
        Position.changeBTOP(Position.getBTOP() - 1);
        Position.changeBBTM(Position.getBBTM() - 1);
        
        for (i = Position.getBTOP(), I = 0; i < Position.getBTOP() + BRICK_BIT_LENGTH; i++, I++) {
            for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1), J = 0; j <= Position.getBSIDE(); j++, J++) {
                if (copy[I][J] > 0) {
                    Board.addData(i, j, copy[I][J]);
                }
            }
        }
        
        Display.refreshBoard();
    }
    
    private static boolean canMoveUp() {
        
        int i, j;
        
        if (Position.getBTOP() > 0) {
            for (i = Position.getBTOP(); i < Position.getBTOP() + BRICK_BIT_LENGTH; i++) {
                for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1); j <= Position.getBSIDE(); j++) {
                    if (Board.getData(i, j) > 0 && Board.getData(i - 1, j) < 0) {
                        return false;
                    }
                }
            }
        }
        else {
            for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1); j <= Position.getBSIDE(); j++) {
                if (Board.getData(0, j) > 0) {
                    return false;
                }
            }
            for (i = 1; i < BRICK_BIT_LENGTH; i++) {
                for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1); j <= Position.getBSIDE(); j++) {
                    if (Board.getData(i, j) > 0 && Board.getData(i - 1, j) < 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public static void moveDown() {
        
        if (!canMoveDown()) { return; }
        
        int[][] copy = copyBrickData();
        
        int i, j, I, J, empty = 1;

        clearBlock();
        
        for (i = 0; i < BRICK_BIT_LENGTH; i++) {
            if (copy[BRICK_BIT_LENGTH - 1][i] != 0) {
                empty = 0;
            }
        }
        
        if (empty == 1) {
            for (i = Position.getBTOP(), I = 0; i < Position.getBTOP() + (BRICK_BIT_LENGTH - 1); i++, I++) {
                for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1), J = 0; j <= Position.getBSIDE(); j++, J++) {
                    if (copy[I][J] > 0) {
                        Board.addData(i + 1, j, copy[I][J]);
                    }
                }
            }
            Display.refreshBoard();
            return;
        }
        Position.changeBTOP(Position.getBTOP() + 1);
        Position.changeBBTM(Position.getBBTM() + 1);
        
        for (i = Position.getBTOP(), I = 0; i < Position.getBTOP() + BRICK_BIT_LENGTH; i++, I++) {
            for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1), J = 0; j <= Position.getBSIDE(); j++, J++) {
                if (copy[I][J] > 0) {
                    Board.addData(i, j, copy[I][J]);
                }
            }
        }
        
        Display.refreshBoard();
    }
    
    private static boolean canMoveDown() {
        
        int i, j;
        
        if (Position.getBBTM() < (BOARD_HEIGHT - 1)) {
            for (i = Position.getBTOP(); i < Position.getBTOP() + BRICK_BIT_LENGTH; i++) {
                for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1); j <= Position.getBSIDE(); j++) {
                    if (Board.getData(i, j) > 0 && Board.getData(i + 1, j) < 0) {
                        return false;
                    }
                }
            }
        }
        else {
            for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1); j <= Position.getBSIDE(); j++) {
                if (Board.getData(Position.getBBTM(), j) > 0) {
                    return false;
                }
            }
            for (i = Position.getBTOP(); i < Position.getBTOP() + (BRICK_BIT_LENGTH - 1); i++) {
                for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1); j <= Position.getBSIDE(); j++) {
                    if (Board.getData(i, j) > 0 && Board.getData(i + 1, j) < 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private static int[][] rotateMatrixRight(int[][] matrix)
    {
        int w = matrix.length, h = matrix[0].length, i, j;
        int[][] ret = new int[h][w];
        for (i = 0; i < h; ++i) {
            for (j = 0; j < w; ++j) {
                ret[i][j] = matrix[w - j - 1][i];
            }
        }
        return ret;
    }
    
    public static void rotate() {
        
        if (!canRotate()) { return; }
        
        int[][] copy = copyBrickData();
        copy = rotateMatrixRight(copy);
        
        int i, j, I, J;
        
        clearBlock();
        
        for (i = Position.getBTOP(), I = 0; i < Position.getBTOP() + BRICK_BIT_LENGTH; i++, I++) {
            for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1), J = 0; j <= Position.getBSIDE(); j++, J++) {
                if (copy[I][J] > 0) {
                    Board.addData(i, j, copy[I][J]);
                }
            }
        }
        
        Display.refreshBoard();      
    }
    
    private static boolean canRotate() {
        
        int[][] copy = copyBrickData();
        copy = rotateMatrixRight(copy);
        
        int i, j, I, J;
        
        for (i = Position.getBTOP(), I = 0; i < Position.getBTOP() + BRICK_BIT_LENGTH; i++, I++) {
            for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1), J = 0; j <= Position.getBSIDE(); j++, J++) {
                if (copy[I][J] > 0 && Board.getData(i, j) < 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static boolean checkHit() {
        
        int i, j;
        
        if (Position.getBSIDE() < (BOARD_WIDTH - 1)) {
            for (i = Position.getBTOP(); i < Position.getBTOP() + BRICK_BIT_LENGTH; i++) {
                for (j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1); j <= Position.getBSIDE(); j++) {
                    if (Board.getData(i, j) > 0 && Board.getData(i, j + 1) < 0) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }
    
    private static boolean checkGameOver() {
        
        int i, j, x, y;
        
        for (x = 0, i = Position.getBTOP(); x < BRICK_BIT_LENGTH; x++, i++) {
            for (y = 0, j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1); y < BRICK_BIT_LENGTH; y++, j++) {
                if (Bricks.getBlock(block_type)[x][y] == 3 && Board.getData(i, j) < 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static void becomeWall() {
        
        int[][] copy = copyBrickData();
        
        clearBlock();
        
        int i, j, I, J;
        
        for (I = 0, i = Position.getBTOP(); I < BRICK_BIT_LENGTH; I++, i++) {
            for (J = 0, j = Position.getBSIDE() - (BRICK_BIT_LENGTH - 1); J < BRICK_BIT_LENGTH; J++, j++) {
                if (copy[I][J] > 0) {
                    Board.addData(i, j, - copy[I][J]);
                }
            }
        }
    }

    
}
