package view;

import controller.IController;
import other.Command;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * This view.CommandLine class represents the view in an MVC pattern. It is the I/O handling class and receives input
 * and displays output through a command line interface. When commands cannot be handled by it, it passes them onto the
 * controller it is assigned.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-22
 */
public class CommandLine implements ICommandLine {
    private InputStream inputStream;
    private OutputStream outputStream;
    private IController controller;


    /**
     * Creates a new command line object that sets up its input and output streams that it communicates through.
     */
    public CommandLine() {

    }

    /**
     * Sets up a link to the controller provided. Whenever the command line realizes it does not know how do a command
     * it will redirect the command to the controller that it is linked to.
     *
     * Why: A summary of what was explained in the view.ICommandLine interface is that a chicken and egg situations happens when
     * both the controller and command line need to be created but both need each other to perform their tasks.
     *
     * @param controller The controller that the command line should pass along unknown commands to
     */
    public void setController(IController controller) {

    }

    /**
     * This command takes in the next command from the user. Once that is done, a command object can be created and either
     * handled by the command line or passed on to the assigned controller. Any output the controller returns is then printed
     * to the screen.
     *
     * Why: This is the way that almost all input will be taken from the user (the exception is moving multiple cards at
     * the same time.
     */
    public void collectAndHandleInput() {

    }

    /**
     * This command is available for when the user has chosen to move multiple cards from one card column to another.
     * This is the second step 'movement' for this complex move, not the 'selection' step. All input gets collected and returned
     * to the controller. Either an attempt to move the cards should be made or the player may deselect the group of cards.
     *
     * @return The command to be handled by the controller
     */
    public Command requestMultiCardMoveDestination() {
        return null;
    }

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
    public void printGameUpdate(String updateInformation) {

    }

    /**
     * When called, this will display a simple message saying that the player has won the game shortly before the controller
     * starts a new game/round for the person to play.
     */
    public void printWinMessage() {

    }

    // Prints a summarized version of the rules and links to the Bicycle rules page for a more detailed explanation.
    private void printRules() {

    }

    // Prints the list of available commands, their syntax, a description of what they do
    private void printCommands() {

    }

    /*
    *  Prints a message welcoming the player and telling them about the rules and help commands when they first start
    * the game
    *
     */
    private void welcomeMessage() {

    }
}
