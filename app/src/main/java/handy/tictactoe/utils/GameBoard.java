package handy.tictactoe.utils;

import java.util.ArrayList;
import java.util.Arrays;

import handy.tictactoe.objects.Board;

public class GameBoard implements Board {
    private final int size = 9;

    private final char[] grid = new char[]{
            EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY};

    public char[] getGrid() {
        return grid;
    }

    public char getCell(int index) {
        return grid[index];
    }

    public void setCell(char marker, int index) throws Exception {
        if (cellOccupied(index) || index > size - 1) {
            throw new Exception();
        } else {
            grid[index] = marker;
        }
    }

    /**
     * Returns all empty locations
     */
    public ArrayList<Integer> getEmpty() {
        ArrayList<Integer> emptyLocations = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            checkIfCharIsEmpty(emptyLocations, i);
        }
        return emptyLocations;
    }

    /**
     * Check if the current char is the EMPTY char
     * @param emptyLocations
     * @param i
     */
    private void checkIfCharIsEmpty(ArrayList<Integer> emptyLocations, int i) {
        if (grid[i] == EMPTY) {
            emptyLocations.add(i);
        }
    }

    public ArrayList<char[]> makeRows() {
        ArrayList<char[]> rows = new ArrayList<>();
        rows.add(Arrays.copyOfRange(grid, 0, 3));
        rows.add(Arrays.copyOfRange(grid, 3, 6));
        rows.add(Arrays.copyOfRange(grid, 6, grid.length));
        return rows;
    }

    public ArrayList<char[]> makeColumns() {
        ArrayList<char[]> columns = new ArrayList<>();
        columns.add(new char[]{grid[0], grid[3], grid[6]});
        columns.add(new char[]{grid[1], grid[4], grid[7]});
        columns.add(new char[]{grid[2], grid[5], grid[8]});
        return columns;
    }

    public ArrayList<char[]> makeDiagonals() {
        ArrayList<char[]> diagonals = new ArrayList<>();
        diagonals.add(new char[]{grid[0], grid[4], grid[8]});
        diagonals.add(new char[]{grid[2], grid[4], grid[6]});
        return diagonals;
    }

    public ArrayList<char[]> concatenateWinConditions() {
        ArrayList<char[]> solutions = new ArrayList<>();
        solutions.addAll(makeRows());
        solutions.addAll(makeColumns());
        solutions.addAll(makeDiagonals());
        return solutions;
    }

    /**
     * Check if this index is empty or not
     * @param index
     * @return
     */
    public boolean cellOccupied(int index) {
        return grid[index] != EMPTY;
    }

    public void undoCell(int index) {
        grid[index] = EMPTY;
    }

    @Override
    public int getSize() {
        return size;
    }
}