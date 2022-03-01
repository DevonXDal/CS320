package view;

import controller.IController;
import other.Command;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This view.CommandLine class represents the view in an MVC pattern. It is the I/O handling class and receives input
 * and displays output through a command line interface. When commands cannot be handled by it, it passes them onto the
 * controller it is assigned.
 *
 * @author Devon X. Dalrymple
 * @version 2022-03-01
 */
public class CommandLine implements ICommandLine {
    private InputStream inputStream; // Output stream is removed since it was not used at all
    private IController controller;


    /**
     * Creates a new command line object that sets up its input stream that it communicates through. It also prints a
     * welcome message.
     */
    public CommandLine() {
        inputStream = System.in;

        printWelcomeMessage();
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
        this.controller = controller;
    }

    /**
     * This command takes in the next command from the user. Once that is done, a command object can be created and either
     * handled by the command line or passed on to the assigned controller. Any output the controller returns is then printed
     * to the screen.
     *
     * Why: This is the way that almost all input will be taken from the user (the exception is moving multiple cards at
     * the same time.
     *
     * https://www.techiedelight.com/get-subarray-array-specified-indexes-java/
     */
    public void collectAndHandleInput() {
        Command newCommand = takeInput();
        String output = null;

        switch (newCommand.getCommand()) {
            case "rules":
                printRules();
                break;
            case "help":
                printCommands();
                break;
            case "exit":
                System.out.println("Thanks for playing! Play again soon!");
                controller.sendInput(newCommand);
                break;
            default:
                output = controller.sendInput(newCommand);
                break;
        }

        if (output != null) {
            System.out.println(output);
        }
    }

    /**
     * This command is available for when the user has chosen to move multiple cards from one card column to another.
     * This is the second step 'movement' for this complex move, not the 'selection' step. All input gets collected and returned
     * to the controller. Either an attempt to move the cards should be made or the player may deselect the group of cards.
     *
     * @return The command to be handled by the controller
     */
    public Command requestMultiCardMoveDestination() {
        return takeInput();
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
        System.out.println(updateInformation);
    }

    /**
     * When called, this will display a simple message saying that the player has won the game shortly before the controller
     * starts a new game/round for the person to play.
     */
    public void printWinMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("Congratulations, you have successfully built up your foundations. A new game will start in five " +
                "seconds. Feel free to ‘exit’ after the game resets to close the game.\n");
        builder.append("\n");

        System.out.println(builder);
    }

    // Prints a summarized version of the rules and links to the Bicycle rules page for a more detailed explanation.
    private void printRules() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("In Patience Solitaire, the game by shuffling a deck of cards and dealing one card from left to right" +
                " to the card columns. Each time doing one less card column (the left most column that got a card last time)."+
                " When all columns have been dealt cards from 1-7 cards, the remaining 24 cards in the deck are placed as " +
                "the draw/stockpile. Cards can be drawn one at a time from the deck and placed in the waste pile to the right"+
                " face up. The bottom most card (last recently put in) for each card column is turned face up. As the bottom card"+
                " is removed from these card columns, if the newest bottom card is face down, it becomes face up.");
        builder.append("\n");
        builder.append("The top card of the waste pile, bottom card(s) that are face up on the columns, or the top card of a "+
                "foundation can be moved to another location. A card can start an empty pile if it is a King. A pile can"+
                " be moved to, to start a foundation if it is an Ace. To play a card on a foundation with other cards,"+
                " the card to move to the foundation must be one rank higher and of the same suit as the foundation’s "+
                " top card to be moved. To move a card to a card column with cards, the card must be one rank less and "+
                "of the different suit color (Diamonds and Hearts are red; Clubs and Spades are black) then the bottom most "+
                "column card. If no cards are left in the deck. Cards can be refilled into the deck from the waste pile as "+
                "though the stack of cards was flipped over to be all face down and placed in the deck.");
        builder.append("\n");
        builder.append("If each foundation has been built up to have a King as the top card for each, then the game has been won.");
        builder.append("\n");
        builder.append("More detailed rules can be found at: https://bicyclecards.com/how-to-play/solitaire/");
        builder.append("\n\n");

        System.out.println(builder);
    }

    // Prints the list of available commands, their syntax, a description of what they do
    private void printCommands() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("help – Prints the list of commands, their syntax, and descriptions\n");
        builder.append("rules – Prints the rules for the game Patience Solitaire\n");
        builder.append("select {foundation/column} {num} OR select waste – Selects the specified source if the source has " +
                "at least one card; num is the position from left to right on the display that, that source appears in relation " +
                "to others of the same type\n");
        builder.append("deselect – Deselects the currently selected card(s)\n");
        builder.append("move {foundation/column} {num} – Moves the currently selected card to the specified destination if" +
                " legal; num is the position from left to right on the display that, that destination appears in relation " +
                "to the others of the same type\n");
        builder.append("draw – Draws the top card from the deck and places it face up in the waste pile; if the deck is " +
                "empty, it will be refilled with the cards from the waste pile if the waste pile has cards\n");
        builder.append("select {amount} cards from column {num} – Selects multiple cards to move simultaneously from one" +
                " column to another if legal; amount is the amount of cards from the bottom of the selection to move at " +
                "the same time; num is the position from left to right of the source column\n");
        builder.append("restart – Resets the table with a new distribution of cards and play begins anew\n");
        builder.append("exit – Stops the game and then closes the application shortly afterwards\n");
        builder.append("\n");

        System.out.println(builder);
    }

    /*
    *  Prints a message welcoming the player and telling them about the rules and help commands when they first start
    * the game
    *
     */
    private void printWelcomeMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("Welcome to a command-line variation of Patience Solitaire. In order to start, try using the " +
                "‘rules’ or ‘help’ commands to learn how to interact with the game.\n");
        builder.append("\n");

        System.out.println(builder);
    }

    /*
    Takes in the next line of input from the user, turns it into a command and returns it for processing
     */
    private Command takeInput() {
        while (true) { // True until the return statement is hit
            Scanner scanner = new Scanner(inputStream);

            System.out.print("> ");
            String inputLine = scanner.nextLine().replace("> ", "").trim(); // Take in the input

            String[] inputParts = inputLine.split(" ");

            if (inputParts.length > 0) {
                Command newCommand;

                if (inputParts.length > 1) {
                    String[] arguments = Arrays.copyOfRange(inputParts, 1, inputParts.length);

                    newCommand = new Command(inputParts[0], arguments);
                } else {
                    newCommand = new Command(inputParts[0], new String[0]);
                }

                return newCommand;
            }
        }
    }
}
