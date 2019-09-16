package GameComponets;

import java.util.ArrayList;
import java.util.List;

public final class Board implements Cloneable {

    public static final int BOARD_COLUMNS = 7;
    public static final int BOARD_ROWS = 6;

    private Piece[][] board;

    public Board() {

        Piece[][] board = new Piece[BOARD_COLUMNS][BOARD_ROWS];

        // Initialise each position in the board to an empty piece.
        for (int c = 0; c < BOARD_COLUMNS; c++) {
            for (int r = 0; r < BOARD_ROWS; r++) {
                board[c][r] = new Piece();
            }
        }

        this.board = board;

    }

    public Piece[][] getBoard() {
        return board;
    }

    // Returns true iff there is a win.
    public boolean checkWin() {

        for (int c = 0; c < BOARD_COLUMNS; c++) {
            for (int r = 0; r < BOARD_ROWS; r++) {

                if (checkWin(c, r) && !board[c][r].getColour().equals(Colour.EMPTY)) {
                    return true;
                }

            }
        }

        return false;
    }

    // Returns true iff there is a win involving any piece in the provided column.
    public boolean checkWin(int col) {

        for (int r = 0; r < BOARD_ROWS; r++) {

            if (checkWin(col, r) && !board[col][r].getColour().equals(Colour.EMPTY)) {
                return true;
            }

        }

        return false;
    }

    // Returns true iff there is a win involving the piece in the provided position that has just been placed.
    private boolean checkWin(int col, int row) {

        Colour colour = board[col][row].getColour();

        // Checking if win vertically (only win possible downwards) since no pieces are above

        if (row + 1 >= 4) {
            if (checkColourOfPiece(colour, col, row - 1) && checkColourOfPiece(colour, col, row - 2) &&
                    checkColourOfPiece(colour, col, row - 3)) {
                return true;
            }
        }

        // Checking if win horizontally.

        if (checkColourOfPiece(colour, col - 1, row) && checkColourOfPiece(colour, col + 1, row)) {

            // If here then '3 in a row' with same colour on both sides - have to check piece on either side of the 3.
            if (checkColourOfPiece(colour, col - 2, row) || checkColourOfPiece(colour, col + 2, row)) {
                return true;
            }

        } else if (checkColourOfPiece(colour, col - 1, row)) {

            // If here then same colour on left side only - have to check next two pieces.
            if (checkColourOfPiece(colour, col - 2, row) && checkColourOfPiece(colour, col - 3, row)) {
                return true;
            }

        } else if (checkColourOfPiece(colour, col + 1, row)) {

            // If here then same colour on right side only - have to check next two pieces.
            if (checkColourOfPiece(colour, col + 2, row) && checkColourOfPiece(colour, col + 3, row)) {
                return true;
            }

        }

        // Checking if win diagonal y = x.

        if (checkColourOfPiece(colour, col - 1, row - 1) &&
                checkColourOfPiece(colour, col + 1, row + 1)) {

            // If here then '3 in a row' with same colour on both sides - have to check piece on either side of the 3.
            if (checkColourOfPiece(colour, col - 2, row - 2) ||
                    checkColourOfPiece(colour, col + 2, row + 2)) {
                return true;
            }

        } else if (checkColourOfPiece(colour, col - 1, row - 1)) {

            // If here then same colour on left side only - have to check next two pieces.
            if (checkColourOfPiece(colour, col - 2, row - 2) &&
                    checkColourOfPiece(colour, col - 3, row - 3)) {
                return true;
            }

        } else if (checkColourOfPiece(colour, col + 1, row + 1)) {

            // If here then same colour on right side only - have to check next two pieces.
            if (checkColourOfPiece(colour, col + 2, row + 2) &&
                    checkColourOfPiece(colour, col + 3, row + 3)) {
                return true;
            }

        }

        // Checking if win diagonal y = -x.

        if (checkColourOfPiece(colour, col - 1, row + 1) &&
                checkColourOfPiece(colour, col + 1, row - 1)) {

            // If here then '3 in a row' with same colour on both sides - have to check piece on either side of the 3.
            return checkColourOfPiece(colour, col - 2, row + 2) ||
                    checkColourOfPiece(colour, col + 2, row - 2);

        } else if (checkColourOfPiece(colour, col - 1, row + 1)) {

            // If here then same colour on left side only - have to check next two pieces.
            return checkColourOfPiece(colour, col - 2, row + 2) &&
                    checkColourOfPiece(colour, col - 3, row + 3);

        } else if (checkColourOfPiece(colour, col + 1, row - 1)) {

            // If here then same colour on right side only - have to check next two pieces.
            return checkColourOfPiece(colour, col + 2, row - 2) &&
                    checkColourOfPiece(colour, col + 3, row - 3);

        }

        return false;
    }

    // Returns true iff piece in given position is the same as given colour.
    private boolean checkColourOfPiece(Colour colour, int col, int row) {
        if (col >= 0 && col < BOARD_COLUMNS & row >= 0 && row < BOARD_ROWS) {
            return colour.equals(board[col][row].getColour());
        }
        return false;
    }

    // Returns list of possible moves.
    public List<Integer> generatePossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<Integer>();

        // If game is over, return empty list as no possible move.
        if (checkWin()) {
            return possibleMoves;
        }

        // If piece on top of columns is empty then it is possible to place a piece
        // in that column so add to list of possible moves.
        for (int col = 0; col < BOARD_COLUMNS; col++) {
            if (board[col][BOARD_ROWS - 1].getColour().equals(Colour.EMPTY)) {
                possibleMoves.add(col);
            }
        }

        return possibleMoves;
    }

    // Removes top piece in given column.
    // Method used by computer player when searching for best move.
    public void undoMove(int colIndex) {
        for (int r = BOARD_ROWS - 1; r >= 0; r--) {
            if (!board[colIndex][r].getColour().equals(Colour.EMPTY)) {
                board[colIndex][r].setColour(Colour.EMPTY);
                break;
            }
        }
    }

    // Deep clones the board.
    @Override
    public Board clone() {
        try {
            Board copy = (Board) super.clone();
            copy.board = new Piece[BOARD_COLUMNS][BOARD_ROWS];
            for (int c = 0; c < BOARD_COLUMNS; c++) {
                for (int r = 0; r < BOARD_ROWS; r++) {
                    if (board[c][r].getColour().equals(Colour.EMPTY)) {
                        copy.board[c][r] = new Piece();
                    } else if (board[c][r].getColour().equals(Colour.RED)) {
                        copy.board[c][r] = new Piece(Colour.RED);
                    } else {
                        copy.board[c][r] = new Piece(Colour.BLUE);
                    }
                }
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            return null;
        }

    }

    public void printBoard() {

        for (int r = BOARD_ROWS - 1; r >= 0; r--) {

            for (int c = 0; c < BOARD_COLUMNS; c++) {
                board[c][r].printPiece();
                System.out.print(" ");
            }

            System.out.println();
        }

    }
}
