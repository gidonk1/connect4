package ApplicationManager;

import States.GameStates.RunningStateAgainstComputerFirst;
import States.GameStates.RunningStateAgainstComputerSecond;
import States.GameStates.RunningStateAgainstHuman;
import States.MenuStates.StartStateComputer;

import javax.swing.*;
import java.util.Random;

// Manages transitions between screens.
public final class EventHandler {

    // GUI components
    private final JFrame boardFrame;

    public EventHandler() {

        this.boardFrame = new JFrame();
        boardFrame.setTitle("connect4");

        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardFrame.setLocationRelativeTo(null);
        boardFrame.setVisible(true);
    }

    public JFrame getBoardFrame() {
        return boardFrame;
    }

    // Transitions to screen for playing another human.
    public void playHuman() {
        new RunningStateAgainstHuman(getBoardFrame()).draw();
    }

    // Transitions to screen for choosing to go first / second when playing computer.
    public void playComputer() {
        new StartStateComputer(getBoardFrame(), this).draw();
    }

    // Transitions to screen for playing computer first.
    public void playComputerFirst() {
        new RunningStateAgainstComputerFirst(getBoardFrame()).draw();
    }

    // Transitions to screen for playing computer second.
    public void playComputerSecond() {
        new RunningStateAgainstComputerSecond(getBoardFrame()).draw();
    }

    // Randomly chooses between playing computer first or second.
    public void playComputerRandomise() {
        boolean first = new Random().nextBoolean();
        if (first) {
            playComputerFirst();
        } else {
            playComputerSecond();
        }
    }
}