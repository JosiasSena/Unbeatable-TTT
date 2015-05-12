package handy.tictactoe.objects;

import java.util.ArrayList;

public interface Board {
    char EMPTY = ' ';

    char[] getGrid();

    char getCell(int index);

    void setCell(char marker, int index) throws Exception;

    ArrayList<Integer> getEmpty();

    ArrayList<char[]> makeRows();

    ArrayList<char[]> makeColumns();

    ArrayList<char[]> makeDiagonals();

    ArrayList<char[]> concatenateWinConditions();

    boolean cellOccupied(int index);

    void undoCell(int index);

    int getSize();

}
