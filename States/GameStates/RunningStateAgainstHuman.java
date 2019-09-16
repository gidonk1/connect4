package States.GameStates;

import GameComponets.Colour;
import GameComponets.Players.HumanPlayer;

import javax.swing.*;

public class RunningStateAgainstHuman extends RunningState {

    public RunningStateAgainstHuman(JFrame boardFrame) {
        super(boardFrame);
        this.player1 = new HumanPlayer(Colour.RED, board);
        this.currentPlayer = this.player1;
        this.player2 = new HumanPlayer(Colour.BLUE, board);
    }

    @Override
    void doMouseClick(int column) {

        boolean successfullyPlacedPlace = currentPlayer.placePiece(column);

        if (successfullyPlacedPlace) {

            // Repaint to see new piece placed in column.
            boardFrame.repaint();

            checkEndGame(column);

            changeCurrentPlayer();
            updateStatusLabel();

        }

    }
}
