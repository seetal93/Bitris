package com.bitris.data;

import java.util.Random;

public class NextBrick {
    
    private static int next_brick;
    private static final Random RAND = new Random();
    
    public static void next() { next_brick = Math.abs(RAND.nextInt()) % (Bricks.getNumberOfBricks() + 1); }
    public static int getNext() { return next_brick; }
}
