package model;

/**
 * Represents the game board containing the grid size and number layout.
 * Used for JSON serialization and deserialization via Jackson.
 *
 * @author Philipp Palm
 */
public class GameBoard {

	private static final int DEFAULT_BOARD_SIZE = 4;
	
	private int max = DEFAULT_BOARD_SIZE;
    
    private int[][] fld;

    /**
     * Creates a default 4x4 board with predefined example values.
     */
    public GameBoard() {
        fld = new int[max][max];

        fld[0][0] = 1;
        fld[3][0] = 1;
        fld[1][1] = 3;
        fld[2][1] = 2;
        fld[0][2] = 4;
        fld[1][2] = 3;
        fld[2][2] = 4;
        fld[3][3] = 2;
    }

    /**
     * Returns the value at the given grid coordinates.
     *
     * @param column the column index (x-axis)
     * @param row    the row index (y-axis)
     * @return the number at the specified cell, or 0 if the cell is empty
     */
    public int getValue(int column, int row) {
        return fld[column][row];
    }

    /**
     * Returns the raw 2D array representing the board layout.
     *
     * @return the board grid as a 2D int array
     */
    public int[][] getFld() {
        return fld;
    }

    /**
     * Sets the board array and updates the dimension accordingly.
     * Required for Jackson deserialization.
     *
     * @param fld the new board grid; must be a square 2D array
     */
    public void setFld(int[][] fld) {
        this.fld = fld;
        this.max = fld.length;
    }

    /**
     * Returns the board dimension (number of rows and columns).
     *
     * @return the board size
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the board dimension.
     * Required for Jackson deserialization.
     *
     * @param max the new board size
     */
    public void setMax(int max) {
        this.max = max;
    }
}