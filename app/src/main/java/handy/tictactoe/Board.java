package handy.tictactoe;

import java.util.ArrayList;

interface Board {
    public static final char EMPTY = ' ';

    public char[] getGrid();

    public char getCell(int index);

    public void setCell(char marker, int index) throws Exception;

    public ArrayList<Integer> getEmpty();

    public ArrayList<char[]> makeRows();

    public ArrayList<char[]> makeColumns();

    public ArrayList<char[]> makeDiagonals();

    public ArrayList<char[]> concatenateWinConditions();

    public boolean cellOccupied(int index);

    public void undoCell(int index);

    public int getSize();

}
