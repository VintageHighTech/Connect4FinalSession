package com.vintagehightech.projects.connect4.connect4restfulwebservices;

public class TestBoards {

    // Almost full board to check moves made by AI
    int[][] test1 = new int[][]
            {
                    {1,2,1,2,0,0},
                    {1,2,1,2,0,0},
                    {1,2,1,2,0,0},
                    {2,1,2,1,0,0},
                    {2,1,2,1,0,0},
                    {2,1,2,2,0,0},
                    {1,2,1,0,0,0}
            };

    // Yellow's next move is a win
    int[][] test2 = new int[][]
            {
                    {1,2,1,2,0,0},
                    {1,1,1,2,0,0},
                    {1,2,1,2,0,0},
                    {2,1,2,0,0,0},
                    {2,1,2,1,0,0},
                    {2,1,2,2,0,0},
                    {1,2,1,0,0,0}
            };

    // Red's next move is a win - two winning positions!
    int[][] test3 = new int[][]
            {
                    {2,2,1,2,0,0},
                    {1,2,1,2,0,0},
                    {1,2,2,2,0,0},
                    {2,1,2,0,0,0},
                    {2,1,2,1,0,0},
                    {2,1,2,2,0,0},
                    {1,2,1,0,0,0}
            };

    // Red's next move is a win - one diagonal winning position!
    int[][] test4 = new int[][]
            {
                    {1,2,1,2,0,0},
                    {1,2,1,2,0,0},
                    {1,2,1,2,0,0},
                    {2,1,2,1,0,0},
                    {2,2,2,1,0,0},
                    {2,1,2,2,0,0},
                    {1,2,1,0,0,0}
            };

    // Red's next move is a win - placed inside existing three discs.
    int[][] test5 = new int[][]
            {
                    {1,2,1,2,0,0},
                    {1,2,1,2,0,0},
                    {1,2,1,0,0,0},
                    {2,1,2,2,0,0},
                    {2,1,2,1,0,0},
                    {2,1,2,2,0,0},
                    {1,2,1,0,0,0}
            };

    int[][] test6 = new int[][]
            {
                    {2,2,1,2,0,0},
                    {1,2,1,2,0,0},
                    {1,2,0,0,0,0},
                    {2,1,2,2,0,0},
                    {2,1,2,1,0,0},
                    {2,1,2,2,0,0},
                    {1,2,1,0,0,0}
            };

    int[][] test7 = new int[][]
            {
                    {1,2,1,2,1,2},
                    {1,2,1,2,1,2},
                    {1,2,1,2,1,2},
                    {2,1,2,1,2,1},
                    {2,1,2,1,2,0},
                    {2,1,2,2,1,0},
                    {1,2,1,1,2,0}
            };
}
