package com.bitris.data;

public class Position {
    
    private static int b_top, b_btm, b_side;
    
    public static void changeBTOP(int change)   { b_top = change; }
    public static void changeBBTM(int change)   { b_btm = change; }
    public static void changeBSIDE(int change)  { b_side = change; }
    
    public static int getBTOP()     { return b_top; }
    public static int getBBTM()     { return b_btm; }
    public static int getBSIDE()    { return b_side; }
}
