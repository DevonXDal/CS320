package viewtests;

import mocks.MockController;
import models.Table;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.Test;
import other.Command;
import view.CommandLine;

import java.io.*;
import java.util.concurrent.CompletableFuture;

/**
 * Tests the CommandLine class to ensure that it reacts appropriately.
 * https://stackoverflow.com/questions/6371379/mocking-java-inputstream - Used to send mock data through
 *
 * Axioms:
 * I.	commandLine.collectAndHandleInput()
 * capture the output stream and verify that it contains “> “
 * II.	commandLine.collectAndHandleInput()
 * send “help” as input
 * capture the output stream and verify that it contains every command available with their syntax and descriptions
 * III.	commandLine.collectAndHandleInput()
 * send “rules” as input
 * capture the output stream and verify that it contains the desired short description of rules and a link to the Bicycle page
 * IV.	commandLine.printWinMessage()
 * capture the output stream and verify that it contains displays the message that the game was won
 * V.	A new command line is created
 * Capture the output stream and verify that it displays the initial welcome message
 * VI.	commandLine.printGameUpdate({fake information})
 * Capture the output stream and verify that the fake information is displayed to the screen
 * VII.	commandLine.requestMultiCardMoveDestination()
 * send anything on one line as input
 * Verify that the command line sends a command object with the command and arguments from the inputted text to the mock controller
 * VIII.	commandLine.collectAndHandleInput()
 * send gibberish input that the command line is not going to recognize
 * Verify the mock controller receives a command with the command and arguments from the input
 *
 * @author Devon X. Dalrymple
 * @version 2022-03-01
 */
public class CommandLineTest {
    private CommandLine commandLine;
    private MockController controller;

    private InputStream oldSystemIn;
    private OutputStream systemOutput;

    private InputStream mockInputStream;

    /**
     * Prepares the streams to handle the information that is sent out or taken in by the CommandLine object during tests
     */
    @Before
    public void setUp() {
        oldSystemIn = System.in;
        mockInputStream = System.in;
        systemOutput = System.out;

        System.setIn(new ByteArrayInputStream("help\n".getBytes()));
        commandLine = new CommandLine();
        controller = new MockController();

        commandLine.setController(controller);
    }

    /**
     * Tests that the "> " is shown whenever input is requested and that the help command works as intended
     *
     * https://stackoverflow.com/questions/1842734/how-to-asynchronously-call-a-method-in-java - Rahul Chauhan with running
     * async operations easily.
     * https://www.baeldung.com/convert-string-to-input-stream - Easy way to send a string as a stream.
     *
     * Relevant Axioms:
     * I.	commandLine.collectAndHandleInput()
     * capture the output stream and verify that it contains “> “
     * II.	commandLine.collectAndHandleInput()
     * send “help” as input
     * capture the output stream and verify that it contains every command available with their syntax and descriptions
     */
    @Test
    public void helpTest() {
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

        CompletableFuture.runAsync(() -> {
            try {
                wait(2500); // This ensures that time is allowed for the command line to start asking for input.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.setIn(new ByteArrayInputStream("help\n".getBytes()));

        });

        commandLine.collectAndHandleInput();

        assertTrue(systemOutput.toString().contains("> "));
        assertTrue(systemOutput.toString().contains(builder.toString()));
    }

    /**
     * Tests that the rules for the game are displayed when the 'rules' command is typed in.
     *
     * Relevant Axioms:
     * III.	commandLine.collectAndHandleInput()
     * send “rules” as input
     * capture the output stream and verify that it contains the desired short description of rules and a link to the Bicycle page
     */
    @Test
    public void rulesTest() {
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

        CompletableFuture.runAsync(() -> {
            try {
                wait(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.setIn(new ByteArrayInputStream("rules".getBytes()));
        });

        commandLine.collectAndHandleInput();

        assertTrue(systemOutput.toString().contains(builder.toString()));
    }

    /**
     * Tests that the right message is properly displayed when the game is first starting.
     *
     * Relevant Axioms:
     * V.	A new command line is created
     * Capture the output stream and verify that it displays the initial welcome message
     */
    @Test
    public void welcomeMessageTest() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("Welcome to a command-line variation of Patience Solitaire. In order to start, try using the " +
                "‘rules’ or ‘help’ commands to learn how to interact with the game.\n");
        builder.append("\n");


        CompletableFuture.runAsync(() -> {
            try {
                wait(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.setIn(new ByteArrayInputStream("rules".getBytes()));
        });

        commandLine.collectAndHandleInput();

        assertTrue(systemOutput.toString().contains(builder.toString()));
    }

    /**
     * Tests that the correct message is properly displayed when a game is won.
     *
     * Relevant Axioms:
     * IV.	commandLine.printWinMessage()
     * capture the output stream and verify that it contains displays the message that the game was won
     */
    @Test
    public void gameWonMessageTest() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("Congratulations, you have successfully built up your foundations. A new game will start in five " +
                "seconds. Feel free to ‘exit’ after the game resets to close the game.\n");
        builder.append("\n");

        commandLine.printWinMessage();

        assertTrue(systemOutput.toString().contains(builder.toString()));
    }

    /**
     * Tests that the command line prints the game update when requested to do so
     *
     * Relevant Axioms:
     * VI.	commandLine.printGameUpdate({fake information})
     * Capture the output stream and verify that the fake information is displayed to the screen
     */
    @Test
    public void printGameUpdateTest() {
        String testString = "The cake is a lie";

        commandLine.printGameUpdate(testString);

        assertTrue(systemOutput.toString().contains(testString));
    }

    /**
     * Tests that the command line sends input as a command back to the controller to review when requestMultiCardMoveDestination()
     * is called
     *
     * Relevant Axioms:
     * VII.	commandLine.requestMultiCardMoveDestination()
     * send anything on one line as input
     * Verify that the command line sends a command object with the command and arguments from the inputted text to the mock controller
     */
    @Test
    public void requestMultiCardMoveDestinationTest() {
        CompletableFuture.runAsync(() -> {
            try {
                wait(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.setIn(new ByteArrayInputStream("butterflies eat people".getBytes()));
        });

        commandLine.requestMultiCardMoveDestination();
        Command expectedCommand = controller.receivedCommands.poll();

        assertEquals("butterflies", expectedCommand.getCommand());
        assertArrayEquals(new String[] {"eat", "people"}, expectedCommand.getArguments());
    }

    /**
     * Tests that unknown input is sent to the controller to be handled.
     *
     * Relevant Axioms:
     * VIII.	commandLine.collectAndHandleInput()
     * send gibberish input that the command line is not going to recognize
     * Verify the mock controller receives a command with the command and arguments from the input
     */
    @Test
    public void collectAndHandleUnknownInputTest() {
        CompletableFuture.runAsync(() -> {
            try {
                wait(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.setIn(new ByteArrayInputStream("butterflies drink people".getBytes()));
        });

        commandLine.collectAndHandleInput();
        Command expectedCommand = controller.receivedCommands.poll();

        assertEquals("butterflies", expectedCommand.getCommand());
        assertArrayEquals(new String[] {"drink", "people"}, expectedCommand.getArguments());
    }

    /**
     * https://stackoverflow.com/questions/3814055/writing-data-to-system-in - Resetting and altering system in
     *
     * Resets the System.in InputStream so that it comes from its original source.
     */
    @After
    public void resetInputStreamForSystem() {
        System.setIn(oldSystemIn);
    }


    // Ignore this
    @Test
    public void troubleshootingTest() {
        CompletableFuture.runAsync(() -> {
            try {
                wait(2500); // This ensures that time is allowed for the command line to start asking for input.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.setIn(new ByteArrayInputStream("help\n".getBytes()));

        });

        commandLine.collectAndHandleInput();

        assertTrue(systemOutput.toString().contains("> "));
        //assertTrue(systemOutput.toString().contains(builder.toString()));
    }

}
