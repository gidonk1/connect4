package ApplicationManager;

import States.MenuStates.StartState;

public class Application {

    public static void main(String[] args) {

        // Event handler creates and transitions to different states based on user input.
        EventHandler handler = new EventHandler();

        // Initialises the start screen with the event handler as a parameter.
        // Only initialise the start screen since that is the only screen you know is needed
        // at this stage, thus avoiding the issue of creating unused screens.
        new StartState(handler.getBoardFrame(), handler).draw();

    }
}
