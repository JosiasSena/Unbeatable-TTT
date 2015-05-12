package handy.tictactoe.utils;

import java.util.ArrayList;

import handy.tictactoe.objects.Board;

public class GameRules {
    private final Board board;

    public GameRules(Board board) {
        this.board = board;
    }

    /**
     * For all the possible solutions passed in lets check which one is a winning solution
     * @param solutions
     * @return
     */
    boolean isWinner(ArrayList<char[]> solutions) {
        for (char[] solution : solutions) {
            char first = solution[0];
            if (firstIsBlank(first)) {
                continue;
            } else if(isWinningLine(solution, first)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return if this is a winning line or not
     * @param solution
     * @param first
     * @return
     */
    boolean isWinningLine(char[] solution, char first) {
        return first == solution[1] & first == solution[2];
    }

    public boolean isDraw() {
        return board.getEmpty().size() == 0;
    }

    public boolean isGameOver() {
        if (isWinner(board.concatenateWinConditions())) {
            return true;
        } else if (isDraw()){
            return true;
        }
        return false;
    }

    /**
     * Check if the marker is a winner in the following solutions
     * @param marker
     * @param solutions
     * @return
     */
    public boolean isMarkerWinner(char marker, ArrayList<char[]> solutions) {
        for (char[] solution : solutions) {
            char first = solution[0];
            if (firstIsBlank(first) || first != marker) {
                continue;
            } else if(isWinningLine(solution, first)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return if first is blank
     * @param first
     * @return
     */
    private boolean firstIsBlank(char first){
        return first == Board.EMPTY;
    }
}