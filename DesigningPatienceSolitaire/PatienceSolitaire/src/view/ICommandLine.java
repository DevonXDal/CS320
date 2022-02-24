package view;

import controller.IController;
import other.Command;

/**
 * The view.ICommandLine class provides a contract that command line handlers for the game Patience Solitaire are expected to implement.
 *
 * Why: This allows the project to better follow the dependency-inversion principle for larger moving piece that is the I/O handler
 * and to allow mock command lines to be implemented so that testing the game controller is feasible.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-21
 */
public interface ICommandLine {

    /**
     * Sets the controller to be used with this command line
     *
     * Why: This is done because a chicken and the egg situation would develop otherwise due to controller needing an object
     * of the command line and the command line needing an object of the controller. The controller is first necessary piece so
     * the command line is provided the controller object after it has been created.
     *
     * @param controller The controller to send the input from this command line
     */
    void setController(IController controller);

    /**
     * This command takes in the next command from the user. Once that is done, a command object can be created and either
     * handled by the command line or passed on to the assigned controller. Any output the controller returns is then printed
     * to the screen.
     *
     * Why: This is the way that almost all input will be taken from the user (the exception is moving multiple cards at
     * the same time.
     */
    void collectAndHandleInput();

    /**
     * This command is available for when the user has chosen to move multiple cards from one card column to another.
     * This is the second step 'movement' for this complex move, not the 'selection' step. All input gets collected and returned
     * to the controller. Either an attempt to move the cards should be made or the player may deselect the group of cards.
     *
     * @return The command to be handled by the controller
     */
    Command requestMultiCardMoveDestination();

    /**
     * Takes in additional information (usually just the table and the current selection) and prints that update from the
     * game controller to the screen.
     *
     * Why: Some commands may or may not cause the table or selection to show updates. Each successful action will tend to
     * use the return from the controller's send input method to send that the move was successful. Formatting the display
     * of information and being consistent is easier if done by allowing this separate method call.
     *
     * @param updateInformation The text to be printed to the screen for the user to see
     */
    void printGameUpdate(String updateInformation);

    /**
     * When called, this will display a simple message saying that the player has won the game shortly before the controller
     * starts a new game/round for the person to play.
     */
    void printWinMessage();
}
