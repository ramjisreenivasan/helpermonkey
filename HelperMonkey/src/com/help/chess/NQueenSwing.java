package com.help.chess;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NQueenSwing extends JPanel {
    static final int N = 6;
    static final int CELL_SIZE = 50; // Size of each cell in pixels
    static final int DELAY = 500; // Delay in milliseconds for updating the board

    private int[][] board;
    private int currentRow;
    private Timer timer;

    public NQueenSwing() {
        board = new int[N][N];
        currentRow = 0;
        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentRow < N) {
                    try {
						solveNQUtil(N, currentRow, board);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    currentRow++;
                    repaint();
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    /* A recursive utility function to solve N
    Queen problem */
    boolean solveNQUtil(int n, int row, int[][] board) throws InterruptedException {
        /* base case: If all queens are placed
        then return true */
        if (row == n)
            return true;

        /* Consider this column and try placing
        this queen in all rows one by one */

        int col;
        for (col = 0; col < n; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 1;
                repaint();
                Thread.sleep(1000);
                
                if (solveNQUtil(n, row + 1, board)) // try for the next row
                    return true;
                board[row][col] = 0; // if not successful unmark that place to 0
                repaint();
                Thread.sleep(1000);
                
            }
        }

        /* If the queen cannot be placed in any row in
        this column col then return false */
        return false;
    }

    /* This function solves the N Queen problem using
    Backtracking. It mainly uses solveNQUtil() to
    solve the problem.*/
    void solveNQ(int[][] board) {
        try {
			solveNQUtil(N, 0, board);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /* check to see if it is safe to put the queen in x,y position */
    static boolean isSafe(int[][] board, int x, int y) {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (i == x || j == y || (i - j) == (x - y) || (i + j) == (x + y)) {
                    if (board[i][j] == 1)
                        return false;
                }
        return true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
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
                    g.fillOval(col * CELL_SIZE + CELL_SIZE / 4, row * CELL_SIZE + CELL_SIZE / 4, CELL_SIZE / 2, CELL_SIZE / 2);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("N Queens Solution");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            NQueenSwing panel = new NQueenSwing();
            frame.add(panel);
            frame.setSize(N * CELL_SIZE, N * CELL_SIZE);
            frame.setVisible(true);
        });
    }
}
