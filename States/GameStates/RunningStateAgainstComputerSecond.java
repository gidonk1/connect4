package States.GameStates;

import GameComponets.Colour;
import GameComponets.Players.ComputerPlayer;
import GameComponets.Players.HumanPlayer;

import javax.swing.*;

public class RunningStateAgainstComputerSecond extends RunningStateAgainstComputer {

    public RunningStateAgainstComputerSecond(JFrame boardFrame) {
        super(boardFrame);
        this.player1 = new HumanPlayer(Colour.BLUE, board);
        this.player2 = new ComputerPlayer(Colour.RED, board, this.player1);
        this.currentPlayer = this.player2;

        // Computer plays first move and now state behaves same way as when computer goes second.
        computerFirstMove();
    }

    private void computerFirstMove() {
        currentPlayer.placePiece(((ComputerPlayer) currentPlayer).findBestMove(board.clone()));
        changeCurrentPlayer();
    }

}
