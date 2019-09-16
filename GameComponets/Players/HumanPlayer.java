package GameComponets.Players;

import GameComponets.Board;
import GameComponets.Colour;

public final class HumanPlayer extends AbstractPlayer {

    public HumanPlayer(Colour colour, Board board) {
        this.colour = colour;
        this.board = board;
    }

}
