package GameComponets.Players;

import GameComponets.Board;
import GameComponets.Colour;

public interface Player {

    // Returns colour of player.
    Colour getColour();

    // Returns colour of opponents player.
    Colour getOppColour();

    // Returns true iff piece is placed in column.
    boolean placePiece(int colIndex);

    // Returns true iff piece is placed in column of the given board.
    boolean placePiece(int colIndex, Board boardCopy);

}
