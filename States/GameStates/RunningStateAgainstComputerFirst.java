package States.GameStates;

import GameComponets.Colour;
import GameComponets.Players.ComputerPlayer;
import GameComponets.Players.HumanPlayer;

import javax.swing.*;

public class RunningStateAgainstComputerFirst extends RunningStateAgainstComputer {

    public RunningStateAgainstComputerFirst(JFrame boardFrame) {
        super(boardFrame);
        this.player1 = new HumanPlayer(Colour.RED, board);
        this.player2 = new ComputerPlayer(Colour.BLUE, board, this.player1);
        this.currentPlayer = this.player1;
    }

}
