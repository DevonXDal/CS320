package modeltests;

import models.Card;
import models.Deck;
import models.Table;

import models.piles.CardColumn;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Table class to find defects in its implementation early on.
 *
 * Axioms:
 * I.	table = table.create()
 * table.getDeck().size() == 52
 * table.getSelectablePile(“waste”).viewCard() == null
 * table.getSelectablePile(“foundation”, {foundation position number from 1 to 4}).viewCard() == null
 * table. getSelectablePile(“column” {card column position number from 1 to 7}).viewCard() == null
 * II.	table = table.create()
 * table.toString() == "[52]   [  ]      [  ]   [  ]   [  ]   [  ]"
 * III.	table = table.create()
 * deal the cards without shuffling as though the game was being set up
 * table.toString() ==
 *  - Image representation from notepad, where ?? represents a card that was in that order going down since the deck should
 *  be unshuffled
 *  3 spaces each slot unless three characters are used in the case of 10s, in which case it will be 2 spaces
 *  (waste pile has 5/6 spaces afterwards)
 * [24]   [  ]      [  ]   [  ]   [  ]   [  ]
 * [??]   [UC]   [UC]   [UC]   [UC]   [UC]   [UC]
 * [  ]   [??]   [UC]   [UC]   [UC]   [UC]   [UC]
 * [  ]   [  ]   [??]   [UC]   [UC]   [UC]   [UC]
 * [  ]   [  ]   [  ]   [??]   [UC]   [UC]   [UC]
 * [  ]   [  ]   [  ]   [  ]   [??]   [UC]   [UC]
 * [  ]   [  ]   [  ]   [  ]   [  ]   [??]   [UC]
 * [  ]   [  ]   [  ]    [  ]   [  ]   [  ]   [??]
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-27
 */
public class TableTest {
    private Table table;

    @Before
    public void setUp() {
        table = new Table();
    }

    /**
     * Tests that the appropriate keys work to access the piles and that those piles are in their initial states
     *
     * Relevant Axioms:
     * I.	table = table.create()
     * table.getDeck().size() == 52
     * table.getSelectablePile(“waste”).viewCard() == null
     * table.getSelectablePile(“foundation”, {foundation position number from 1 to 4}).viewCard() == null
     * table. getSelectablePile(“column” {card column position number from 1 to 7}).viewCard() == null
     */
    @Test
    public void constructorTest() {
        assertEquals(52, table.getDeck().size());
        assertNull(table.getSelectablePile("waste").viewCard());

        for (int i = 1; i <= 4; i++) { // For each foundation, if the foundation is null, an exception will fail the test
            assertNull(table.getSelectablePile("foundation " + i).viewCard());
        }

        for (int i = 1; i <= 7; i++) { // For each foundation, if the foundation is null, an exception will fail the test
            assertNull(table.getSelectablePile("column " + i).viewCard());
        }
    }

    /**
     * Tests that the toString when the table is done correctly when first created.
     *
     * Relevant Axioms:
     * II.	table = table.create()
     * table.toString() == "[52]   [  ]      [  ]   [  ]   [  ]   [  ]"
     */
    @Test
    public void initialToStringTest() {
        assertEquals("[52]   []      []   []   []   []", table.toString());
    }

    /**
     * Tests that the toString representation for the table is correct after dealing out cards to begin the game.
     *
     * Relevant Axioms:
     * III.	table = table.create()
     * deal the cards without shuffling as though the game was being set up
     * table.toString() ==
     *  - Image representation from notepad, where ?? represents a card that was in that order going down since the deck should be unshuffled
     * 3 spaces each slot unless three characters are used in the case of 10s, in which case it will be 2 spaces (waste pile has 5/6 spaces afterwards)
     * [24]   [  ]      [  ]   [  ]   [  ]   [  ]
     * [??]   [UC]   [UC]   [UC]   [UC]   [UC]   [UC]
     * [  ]   [??]   [UC]   [UC]   [UC]   [UC]   [UC]
     * [  ]   [  ]   [??]   [UC]   [UC]   [UC]   [UC]
     * [  ]   [  ]   [  ]   [??]   [UC]   [UC]   [UC]
     * [  ]   [  ]   [  ]   [  ]   [??]   [UC]   [UC]
     * [  ]   [  ]   [  ]   [  ]   [  ]   [??]   [UC]
     * [  ]   [  ]   [  ]    [  ]   [  ]   [  ]   [??]
     */
    @Test
    public void toStringTest() {
        StringBuilder builder = new StringBuilder();

        builder.append("[24]   [  ]      [  ]   [  ]   [  ]   [  ]\n");
        builder.append("[A♢]   [UC]   [UC]   [UC]   [UC]   [UC]   [UC]\n");
        builder.append("[  ]   [8♢]   [UC]   [UC]   [UC]   [UC]   [UC]\n");
        builder.append("[  ]   [  ]   [A♧]   [UC]   [UC]   [UC]   [UC]\n");
        builder.append("[  ]   [  ]   [  ]   [6♧]   [UC]   [UC]   [UC]\n");
        builder.append("[  ]   [  ]   [  ]   [  ]   [10♧]  [UC]   [UC]\n");
        builder.append("[  ]   [  ]   [  ]   [  ]   [  ]   [K♧]   [UC]\n");
        builder.append("[  ]   [  ]   [  ]   [  ]   [  ]   [  ]   [2♡]\n");

        Deck deck = table.getDeck();
        CardColumn[] columnsFromTable = new CardColumn[7];

        for (int i = 1; i <= 7; i++) { // For each foundation, if the foundation is null, an exception will fail the test
            columnsFromTable[i - 1] = (CardColumn) table.getSelectablePile("column " + i); // If this fails, the table is set up wrong
        }

        for (int currentColumnIndex = 0, indexOfCurrentStartingColumn = 0; indexOfCurrentStartingColumn != columnsFromTable.length; currentColumnIndex++) {
            columnsFromTable[currentColumnIndex].addCard(deck.draw());

            if (currentColumnIndex + 1 == columnsFromTable.length) {
                indexOfCurrentStartingColumn++;
                currentColumnIndex = indexOfCurrentStartingColumn - 1;
            }
        }

        assertEquals(builder.toString(), table.toString());
    }
}
