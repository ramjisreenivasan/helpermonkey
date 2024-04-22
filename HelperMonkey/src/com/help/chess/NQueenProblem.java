package com.help.chess;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

class NQueenProblem  extends JPanel {
    static final int N = 6;

    static final int CELL_SIZE = 50; // Size of each cell in pixels

    static int[][] board;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
    	//System.out.println("Draw board:::");
        int width = getWidth();
        int height = getHeight();

        // Draw the grid
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                if (board[row][col] == 1) {
                    g.setColor(Color.RED);
                    g.fillOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 15, CELL_SIZE - 15);
                    //g.drawOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 12, CELL_SIZE - 12);
                    //g.drawOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                }
                if (board[row][col] == -1) {
                    g.setColor(Color.RED);
                    g.drawOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 15, CELL_SIZE - 15);
                    //g.drawOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 12, CELL_SIZE - 12);
                    //g.drawOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                }
            }
        }
    }
    
    // A utility function to print the solution
    static void printSolution(int[][] board) {
        try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        System.out.println("Printing in matrix format:");
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++)
//                System.out.print(" " + board[i][j] + " ");
//            System.out.println();
//        }
//
//        System.out.println("Printing in Queen format:");
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                if (board[i][j] == 1)
//                    System.out.print("Q" + (i + 1) + " ");
//                else
//                    System.out.print("_  ");
//            }
//            System.out.println();
//        }
    }

    // Check if it is safe to place the queen at the given position
    boolean isSafe(int[][] board, int x, int y) {
        int XminusY = x - y;
        int XplusY = x + y;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == x || j == y || (i - j) == XminusY || (i + j) == XplusY) {
                    if (board[i][j] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // A recursive utility function to solve the N-Queen problem
    boolean solveNQUtil(int n, int row, int[][] board) {
        // Base case: If all queens are placed, return true
        if (row == n)
            return true;

        // Consider this row and try placing this queen in all columns one by one
        for (int col = 0; col < n; col++) {
        	board[row][col] = -1;
            repaint();
            printSolution(board);
            if (isSafe(board, row, col)) {
                board[row][col] = 1;
                repaint();
                printSolution(board);
                if (solveNQUtil(n, row + 1, board)) {// Try for the next row
                    return true;
                }
                
                board[row][col] = 0; // If not successful, unmark that place to 0
                repaint();
                printSolution(board);
            } else {
            	board[row][col] = 0;
                repaint();
                printSolution(board);
            }
        }

        // If the queen cannot be placed in any row in this column, return false
        return false;
    }

    // This function solves the N-Queen problem using backtracking
    boolean solveNQ() {
        board = new int[N][N];

        // Initialize all positions to 0
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                board[i][j] = 0;
        }

        if (!solveNQUtil(N, 0, board)) {
            System.out.println("Solution does not exist");
            return false;
        }

        System.out.println("SOLVED!!!");
        printSolution(board);
        repaint();
        return true;
    }

    public static void main(String[] args) {
    	NQueenProblem panel = new NQueenProblem();
    	
    	JFrame frame = new JFrame("N Queens Solution");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(N * CELL_SIZE + 100, N * CELL_SIZE + 100);
        frame.setVisible(true);

        panel.solveNQ();

    }
}
