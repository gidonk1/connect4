package GameComponets.Players;

import GameComponets.Board;
import GameComponets.Colour;
import GameComponets.Piece;

import java.util.List;

public final class ComputerPlayer extends AbstractPlayer {

    private static final int depth = 8; // max depth when searching game tree
    private Player opponent;

    public ComputerPlayer(Colour colour, Board board, Player opponent) {
        this.colour = colour;
        this.board = board;
        this.opponent = opponent;
    }

    // Returns best column to place piece using minimax algorithm with alpha-beta pruning and a heuristic.
    public int findBestMove(Board boardCopy) {
        return minimax(boardCopy, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true)[1];
    }

    // 'isMax' is true if current move is of maximizer, else false.
    // Returns int[2] of {score, column}.
    private int[] minimax(Board boardCopy, int currentDepth, int alpha, int beta, boolean isMax) {

        // Generate all possible next moves (moves represented by column indices).
        List<Integer> possibleMoves = boardCopy.generatePossibleMoves();

        int score;
        int bestColumn = -1;

        // If game is over or depth is reached, evaluate score.
        if (possibleMoves.isEmpty() || currentDepth == 0) {

            score = evaluate(boardCopy);
            return new int[]{score, bestColumn};

        } else {

            for (int move : possibleMoves) {

                // try this move for current player
                if (isMax) {
                    placePiece(move, boardCopy);
                } else {
                    opponent.placePiece(move, boardCopy);
                }

                if (isMax) {
                    // current player is maximizing
                    score = minimax(boardCopy, currentDepth - 1, alpha, beta, false)[0];
                    if (score > alpha) {
                        alpha = score;
                        bestColumn = move;
                    }
                } else {
                    // current player is minimizing
                    score = minimax(boardCopy, currentDepth - 1, alpha, beta, true)[0];
                    if (score < beta) {
                        beta = score;
                        bestColumn = move;
                    }
                }

                // undo move
                boardCopy.undoMove(move);

                // cut-off
                if (alpha >= beta) {
                    break;
                }
            }

        }

        return new int[]{(isMax) ? alpha : beta, bestColumn};
    }

    // The heuristic evaluation function for the current board is to count number of
    // possible 4-in-a-rows each player can still make and weight them accordingly.

    // Weight of possible 4-in-a-rows:
    // 1000 if possible 4-in-a-row has 4-in-a-row
    // 100 if possible 4-in-a-row has 3-in-a-row
    // 10 if possible 4-in-a-row has 2-in-a-row
    // 1 if possible 4-in-a-row has 1-in-a-row
    private int evaluate(Board boardCopy) {

        int score = 0;

        // Calculate vertical score.
        for (int col = 0; col < Board.BOARD_COLUMNS; col++) {
            for (int row = 0; row < Board.BOARD_ROWS - 3; row++) {
                Piece[] vertical = new Piece[4];
                for (int i = 0; i < 4; i++) {
                    vertical[i] = boardCopy.getBoard()[col][row + i];
                }
                score += evaluatePossible4InARow(vertical);
            }
        }

        // Calculate horizontal score.
        for (int row = 0; row < Board.BOARD_ROWS; row++) {
            for (int col = 0; col < Board.BOARD_COLUMNS - 3; col++) {
                Piece[] horizontal = new Piece[4];
                for (int i = 0; i < 4; i++) {
                    horizontal[i] = boardCopy.getBoard()[col + i][row];
                }
                score += evaluatePossible4InARow(horizontal);
            }
        }

        // Calculate diagonal y = x score.
        for (int row = 0; row < Board.BOARD_ROWS - 3; row++) {
            for (int col = 0; col < Board.BOARD_COLUMNS - 3; col++) {
                Piece[] diagonal = new Piece[4];
                for (int i = 0; i < 4; i++) {
                    diagonal[i] = boardCopy.getBoard()[col + i][row + i];
                }
                score += evaluatePossible4InARow(diagonal);
            }
        }

        // Calculate diagonal y = -x score.
        for (int row = 0; row < Board.BOARD_ROWS - 3; row++) {
            for (int col = Board.BOARD_COLUMNS - 1; col >= 3; col--) {
                Piece[] diagonal = new Piece[4];
                for (int i = 0; i < 4; i++) {
                    diagonal[i] = boardCopy.getBoard()[col - i][row + i];
                }
                score += evaluatePossible4InARow(diagonal);
            }
        }

        return score;
    }

    // Given an array of 4 pieces, it returns the following score (+ for comp and - for opp):
    // 1000 if possible 4-in-a-row has 4-in-a-row
    // 100 if possible 4-in-a-row has 3-in-a-row
    // 10 if possible 4-in-a-row has 2-in-a-row
    // 1 if possible 4-in-a-row has 1-in-a-row
    private int evaluatePossible4InARow(Piece[] pieces) {

        assert pieces.length == 4 : "length must be 4";

        int compColourCount = 0;
        int oppColourCount = 0;

        for (Piece piece : pieces) {
            if (piece.getColour().equals(colour)) {
                compColourCount++;
            } else if (piece.getColour().equals(getOppColour())) {
                oppColourCount++;
            }
        }

        // If array of 4 pieces has both colour pieces then return 0 as no possible 4-in-a-row.
        if (compColourCount > 0 && oppColourCount > 0) {
            return 0;
        }


        // If here then 4 pieces do not contain both colours.

        switch (compColourCount) {
            case 0:
                break;
            case 1:
                return 1;
            case 2:
                return 10;
            case 3:
                return 100;
            case 4:
                return 1000;
        }

        switch (oppColourCount) {
            case 0:
                break;
            case 1:
                return -1;
            case 2:
                return -10;
            case 3:
                return -100;
            case 4:
                return -1000;
        }

        // If here then array of pieces are all empty so return 0.
        return 0;
    }

}
