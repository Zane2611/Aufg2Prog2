package model;

import java.awt.Point;

public class GameBoard {
	private int[][] board;

	public GameBoard() {
		this.board = new int[4][4];
		// Initialize with default hardcoded values from picture
		this.board[1][0] = 1;
		this.board[3][0] = 1;
		this.board[1][1] = 3;
		this.board[2][1] = 2;
		this.board[0][2] = 4;
		this.board[1][2] = 3;
		this.board[2][2] = 4;
		this.board[3][3] = 2;
	}

	public int getValue(int column, int row) {
		return this.board[column][row];
	}

	public int[][] getBoard() {
		return this.board;
	}
}
