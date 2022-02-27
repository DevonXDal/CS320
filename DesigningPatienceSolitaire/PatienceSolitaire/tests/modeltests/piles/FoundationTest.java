package modeltests.piles;

import models.Card;
import models.enumerations.Rank;
import models.enumerations.Suit;
import models.piles.Foundation;
import models.piles.WastePile;
import org.junit.Before;
import org.junit.Test;
import other.GeneralTestHelper;

import static org.junit.Assert.*;

/**
 * Tests the Foundation class to ensure that it is implemented correctly.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-26
 */
public class FoundationTest {
    private Foundation foundation;

    @Before
    public void setUp() {
        foundation = new Foundation();
    }

    /**
     * Tests the Foundation's implementation against the rules put forth by the abstract class' methods.
     */
    @Test
    public void runInterfaceTests() {
        SelectablePileTestHelper helper = new SelectablePileTestHelper(foundation);

        helper.runAbstractClassImplementationTests();
    }

    /**
     * Tests that the foundation is only completed if the top card is the King.
     */
    @Test
    public void checkFoundationCompleted() {
        for (Rank rank : Rank.values()) {
            Suit suit = GeneralTestHelper.generateRandomSuit();

            foundation.addCard(new Card(rank, suit));

            if (foundation.viewCard().getRank() == Rank.King) { // If rank is one more and suit is the same
                assertTrue(foundation.checkFoundationCompleted());
            } else {
                assertFalse(foundation.checkFoundationCompleted());
            }
        }
    }

    /**
     * Tests that the foundation appropriately checks the legality of its moves.
     */
    @Test
    public void verifyMoveIsLegalTest() {

        Card[] everyCard = GeneralTestHelper.getAllPlayingCardsOrderedBySuitThenRank();

        for (Card card : everyCard) {
            if (card.getRank() == Rank.Ace) {
                assertTrue(foundation.verifyMoveIsLegal(card));
            } else {
                assertFalse(foundation.verifyMoveIsLegal(card));
            }
        }

        for (Rank rank : Rank.values()) {
            Suit suit = GeneralTestHelper.generateRandomSuit();

            foundation.addCard(new Card(rank, suit));

            for (Card card : everyCard) {
                if (card.getRank() == Rank.values()[rank.ordinal() + 1] && card.getSuit() == suit) { // If rank is one more and suit is the same
                    assertTrue(foundation.verifyMoveIsLegal(card));
                } else {
                    assertFalse(foundation.verifyMoveIsLegal(card));
                }
            }
        }
    }

    /**
     * Tests that the foundation displays itself appropriately
     */
    @Test
    public void toStringTest() {
        Card randomCard = GeneralTestHelper.generateRandomCard();

        assertEquals("The empty foundation did not generate its string correctly", "[  ]", randomCard.toString());

        foundation.addCard(randomCard);

        assertEquals("The foundation did not generate its string correctly", "[" + randomCard + "]", randomCard.toString());
    }
}
