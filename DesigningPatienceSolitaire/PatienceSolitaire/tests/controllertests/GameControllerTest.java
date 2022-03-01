package controllertests;

import controller.GameController;
import mocks.MockCommandLine;
import models.Card;
import models.Player;
import models.Table;

import models.enumerations.Rank;
import models.enumerations.Suit;
import models.piles.CardColumn;
import models.piles.SelectablePile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import other.Command;
import view.ICommandLine;

import java.util.List;

/**
 * Tests the GameController class. This is the most important class to test as it ensures that the internal game loop is
 * functioning as intended.
 *
 * Axioms:
 * I.   Create the controller
 * Initialize the game loop
 * Test that the mock command line is asked for input
 * II.	Rig the game so that cards from one card column can be moved to another
 * Start the game loop
 * Reply to the request for input with a command to move multiple cards from known initial column
 * Reply to the second request by supplying the expected command to move those cards to another column
 * Verify that the cards have been moved
 * III.	Start the game loop
 * Reply to the request for input with a command to move multiple cards from known initial column
 * Reply to the second request by requesting to deselect the cards
 * Verify that the cards are deselected
 * IV.	Rig the game table as necessary
 * Start the game loop
 * Respond for the request for input by selecting the waste pile and receiving that the waste pile is selected through the information returned to the mock command line object
 * Than reply to the next requests by sending commands to change the selection to each of the foundations and then the columns, checking that the selection is changed each time
 * This can be done by rigging each selectable to have at least one card and checking for the name of the card in the output sent to be printed; additionally, check that the player has that pile selected by comparing the memory addresses or by a similar method
 * Respond by deselecting the current selection
 * Ensure that nothing is selected
 * V.	Rig the table as necessary
 * Start the game loop
 * Perform both a selection and a move command
 * Ensure that the cards are moved
 * VI.	Rig the table as necessary
 * Start the game loop
 * Do a series of selections and movements to ensure that legal cards get moved to each destination pile
 * VII.	Start the game loop
 * Send a command to have the controller draw a card
 * Ensure that a card has been moved from the deck into the waste pile
 * VIII.	Rig the deck to have no cards left and the waste pile to have cards
 * Start the game loop
 * React to requests for input by sending a command to draw a card
 * Ensure that the deck is refilled by all but one card and that the first card is drawn into the waste pile
 * IX.	Start the game loop
 * Request that the controller restart the game
 * Ensure that the controller sends back a message to print about the game reset to the command line
 * Select a card from one of the card columns
 * Ensure that the player from the previous game does not know about the change
 * Ensure that the table now in use by the controller is different from the testing table
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-27
 */
public class GameControllerTest {
    private Table table;
    private Player player;
    private MockCommandLine commandLine;
    private GameController controller;

    /**
     * Creates the four main objects used across the tests to cut down on code duplication
     */
    @Before
    public void setUp() {
        table = new Table();
        player = new Player();
        commandLine = new MockCommandLine();

        controller = new GameController(commandLine, table, player);

        commandLine.setController(controller);
    }

    /**
     * Tests that input is requested of the command line at some point during execution.
     *
     * Relevant Axioms:
     * I.	Create the controller
     * Initialize the game loop
     * Test that the mock command line is asked for input
     */
    @Test
    public void inputRequestedOfCommandLineTest() {
        MockCommandLine.mockUserInput.add(new Command("exit", new String[0]));

        controller.initializeGame(true);

        assertTrue(MockCommandLine.mockUserInput.isEmpty());
    }

    /**
     * Tests that multiple cards are moved from one card column to another correctly
     *
     * Relevant Axioms:
     * II.	Rig the game so that cards from one card column can be moved to another
     * Start the game loop
     * Reply to the request for input with a command to move multiple cards from known initial column
     * Reply to the second request by supplying the expected command to move those cards to another column
     * Verify that the cards have been moved
     */
    @Test
    public void moveMultipleCardsTest() {
        SelectablePile[] twoColumns = new CardColumn[2];
        twoColumns[0] = table.getSelectablePile("column 1");
        twoColumns[1] = table.getSelectablePile("column 2");

        Card[] columnOneCards = new Card[3];
        columnOneCards[0] = new Card(Rank.Seven, Suit.Clubs);
        columnOneCards[1] = new Card(Rank.Six, Suit.Hearts);

        Card columnTwoCard = new Card (Rank.Eight, Suit.Hearts);

        for (Card card : columnOneCards) {
            if (card != null) {
                card.show();
                twoColumns[0].addCard(card);
            }
        }

        columnTwoCard.show();
        twoColumns[1].addCard(columnTwoCard);

        MockCommandLine.mockUserInput.add(new Command("select", new String[] {"2", "cards", "from", "column", "1"}));
        MockCommandLine.mockUserInput.add(new Command("column", new String[] {"2"}));
        MockCommandLine.mockUserInput.add(new Command("exit", new String[0]));

        controller.initializeGame(true);

        assertEquals("What card column do you wish to move the group of cards to? Use 'column #' with # being the column's identifying position or 'deselect'",
                MockCommandLine.systemOutput.poll());
        assertTrue(MockCommandLine.systemOutput.poll().contains("[  ]")); // Output of the table occurs on a successful move

        assertEquals(columnOneCards[1], twoColumns[1].viewCard()); // This card should be on the bottom
    }

    /**
     * Tests that the deselect command works as intended when moving multiple cards
     *
     * Relevant Axioms:
     * III.	Start the game loop
     * Reply to the request for input with a command to move multiple cards from known initial column
     * Reply to the second request by requesting to deselect the cards
     * Verify that the cards are deselected
     */
    @Test
    public void moveMultipleCardsDeselectionTest() {
        SelectablePile[] twoColumns = new CardColumn[2];
        twoColumns[0] = table.getSelectablePile("column 1");
        twoColumns[1] = table.getSelectablePile("column 2");

        Card[] columnOneCards = new Card[3];
        columnOneCards[0] = new Card(Rank.Seven, Suit.Clubs);
        columnOneCards[1] = new Card(Rank.Six, Suit.Hearts);

        Card columnTwoCard = new Card (Rank.Eight, Suit.Hearts);

        for (Card card : columnOneCards) {
            card.show();
            twoColumns[0].addCard(card);
        }

        columnTwoCard.show();
        twoColumns[1].addCard(columnTwoCard);

        MockCommandLine.mockUserInput.add(new Command("select", new String[] {"2", "cards", "from", "column", "1"}));
        MockCommandLine.mockUserInput.add(new Command("deselect", new String[0]));
        MockCommandLine.mockUserInput.add(new Command("exit", new String[0]));

        controller.initializeGame(true);

        assertEquals("What card column do you wish to move the group of cards to? Use 'column #' with # being the column's identifying position or 'deselect'",
                MockCommandLine.systemOutput.poll());
        assertTrue(MockCommandLine.systemOutput.poll().contains("Currently Selected Card(s): N/A")); // Output of the table occurs on a successful move

    }

    /**
     * Tests that each of the piles are selectable when they have a card.
     *
     * Relevant Axioms:
     * IV.	Rig the game table as necessary
     * Start the game loop
     * Respond for the request for input by selecting the waste pile and receiving that the waste pile is selected through the information returned to the mock command line object
     * Than reply to the next requests by sending commands to change the selection to each of the foundations and then the columns, checking that the selection is changed each time
     * This can be done by rigging each selectable to have at least one card and checking for the name of the card in the output sent to be printed; additionally, check that the player has that pile selected by comparing the memory addresses or by a similar method
     * Respond by deselecting the current selection
     * Ensure that nothing is selected
     */
    @Test
    public void pileSelectionTest() {
        SelectablePile[] piles = getAllPiles();


        for (int i = 1; i <= 12; i++) {
            piles[i - 1].addCard(new Card(Rank.values()[i - 1], Suit.Hearts));
        }

        MockCommandLine.mockUserInput.add(new Command("select", new String[] {"waste"}));

        for (int i = 1; i < 5; i++) {
            MockCommandLine.mockUserInput.add(new Command("select", new String[] {"foundation", String.valueOf(i)}));
        }

        for (int i = 1; i < 8; i++) {
            MockCommandLine.mockUserInput.add(new Command("select", new String[] {"column", String.valueOf(i)}));
        }

        MockCommandLine.mockUserInput.add(new Command("exit", new String[0]));

        controller.initializeGame(true);

        for (int i = 0; i < 12; i++) {
            assertTrue(MockCommandLine.systemOutput.poll().contains("Currently Selected Card(s): " + Rank.values()[i] + " of " + Suit.Hearts));
        }
    }

    /**
     * Tests that the movement is handled by the controller and that only legal moves are taken. If any action were to fail
     * then the test will fail because it will not run the other user input calls correctly.
     *
     * Relevant Axioms:
     * V.	Rig the table as necessary
     * Start the game loop
     * Perform both a selection and a move command
     * Ensure that the cards are moved
     * VI.	Rig the table as necessary
     * Start the game loop
     * Do a series of selections and movements to ensure that legal cards get moved to each destination pile
     */
    @Test
    public void checkingMoveValidityBeforeMovingTest() {
        SelectablePile[] piles = getAllPiles(); // Waste pile or [0] will be used to move cards from

        piles[0].addCard(new Card(Rank.Queen, Suit.Clubs));
        piles[0].addCard(new Card(Rank.Two, Suit.Hearts));
        piles[0].addCard(new Card(Rank.King, Suit.Diamonds));
        piles[0].addCard(new Card(Rank.Ace, Suit.Hearts));

        MockCommandLine.mockUserInput.add(new Command("select", new String[] {"waste"})); // Select Ace of Hearts
        MockCommandLine.mockUserInput.add(new Command("move", new String[] {"column", "1"})); // Illegal Move
        MockCommandLine.mockUserInput.add(new Command("move", new String[] {"foundation", "1"})); // Correct Move
        MockCommandLine.mockUserInput.add(new Command("select", new String[] {"waste"})); // Select King of Diamonds
        MockCommandLine.mockUserInput.add(new Command("move", new String[] {"foundation", "1"})); // Illegal Move
        MockCommandLine.mockUserInput.add(new Command("move", new String[] {"column", "1"})); // Correct Move
        MockCommandLine.mockUserInput.add(new Command("select", new String[] {"waste"})); // Select Two of Hearts
        MockCommandLine.mockUserInput.add(new Command("move", new String[] {"column", "1"})); // Illegal Move
        MockCommandLine.mockUserInput.add(new Command("move", new String[] {"foundation", "1"})); // Correct Move
        MockCommandLine.mockUserInput.add(new Command("select", new String[] {"waste"}));; // Select Queen of Clubs
        MockCommandLine.mockUserInput.add(new Command("move", new String[] {"foundation", "1"})); // Illegal Move
        MockCommandLine.mockUserInput.add(new Command("move", new String[] {"column", "1"})); // Correct Move
        MockCommandLine.mockUserInput.add(new Command("exit", new String[0])); // Finish the program

        controller.initializeGame(true);

        assertEquals("Two of Hearts", piles[1].viewCard().toFullName()); // Ensure that the Two of Hearts was built up to successfully
        assertEquals("Queen of Clubs", piles[5].viewCard().toFullName()); // Ensure that the Queen of Clubs was built up to successfully
        assertEquals(2, ((CardColumn) piles[5]).getCards().size()); // If more than two cards were added than there is a problem
    }

    /**
     * Tests that the controller will draw a card from the deck correctly and place it into the waste pile when asked to
     * do so.
     *
     * Relevant Axioms:
     * VII.	Start the game loop
     * Send a command to have the controller draw a card
     * Ensure that a card has been moved from the deck into the waste pile
     */
    @Test
    public void drawCardTest() {
        while (true) { // Drain the cards from the deck
            Card currentCard = table.getDeck().draw();

            if (currentCard == null) {
                break;
            }
        }

        table.getDeck().insertCardsInInvertedOrder(List.of(new Card(Rank.Eight, Suit.Hearts))); // The card doesn't really matter
        SelectablePile[] piles = getAllPiles();

        MockCommandLine.mockUserInput.add(new Command("draw", new String[0])); // Draw the card
        MockCommandLine.mockUserInput.add(new Command("exit", new String[0])); // Finish the program

        controller.initializeGame(true);

        assertEquals(0, table.getDeck().size());
        assertEquals("Eight of Hearts", piles[0].viewCard().toFullName());
    }

    /**
     * Tests that the controller refills the deck with cards from the waste pile when the deck is out of cards
     *
     * Relevant Axioms:
     * VIII.	Rig the deck to have no cards left and the waste pile to have cards
     * Start the game loop
     * React to requests for input by sending a command to draw a card
     * Ensure that the deck is refilled by all but one card and that the first card is drawn into the waste pile
     */
    @Test
    public void refillDeckWithWastePileTest() {
        while (true) { // Drain the cards from the deck
            Card currentCard = table.getDeck().draw();

            if (currentCard == null) {
                break;
            }
        }

        SelectablePile[] piles = getAllPiles();

        piles[0].addCard(new Card(Rank.Seven, Suit.Hearts));

        MockCommandLine.mockUserInput.add(new Command("draw", new String[0])); // Draw the card
        MockCommandLine.mockUserInput.add(new Command("exit", new String[0])); // Finish the program

        controller.initializeGame(true);

        assertEquals(1, table.getDeck().size());
        assertNull(piles[0].viewCard());
    }

    /**
     * Tests the resetGame method to ensure that the game is properly reset
     *
     * Relevant Axioms:
     * IX.	Start the game loop
     * Request that the controller restart the game
     * Ensure that the controller sends back a message to print about the game reset to the command line
     * Select a card from one of the card columns
     * Ensure that the player from the previous game does not know about the change
     * Ensure that the table now in use by the controller is different from the testing table
     */
    @Test
    public void restartGameTest() {
        String currentTableToString = table.toString();
        MockCommandLine.mockUserInput.add(new Command("restart", new String[0]));
        MockCommandLine.mockUserInput.add(new Command("select", new String[] {"column", "1"})); // This will/should fail if the table is not reset
        MockCommandLine.mockUserInput.add(new Command("exit", new String[0])); // Finish the program

        controller.initializeGame(true);

        MockCommandLine.systemOutput.poll(); // Get rid of the restart information as it is not useful
        MockCommandLine.systemOutput.poll(); // Get rid of the initial status because it is not useful either

        assertFalse(MockCommandLine.systemOutput.poll().contains("N/A")); // From Current Selection: N/A
        assertNull(player.getSelectedSource());
        assertEquals(currentTableToString, table.toString());
    }

    // Returns all piles in the order: waste, foundation 1-4, and finally column 1-7
    private SelectablePile[] getAllPiles() {
        SelectablePile[] piles = new SelectablePile[12];

        piles[0] = table.getSelectablePile("waste");

        for (int i = 1; i <= 4; i++) { // For each foundation, if the foundation is null, an exception will fail the test
            piles[i] = table.getSelectablePile("foundation " + i);
        }

        for (int i = 1; i <= 7; i++) { // For each foundation, if the foundation is null, an exception will fail the test
            piles[i + 4] = table.getSelectablePile("column " + i);
        }

        return piles;
    }


}
