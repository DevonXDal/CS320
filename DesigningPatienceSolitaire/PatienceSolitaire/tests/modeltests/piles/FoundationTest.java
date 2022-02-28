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
 * Axioms:
 * I.	foundation = foundation.create()
 * foundation.viewCard() == null or None or similar
 * II.	foundation = foundation.create()
 * foundation.addCard({the card})
 * foundation.viewCard() == {the card}
 * foundation.viewCard() == {the card} // The card is still there
 * foundation.removeCard() == {the card}
 * foundation.removeCard() == null or None or similar
 * III.	foundation = foundation.create()
 * foundation.verifyMoveIsLegal({an ace of some sort}) == true
 * foundation.verifyMoveIsLegal({a non-ace card}) == false
 * IV.	foundation = foundation.create()
 * foundation.addCard({an ace of any suit})
 * foundation.verifyMoveIsLegal({a different ace} == false
 * foundation.verifyMoveIsLegal({a two but a different suit} == false
 * foundation.verifyMoveIsLegal({a two of the same suit} == true
 * Continue building up to King, checking other cards that should or should not work
 * V.	a foundation that has been built up to King
 * foundation.checkFoundationCompleted() == true
 * VI.	a foundation that may have cards but does not have all the way up to king
 * foundation.checkFoundationCompleted() == false
 * VII.	a foundation with a few cards
 * foundation.viewCard == {the top most card [highest rank]}
 * VIII.	a foundation with no cards
 * foundation.toString() == “[  ]”
 * IX.	a foundation with at least one card
 * foundation.toString == “[??]” where ?? is the two-three character representation of the top card
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-28
 */
public class FoundationTest {
    private Foundation foundation;

    @Before
    public void setUp() {
        foundation = new Foundation();
    }

    /**
     * Tests the Foundation's implementation against the rules put forth by the abstract class' methods.
     *
     * Relevant Axioms:
     * I.	foundation = foundation.create()
     * foundation.viewCard() == null or None or similar
     * II.	foundation = foundation.create()
     * foundation.addCard({the card})
     * foundation.viewCard() == {the card}
     * foundation.viewCard() == {the card} // The card is still there
     * foundation.removeCard() == {the card}
     * foundation.removeCard() == null or None or similar
     * VII.	a foundation with a few cards
     * foundation.viewCard == {the top most card [highest rank]}
     */
    @Test
    public void runInterfaceTests() {
        SelectablePileTestHelper helper = new SelectablePileTestHelper(foundation);

        helper.runAbstractClassImplementationTests();
    }

    /**
     * Tests that the foundation is only completed if the top card is the King.
     *
     * Relevant Axioms:
     * V.	a foundation that has been built up to King
     * foundation.checkFoundationCompleted() == true
     * VI.	a foundation that may have cards but does not have all the way up to king
     * foundation.checkFoundationCompleted() == false
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
     *
     * Relevant Axioms:
     * III.	foundation = foundation.create()
     * foundation.verifyMoveIsLegal({an ace of some sort}) == true
     * foundation.verifyMoveIsLegal({a non-ace card}) == false
     * IV.	foundation = foundation.create()
     * foundation.addCard({an ace of any suit})
     * foundation.verifyMoveIsLegal({a different ace} == false
     * foundation.verifyMoveIsLegal({a two but a different suit} == false
     * foundation.verifyMoveIsLegal({a two of the same suit} == true
     * Continue building up to King, checking other cards that should or should not work
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
     *
     * Relevant Axioms:
     * VIII.	a foundation with no cards
     * foundation.toString() == “[  ]”
     * IX.	a foundation with at least one card
     * foundation.toString == “[??]” where ?? is the two-three character representation of the top card
     */
    @Test
    public void toStringTest() {
        Card randomCard = GeneralTestHelper.generateRandomCard();

        assertEquals("The empty foundation did not generate its string correctly", "[  ]", randomCard.toString());

        foundation.addCard(randomCard);

        assertEquals("The foundation did not generate its string correctly", "[" + randomCard + "]", randomCard.toString());
    }
}
