package model;

public class GameBoard {

    private int max = 4;
    private int[][] fld;

    public GameBoard() {
        this.fld = new int[max][max];

        // Beispielwerte
        fld[1][0] = 1;
        fld[3][0] = 1;
        fld[1][1] = 3;
        fld[2][1] = 2;
        fld[0][2] = 4;
        fld[1][2] = 3;
        fld[2][2] = 4;
        fld[3][3] = 2;
    }

    public int getValue(int column, int row) {
        return fld[column][row];
    }

    public int[][] getFld() {
        return fld;
    }

    public void setFld(int[][] fld) {
        this.fld = fld;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}