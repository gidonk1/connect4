package States.GameStates;

import GameComponets.Board;
import GameComponets.Colour;
import GameComponets.Players.Player;
import States.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class RunningState implements State {

    protected Board board;
    protected Player player1;
    protected Player player2;
    protected Player currentPlayer;

    // GUI components
    protected final JFrame boardFrame;
    protected BoardPanel boardPanel;
    protected JLabel statusLabel;

    public RunningState(JFrame boardFrame) {
        this.board = new Board();
        this.boardFrame = boardFrame;
    }

    @Override
    public void draw() {
        this.boardPanel = new RunningState.BoardPanel();
        boardPanel.setPreferredSize(new Dimension(700, 600));
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                doMouseClick(x / boardPanel.getColumnWidth());
            }
        });

        JPanel statusPanel = new JPanel();
        this.statusLabel = new JLabel();
        statusLabel.setOpaque(true);
        statusPanel.add(statusLabel);
        updateStatusLabel();

        boardFrame.add(boardPanel, BorderLayout.CENTER);
        boardFrame.add(statusPanel, BorderLayout.PAGE_END);

        boardFrame.pack();
    }

    abstract void doMouseClick(int column);

    // Changes current player to opposite of what it was.
    protected void changeCurrentPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    // Update label under board to say whose turn it is.
    protected void updateStatusLabel() {
        statusLabel.setText(currentPlayer.getColour().name() + "'s turn");
        if (currentPlayer.getColour().equals(Colour.RED)) {
            statusLabel.setBackground(Color.RED);
        } else {
            statusLabel.setBackground(Color.BLUE.brighter());
        }
    }

    protected void checkEndGame(int column) {
        // Make a copy of board for computer to play with
        // so we won't refresh the GUI with the computer's
        // thoughts.
        Board boardCopy = board.clone();
        if (boardCopy.checkWin(column)) {
            JOptionPane.showMessageDialog(null, currentPlayer.getColour().name() + " Wins!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else if (boardCopy.generatePossibleMoves().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Draw!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    // Inner class used to draw the game board.
    private class BoardPanel extends JPanel {

        public BoardPanel() {
            setBackground(Color.YELLOW);
        }

        public int getRowHeight() {
            return getHeight() / Board.BOARD_ROWS;
        }

        public int getColumnWidth() {
            return getWidth() / Board.BOARD_COLUMNS;
        }

        @Override
        public void paintComponent(Graphics g) {

            // For board
            super.paintComponent(g);

            int rowHeight = getRowHeight();
            int colWidth = getColumnWidth();
            int rowOffset = rowHeight / 8;
            int colOffset = colWidth / 8;

            for (int row = 0; row < Board.BOARD_ROWS; row++) {
                for (int col = 0; col < Board.BOARD_COLUMNS; col++) {
                    // Flipping columns vertically so row 0 will be at bottom.
                    if (board.getBoard()[col][Board.BOARD_ROWS - row - 1].getColour().equals(Colour.BLUE)) {
                        g.setColor(Color.BLUE);
                    } else if (board.getBoard()[col][Board.BOARD_ROWS - row - 1].getColour().equals(Colour.RED)) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.BLACK);
                    }
                    g.fillOval(col * colWidth + colOffset,
                            row * rowHeight + rowOffset,
                            colWidth - 2 * colOffset,
                            rowHeight - 2 * rowOffset);
                }
            }
        }
    }

}
