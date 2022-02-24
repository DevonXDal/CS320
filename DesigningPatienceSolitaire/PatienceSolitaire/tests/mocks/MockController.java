package mocks;

import controller.IController;
import other.Command;

/**
 * Pretends to be the controller for tests dealing with an implementation of the command line.
 *
 * Why: This mock class exists as a way to test implementations of the command line by ensuring the input received during tests is what is desired
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-22
 */
public class MockController implements IController {
    private Command mostRecentlySentInput;

    /**
     * This collects the input from the command line and notfies the testing class when input has been recieved.
     * It acts as though a controller that would then handle the input, but no input is actually handled.
     *
     * @param command The command object that provides the command and its arguments to be handled
     * @return Whatever the test class desires this mock controller to return
     */
    @Override
    public String sendInput(Command command) {
        mostRecentlySentInput = command;
        return null;
    }

    /**
     * Returns the recently inserted input
     *
     * @return The input command object that was sent most recently from a command line implementation
     */
    public Command getMostRecentlySentInput() {
        return mostRecentlySentInput;
    }
}
