package States.MenuStates;

import ApplicationManager.EventHandler;
import States.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The first screen the user sees when starting the application.
public final class StartState implements State, ActionListener {

    EventHandler handler;

    // GUI components
    private final JFrame boardFrame;
    private JPanel startPanel;
    private JButton playHuman;
    private JButton playComputer;
    private JButton quit;

    public StartState(JFrame boardFrame, EventHandler handler) {

        this.boardFrame = boardFrame;
        this.handler = handler;
        this.startPanel = new JPanel();

        startPanel.setLayout(new GridBagLayout());
        startPanel.setBackground(Color.YELLOW);
        startPanel.setPreferredSize(new Dimension(700, 600));

        boardFrame.pack();
    }

    @Override
    public void draw() {

        this.playHuman = new JButton("Play Human");
        playHuman.addActionListener(this);

        this.playComputer = new JButton("Play Computer");
        playComputer.addActionListener(this);

        this.quit = new JButton("Quit");
        quit.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();

        playHuman.setToolTipText("Click this to play against another human!");
        playComputer.setToolTipText("Click this to play against the computer!");
        quit.setToolTipText("Click this to quit the game.");

        startPanel.add(playHuman, gbc);
        startPanel.add(playComputer, gbc);
        startPanel.add(quit, gbc);

        boardFrame.add(startPanel);
        boardFrame.pack();

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == playHuman) {
            handler.playHuman();
            boardFrame.remove(startPanel);
        } else if (source == playComputer) {
            handler.playComputer();
            boardFrame.remove(startPanel);
        } else if (source == quit) {
            System.exit(0);
        }
    }
}

