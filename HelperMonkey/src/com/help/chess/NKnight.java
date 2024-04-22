package com.help.chess;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class NKnight extends JPanel {
	static final int N = 7;
	static final int COUNT = 25;
	
	static final int CELL_SIZE = 50; // Size of each cell in pixels

	static int[][] board;

	private void resetBoardCell(int idx, int jdx) {
		for (int j=jdx; j < N; j++) board[idx][j] = 0;
		
		for (int i = idx+1; i < N; i++)
			for (int j = 0; j < N; j++)
				board[i][j] = 0;
	}
	
	private void resetFromNext(int idx, int jdx) {
		for (int i=idx; i < N; i++) board[i][jdx] = 0;
		
		for (int i = idx+1; i < N; i++)
			for (int j = 0; j < N; j++)
				board[i][j] = 0;
	}
	
	private int getSafeCount() {
		int cnt =0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (board[i][j] == 1) cnt++;
		
		if(cnt > 21) {
			System.out.println("Safe Count :: " + cnt);
		}
		
		return cnt;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBoard(g);
	}

	private void drawBoard(Graphics g) {
		// System.out.println("Draw board:::");
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
					g.fillOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 10, CELL_SIZE - 10);
				}
				if (board[row][col] == -1) {
					g.setColor(Color.RED);
					g.drawOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 15, CELL_SIZE - 15);
				}
			}
		}
	}

	/* A utility function to print solution */
	static void printSolution(int[][] board) {
		try {
			Thread.sleep(0, 0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        System.out.println("Printing in matrix format: ");
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++)
//                System.out.print(" " + board[i][j] + " ");
//            System.out.println();
//        }
//
//        System.out.println("Printing in Knight format: ");
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                if (board[i][j] == 1)
//                    System.out.print("K ");
//                else
//                    System.out.print("_  ");
//            }
//            System.out.println();
//        }
	}

	static void printSolution1(int[][] board) {
		System.out.println("Printing in matrix format: ");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				System.out.print(" " + board[i][j] + " ");
			System.out.println();
		}

		System.out.println("Printing in Knight format: ");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == 1)
					System.out.print("K ");
				else
					System.out.print("_  ");
			}
			System.out.println();
		}
	}

	/* check to see if it is safe to put the knight in x,y position */
	boolean isSafe(int[][] board, int row, int col) {
		int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
		int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

		for (int i = 0; i < 8; i++) {
			int x = row + dx[i];
			int y = col + dy[i];

			if (x < 0 || y < 0 || x >= N || y >= N) {
				continue;
			}

			if (board[x][y] != 0) {
				return false;
			}
		}

		return true;
	}

	/* A recursive utility function to solve N Knights problem */
	int solveNKUtil(int n, int row, int col) {
		int nextRow = row;
		int nextCol = col;

		for (int i = 0; i < n;) {
			// System.out.println("Solved:: " + i);

			int prevNC = nextCol;
			int prevNR = nextRow;
			
			board[nextRow][nextCol] = -1;
			repaint();
			printSolution(board);
			if (isSafe(board, nextRow, nextCol)) {
				board[nextRow][nextCol] = 1;
				i++;
				repaint();
				printSolution(board);

				int safeCount = getSafeCount();
				if (safeCount == n)
					return 1;

				nextCol++;

				if (nextCol >= N) {
					nextCol = nextCol % N;
					nextRow++;

					if (nextRow >= N) {
						// System.out.println("MAX Solved");
						resetBoardCell(prevNR,prevNC);
						i--;
						repaint();
						printSolution(board);
						return -2;
					}
				}

				int retVal = solveNKUtil(n, nextRow, nextCol);
				if(retVal == 1) {
					return 1;
				} else if (retVal < 0) {
					//board[nextRow][nextCol] = 0;
					resetBoardCell(prevNR,prevNC);
					repaint();
					printSolution(board);
					
					nextCol--;
					if(nextCol < 0) {
						nextCol = N-1;
						nextRow--;
						
						if(nextRow < 0) {
							System.out.println("!!!!!! ROW out of bounds !!!!!");
							return 0;
						}
					}
					//return 0;
				}

				//board[nextRow][nextCol] = 0;
				resetBoardCell(nextRow,nextCol);
				repaint();
				printSolution(board);
				i--;
			} else {
				//board[nextRow][nextCol] = 0;
				resetBoardCell(nextRow,nextCol);
				repaint();
				printSolution(board);
			}

			nextCol++;

			if (nextCol >= N) {
				nextCol = nextCol % N;
				nextRow++;

				if (nextRow >= N) {
					// System.out.println("MAX Solved");
					//board[prevNR][prevNC] = 0;
					resetBoardCell(prevNR,prevNC);
					repaint();
					printSolution(board);
					return -1;
				}
			}

		}
		
		return 0;
	}

	/*
	 * This function solves the N Knights problem using Backtracking. It mainly uses
	 * solveNKUtil() to solve the problem. It returns false if knights cannot be
	 * placed, otherwise, return true and prints placement of knights in the form of
	 * 1s. Please note that there may be more than one solutions, this function
	 * prints one of the feasible solutions.
	 */
	boolean solveNK() {
		board = new int[N][N];
		// initialize all the positions to 0
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				board[i][j] = 0;

//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
		// board[i][j] = 1;
		if (solveNKUtil(COUNT, 0, 0) == 1) {
			printSolution(board);
			repaint();
			return true;
		}
		// board[i][j] = 0;
//            }
//        }

		System.out.println("Solution does not exist");
		return false;
	}

	// driver program to test above function
	public static void main(String[] args) {
		NKnight panel = new NKnight();

		JFrame frame = new JFrame("N Knights Solution");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(N * CELL_SIZE + 100, N * CELL_SIZE + 100);
		frame.setVisible(true);

		panel.solveNK();
		System.out.println("SOLVED!!!");
	}
}
