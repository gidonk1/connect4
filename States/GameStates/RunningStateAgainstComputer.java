package States.GameStates;

import GameComponets.Board;
import GameComponets.Players.ComputerPlayer;
import GameComponets.Players.HumanPlayer;

import javax.swing.*;

public abstract class RunningStateAgainstComputer extends RunningState {

    public RunningStateAgainstComputer(JFrame boardFrame) {
        super(boardFrame);
    }

    // Checks for mouse click and fulfills the required actions
    @Override
    public void doMouseClick(int column) {

        if (currentPlayer instanceof HumanPlayer) {

            boolean successfullyPlacedPlace = currentPlayer.placePiece(column);

            if (successfullyPlacedPlace) {

                // Repaint to see new piece placed in column.
                boardFrame.repaint();

                checkEndGame(column);

                changeCurrentPlayer();
                updateStatusLabel();

                // Use worker thread to think in the background, preventing the GUI locking up.
                SwingWorker<Integer, ?> worker = new SwingWorker<Integer, Object>() {
                    @Override
                    public Integer doInBackground() {
                        // Copy of board for computer to play with so GUI does not refresh with
                        // the computer's thoughts.
                        Board boardCopy = board.clone();
                        return ((ComputerPlayer) currentPlayer).findBestMove(boardCopy);
                    }

                    @Override
                    protected void done() {
                        try {
                            int column = get();

                            currentPlayer.placePiece(column);

                            // Repaint to see new piece placed in column.
                            boardFrame.repaint();

                            checkEndGame(column);

                            changeCurrentPlayer();
                            updateStatusLabel();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                };

                worker.execute();
            }
        }
    }
}
