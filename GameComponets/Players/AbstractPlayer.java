package GameComponets.Players;

import GameComponets.Board;
import GameComponets.Colour;

public abstract class AbstractPlayer implements Player {

    protected Colour colour;
    protected Board board;

    @Override
    public Colour getColour() {
        return colour;
    }

    @Override
    public Colour getOppColour() {
        if (colour.equals(Colour.RED)) {
            return Colour.BLUE;
        } else {
            return Colour.RED;
        }
    }

    // Returns true iff piece is placed in column.
    public boolean placePiece(int colIndex) {

        for (int r = 0; r < Board.BOARD_ROWS; r++) {

            if (board.getBoard()[colIndex][r].isEmpty()) {
                board.getBoard()[colIndex][r].setColour(colour);
                return true;
            }

        }

        return false;
    }

    // Returns true iff piece is placed in column of the given board.
    // Function used when computer working out best move.
    public boolean placePiece(int colIndex, Board boardCopy) {

        for (int r = 0; r < Board.BOARD_ROWS; r++) {

            if (boardCopy.getBoard()[colIndex][r].isEmpty()) {
                boardCopy.getBoard()[colIndex][r].setColour(colour);
                return true;
            }

        }

        return false;
    }

}
