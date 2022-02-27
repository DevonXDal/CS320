package mocks;

import controller.IController;
import other.Command;
import view.ICommandLine;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Pretends to be the command line to a controller. It handles the public API that the controller would call in order to
 * perform its responsibilities.
 *
 * Why: This mock class is pretty much a necessity in order to ensure that the controller is performing the right actions
 * when expected during its testing. Without a mock command line, the controller will be extremely difficult to test correctly.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-27
 */
public class MockCommandLine implements ICommandLine {
    private IController controller;

    // Use the add() method to queue the input to be sent (stop the system by using the 'exit' input)
    public static LinkedList<Command> mockUserInput = new LinkedList<>(); // Sends input like a queue as requested

    // Use the poll() method to receive lines of input as it came in
    public static LinkedList<String> systemOutput = new LinkedList<>(); // Receives output like a queue as requested

    public static boolean gameWonMessagePrinted = false; // Will be true if the printWinMessage() method is called

    /**
     * Tests the setting up of the controller
     *
     * @param controller The controller to send the input from this command line
     */
    @Override
    public void setController(IController controller) {
        this.controller = controller;
    }

    /**
     * Sends the next fake command and then waits to receive system output strings to store
     * Send the last command with 'exit' to terminate the controller
     */
    @Override
    public void collectAndHandleInput() {
        systemOutput.add(controller.sendInput(mockUserInput.poll()));
    }

    /**
     * Returns the next mock input to the controller to carry out the next action.
     *
     * @return The next command to be used with testing
     */
    @Override
    public Command requestMultiCardMoveDestination() {
        return mockUserInput.poll();
    }

    /**
     * Stores the information from the controller instead of printing it to the screen.
     *
     * @param updateInformation The text to be printed to stored for testing purposes
     */
    @Override
    public void printGameUpdate(String updateInformation) {
        systemOutput.add(updateInformation);
    }

    /**
     * Stores that this method was called
     */
    @Override
    public void printWinMessage() {
        gameWonMessagePrinted = true;
    }
}
