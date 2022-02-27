package modeltests.piles;

import models.Card;
import models.piles.WastePile;
import org.junit.Before;
import org.junit.Test;
import other.GeneralTestHelper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Tests the implementation of the waste pile to find defects early on.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-26
 */
public class WastePileTest {
    private WastePile wastePile;

    @Before
    public void setUp() {
        wastePile = new WastePile();
    }

    /**
     * Tests the WastePile implementation against the rules put forth by the abstract class' methods.
     */
    @Test
    public void runInterfaceTests() {
        SelectablePileTestHelper helper = new SelectablePileTestHelper(wastePile);

        helper.runAbstractClassImplementationTests();
    }

    /**
     * Tests that the removeAll() method for the waste pile works as intended.
     */
    @Test
    public void removeAllTest() {
        Card[] sixRandomButUniqueCards = GeneralTestHelper.generateRandomUniqueCardArrayOfSpecifiedSize(6);

        Arrays.stream(sixRandomButUniqueCards).forEachOrdered(e -> wastePile.addCard(e)); // Add each card in order

        List<Card> wastePileCards = wastePile.removeAll();

        for (int i = 0; i < 6; i++) { // Ensure the order exists and is not ruined
            assertEquals("Incorrect or missing order of returned cards from removeAll testing",
                    sixRandomButUniqueCards[i], wastePileCards.get(i));
        }

        assertNull(wastePile.viewCard());
    }

    /**
     * Tests that the waste pile appropriately checks the legality of its moves. This should always be false.
     */
    @Test
    public void verifyMoveIsLegalTest() {

        Card[] everyCard = GeneralTestHelper.getAllPlayingCardsOrderedBySuitThenRank();

        for (Card card : everyCard) {
            assertFalse(card + " is a valid card to move to an empty waste pile", wastePile.verifyMoveIsLegal(card));
        }

        wastePile.addCard(GeneralTestHelper.generateRandomCard());

        for (Card card : everyCard) {
            assertFalse(card + " is a valid card to move to a waste pile that has a card", wastePile.verifyMoveIsLegal(card));
        }
    }


    // toString()

    /**
     * Tests that the waste pile displays itself appropriately
     */
    @Test
    public void toStringTest() {
        Card randomCard = GeneralTestHelper.generateRandomCard();

        assertEquals("The empty waste pile did not generate its string correctly", "[  ]", randomCard);

        wastePile.addCard(randomCard);

        assertEquals("The waste pile did not generate its string correctly", "[" + randomCard + "]", randomCard);
    }
}
