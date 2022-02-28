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
 * Axioms:
 * I.	wastePile = wastePile.create()
 * wastePile.viewCard() == null or None or similar
 * II.	wastePile = wastePile.create()
 * wastePile.addCard({the card})
 * wastePile.viewCard() == {the card}
 * wastePile.viewCard() == {the card} // The card is still there
 * wastePile.removeCard() == {the card}
 * wastePile.removeCard() == null or None or similar
 * III.	a wastePile with a few cards
 * wastePile.viewCard == {the most recently added]}
 * IV.	A waste pile with some quantity of cards
 * wastePile.removeAll() == {a collection of cards with the quantity equaling what was removed, may be zero; the collection matches the cards that were inserted into the waste pile in order}
 * wastePile.viewCard() == null
 * V.	Any waste pile
 * wastePile.verifyMoveIsLegal({any card}) == false
 * VI.	a waste pile with no cards
 * wastePile.toString() == “[  ]”
 * VII.	a waste pile with at least one card
 * wastePile.toString == “[??]” where ?? is the two-three character representation of the top card
 * VIII.	A waste pile with a card that was added face down
 * wastePile.viewCard().toString != “UC”
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-28
 */
public class WastePileTest {
    private WastePile wastePile;

    @Before
    public void setUp() {
        wastePile = new WastePile();
    }

    /**
     * Tests the WastePile implementation against the rules put forth by the abstract class' methods.
     *
     * Relevant Axioms:
     * I.	wastePile = wastePile.create()
     * wastePile.viewCard() == null or None or similar
     * II.	wastePile = wastePile.create()
     * wastePile.addCard({the card})
     * wastePile.viewCard() == {the card}
     * wastePile.viewCard() == {the card} // The card is still there
     * wastePile.removeCard() == {the card}
     * wastePile.removeCard() == null or None or similar
     * III.	a wastePile with a few cards
     * wastePile.viewCard == {the most recently added]}
     */
    @Test
    public void runInterfaceTests() {
        SelectablePileTestHelper helper = new SelectablePileTestHelper(wastePile);

        helper.runAbstractClassImplementationTests();
    }

    /**
     * Tests that the waste pile adds the card face up.
     *
     * Relevant Axioms:
     * VIII.	A waste pile with a card that was added face down
     * wastePile.viewCard().toString != “UC”
     */
    @Test
    public void visibleCardWhenAddingTest() {
        Card card = new Card(GeneralTestHelper.generateRandomRank(), GeneralTestHelper.generateRandomSuit());

        wastePile.addCard(card);

        assertNotEquals("UC", wastePile.viewCard().toString());
    }

    /**
     * Tests that the removeAll() method for the waste pile works as intended.
     *
     * Relevant Axioms:
     * IV.	A waste pile with some quantity of cards
     * wastePile.removeAll() == {a collection of cards with the quantity equaling what was removed, may be zero; the collection matches the cards that were inserted into the waste pile in order}
     * wastePile.viewCard() == null
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
     *
     * Relevant Axioms:
     * V.	Any waste pile
     * wastePile.verifyMoveIsLegal({any card}) == false
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

    /**
     * Tests that the waste pile displays itself appropriately
     *
     * Relevant Axioms:
     * VI.	a waste pile with no cards
     * wastePile.toString() == “[  ]”
     * VII.	a waste pile with at least one card
     * wastePile.toString == “[??]” where ?? is the two-three character representation of the top card
     */
    @Test
    public void toStringTest() {
        Card randomCard = GeneralTestHelper.generateRandomCard();

        assertEquals("The empty waste pile did not generate its string correctly", "[  ]", randomCard.toString());

        wastePile.addCard(randomCard);

        assertEquals("The waste pile did not generate its string correctly", "[" + randomCard + "]", randomCard.toString());
    }
}
