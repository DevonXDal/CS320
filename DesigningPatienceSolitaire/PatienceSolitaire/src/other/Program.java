package other;

import controller.GameController;

/**
 * This Program class runs the game of Patience Solitaire. Once it creates the GameController it runs the initializeGame method and then waits
 * for the program to finish.
 *
 * Why: This Program class exists to separate the responsibility of starting everything up from the game controller.
 * This also makes it easier to locate the main method.
 */
public final class Program {

    /**
     * Program entrypoint that sets up and runs the controller to get everything going.
     * @param args Command line arguments, none are actually used
     */
    public static void main(String[] args) {
        GameController controller = new GameController();

        controller.initializeGame();
    }
}
