package handy.tictactoe.objects;

import handy.tictactoe.utils.GameRules;

public class ComputerPlayer {
    private final Board board;
    private final char marker;
    private final GameRules gameRules;

    public ComputerPlayer(Board board, GameRules gameRules) {
        marker = 'O';
        this.gameRules = gameRules;
        this.board = board;
    }

    /**
     * Put the marker in the index
     *
     * @param index
     * @throws Exception
     */
    void makeMove(int index) throws Exception {
        // Put the marker in the index
        board.setCell(marker, index);
    }

    /**
     * get the move the computer should make
     *
     * @throws Exception
     */
    public void selectMove() throws Exception {
        makeMove(unbeatableComputer());
    }

    /**
     * if (marker == X) return 'O' else return 'X'
     *
     * @param marker
     * @return
     */
    char getOpponent(char marker) {
        return (marker == 'X') ? 'O' : 'X';
    }

    /**
     * Check who is winning
     *
     * @param marker
     * @return
     */
    float boardScore(char marker) {
        if (gameRules.isMarkerWinner(marker, board.concatenateWinConditions())) {
            return (float) 1.0;
        } else if (gameRules.isMarkerWinner(getOpponent(marker), board.concatenateWinConditions())) {
            return (float) -1.0;
        }
        return (float) 0.0;
    }

    /**
     * Algorithm to always win
     * Online Links :
     * http://en.wikipedia.org/wiki/Negamax
     * http://www.redhotpawn.com/rival/programming/negamax.php
     * http://www.hamedahmadi.com/gametree/
     * http://www.neverstopbuilding.com/minimax
     *
     * @param marker
     * @param depth
     * @param alpha
     * @param beta
     * @return
     * @throws Exception
     */
    float negamax(char marker, int depth, float alpha, float beta) throws Exception {
        char opponent = getOpponent(marker);
        float bestScore = Integer.MIN_VALUE;

        if (gameRules.isGameOver() || depth >= 7) {
            return boardScore(marker) / depth;
        } else {
            for (int move : board.getEmpty()) {
                board.setCell(marker, move);
                float score = -negamax(opponent, depth + 1, -beta, -alpha);
                undoMove(move);
                if (score > bestScore) {
                    bestScore = score;
                }
                if (alpha < score) {
                    alpha = score;
                }
                if (alpha >= beta) {
                    break;
                }
            }
        }
        return bestScore;
    }

    int unbeatableComputer() throws Exception {
        final char opponent = getOpponent(marker);
        int bestMove = -1;
        float bestScore = Integer.MIN_VALUE;

        for (int move : board.getEmpty()) {
            board.setCell(marker, move);
            float score = -negamax(opponent, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            undoMove(move);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }


    private void undoMove(int move) {
        board.undoCell(move);
    }
}