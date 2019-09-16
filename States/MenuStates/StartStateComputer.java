package States.MenuStates;

import ApplicationManager.EventHandler;
import States.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Initialised and drawn when user wants to play against computer.
public class StartStateComputer implements State, ActionListener {

    EventHandler handler;

    // GUI components
    private final JFrame boardFrame;
    private JPanel startPanel;
    private JButton playComputerFirst;
    private JButton playComputerSecond;
    private JButton playComputerRandomise;

    public StartStateComputer(JFrame boardFrame, EventHandler handler) {

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

        this.playComputerFirst = new JButton("Go First");
        playComputerFirst.addActionListener(this);

        this.playComputerSecond = new JButton("Go Second");
        playComputerSecond.addActionListener(this);

        this.playComputerRandomise = new JButton("Randomise");
        playComputerRandomise.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();

        playComputerFirst.setToolTipText("Click this to go first against the computer!");
        playComputerSecond.setToolTipText("Click this to go second against the computer!");
        playComputerRandomise.setToolTipText("Click this to randomly go first or second against the computer!");

        startPanel.add(playComputerFirst, gbc);
        startPanel.add(playComputerSecond, gbc);
        startPanel.add(playComputerRandomise, gbc);

        boardFrame.add(startPanel);
        boardFrame.pack();

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == playComputerFirst) {
            handler.playComputerFirst();
            boardFrame.remove(startPanel);
        } else if (source == playComputerSecond) {
            handler.playComputerSecond();
            boardFrame.remove(startPanel);
        } else if (source == playComputerRandomise) {
            handler.playComputerRandomise();
            boardFrame.remove(startPanel);
        }
    }

}
