package com.bitris.data;

public class Board {
    
    private static final int BOARD_WIDTH = Game.getBoardWidth(),
                             BOARD_HEIGHT = Game.getBoardHeight();
    
    private static final int[][] BOARD_DATA = new int[BOARD_HEIGHT][BOARD_WIDTH];
    
    public static int getData(int i, int j)             { return BOARD_DATA[i][j]; }
    public static void addData(int i, int j, int data)  { BOARD_DATA[i][j] = data; }
    
    public static String convertData(int i, int j) {
        
        switch(BOARD_DATA[i][j]) {
            case -2: return "1";
            case -1: return "0";
            case 1: return "0";
            case 2: return "1";
            case 9: return "9";
            default: return "";
        }
    }
}
