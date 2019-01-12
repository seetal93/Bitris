package com.bitris.data;

public class Bricks {
    
    private static final int [][][] BLOCK_DESIGNS =  {{ {  0, 0, 0  },
                                                        {  3, 3, 0  },
                                                        {  3, 3, 0  } },

                                                      { {  0, 0, 0  },
                                                        {  3, 0, 0  },
                                                        {  3, 3, 3  } },

                                                      { {  3, 3, 3  },
                                                        {  3, 0, 0  },
                                                        {  0, 0, 0  } },

                                                      { {  3, 3, 0  },
                                                        {  0, 3, 3  },
                                                        {  0, 0, 0  } },

                                                      { {  3, 3, 3  },
                                                        {  0, 3, 0  },
                                                        {  0, 0, 0  } },

                                                      { {  3, 3, 3  },
                                                        {  0, 0, 0  },
                                                        {  0, 0, 0  } }};
    
    public static int[][] getBlock(int x) { return BLOCK_DESIGNS[x]; }
    public static int getNumberOfBricks() { return BLOCK_DESIGNS.length - 1; }
}
