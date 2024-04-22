package com.help.chess;

public class MaxNKnight {
    static final int N = 5;

    /* A utility function to print solution */
    static void printSolution(int[][] board) {
        System.out.println("Maximum number of knights placed: ");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(" " + board[i][j] + " ");
            System.out.println();
        }
    }

    /* check to see if it is safe to put the knight in x,y position */
    static boolean isSafe(int[][] board, int x, int y) {
        return (x >= 0 && y >= 0 && x < N && y < N && board[x][y] == 0);
    }

    /* This function solves the N Knights problem using Backtracking.
    It returns the maximum number of knights that can be placed on the board.*/
    static int maxNK() {
        int[][] board = new int[N][N];
        int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };
        int maxKnights = 0;

        // initialize all the positions to 0
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                board[i][j] = 0;

        // Place knights one by one and count the maximum number of knights
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    int count = 0;
                    board[i][j] = 1; // Place the knight
                    count++;

                    // Check for safe positions for other knights
                    for (int k = 0; k < 8; k++) {
                        int newRow = i + dx[k];
                        int newCol = j + dy[k];
                        if (isSafe(board, newRow, newCol)) {
                            board[newRow][newCol] = 1;
                            count++;
                        }
                    }
                    maxKnights = Math.max(maxKnights, count);
                }
            }
        }

        printSolution(board); // Print the board with maximum number of knights placed
        return maxKnights;
    }

    // driver program to test above function
    public static void main(String[] args) {
        int maxKnights = maxNK();
        System.out.println("Maximum number of knights that can be placed: " + maxKnights);
    }
}
