package com.bitris.data;

public class Game {
    
    private static final int    BRICK_SIZE = 50,
                                BRICK_BIT_LENGTH = 3,
                                BOARD_WIDTH = 900/BRICK_SIZE,
                                BOARD_HEIGHT = 550/BRICK_SIZE,
                                ADD_TOP = (int) (BOARD_HEIGHT - 2)/2,
                                MAX_BIT_LENGTH = 4;
    
    public static int getBrickSize()       { return BRICK_SIZE; }
    public static int getBrickBitLength()  { return BRICK_BIT_LENGTH; }
    public static int getBoardWidth()      { return BOARD_WIDTH; }
    public static int getBoardHeight()     { return BOARD_HEIGHT; }
    public static int getAddTopPos()       { return ADD_TOP; }
    public static int getMaxBitLength()    { return MAX_BIT_LENGTH; }
}
