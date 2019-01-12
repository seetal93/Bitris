package com.bitris.data;

import java.util.Random;

public class Targets {
    
    private static final int[] TARGETS = new int[Game.getBoardHeight()];
    private static final int MAX = (int) Math.pow(2, Game.getMaxBitLength()) - 1;
    private static Random rand;
    
    public static void newTarget(int i) {
    
        rand = new Random();
        TARGETS[i] = (Math.abs(rand.nextInt()) % MAX) + 1;
    }
    
    public static int getTarget(int i) { return TARGETS[i]; }
}
