package modeltests.piles;

import models.Card;
import models.enumerations.Rank;
import models.enumerations.Suit;
import models.piles.CardColumn;
import org.junit.Before;
import org.junit.Test;
import other.GeneralTestHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests the CardColumn class to ensure that it is implemented correctly.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-26
 */
public class CardColumnTest {
    private CardColumn column;

    @Before
    public void setUp() {
        column = new CardColumn();
    }

    /**
     * Tests the CardColumn's implementation against the rules put forth by the abstract class' methods.
     */
    @Test
    public void runInterfaceTests() {
        SelectablePileTestHelper helper = new SelectablePileTestHelper(column);

        helper.runAbstractClassImplementationTests();
    }

    /**
     * Tests that the card column appropriately checks the legality of its moves.
     */
    @Test
    public void verifyMoveIsLegalTest() {

        Card[] everyCard = GeneralTestHelper.getAllPlayingCardsOrderedBySuitThenRank();

        for (Card card : everyCard) {
            if (card.getRank() == Rank.King) {
                assertTrue(column.verifyMoveIsLegal(card));
            } else {
                assertFalse(column.verifyMoveIsLegal(card));
            }
        }

        List<Rank> reversedCollection = Arrays.asList(Rank.values());
        Collections.reverse(reversedCollection);

        for (Rank rank : reversedCollection) {
            Suit suit = GeneralTestHelper.generateRandomSuit();
            int suitOrdinal = suit.ordinal();

            Suit otherBadSuit = (suitOrdinal > 1) ? Suit.values()[suitOrdinal - 2] : Suit.values()[suitOrdinal + 2]; // Finds the other black or red suit card

            column.addCard(new Card(rank, suit));

            for (Card card : everyCard) {
                if (card.getRank() == Rank.values()[rank.ordinal() - 1] && (card.getSuit() != suit || card.getSuit() != otherBadSuit)) { // If rank is one less and suit is the opposite color
                    assertTrue(column.verifyMoveIsLegal(card));
                } else {
                    assertFalse(column.verifyMoveIsLegal(card));
                }
            }
        }
    }

    /**
     * Tests that the cards are returned but not removed from the card column.
     */
    @Test
    public void getCardsTest() {
        assertTrue(column.getCards().isEmpty());

        column.addMultipleCards(Arrays.asList(GeneralTestHelper.generateRandomUniqueCardArrayOfSpecifiedSize(30)));

        assertEquals(30, column.getCards().size());
        assertFalse(column.getCards().isEmpty()); // If getCards() removes its cards, this will fail the test.
    }

    /**
     * Tests that removing multiple cards, maintains the order of the cards returned, and that if no cards are there, it is null
     */
    @Test
    public void removeMultipleCardsTest() {
        assertNull(column.removeMultipleCards(1)); // This amount should not matter

        Card[] cardsForTest = GeneralTestHelper.generateRandomUniqueCardArrayOfSpecifiedSize(15);

        for (Card card : cardsForTest) {
            column.addCard(card);
        }

        List<Card> firstSetBack = column.removeMultipleCards(8);
        for (int i = 0; i < 8; i++) {
            assertEquals(cardsForTest[i], firstSetBack.get(i));
        }

        assertNull(column.removeMultipleCards(8)); // This will verify null because 8 out of the 15 have already been removed

    }

    /**
     * Tests that adding multiple cards works as intended
     */
    @Test
    public void addMultipleCardsTest() {
        Card[] cardsForTest = GeneralTestHelper.generateRandomUniqueCardArrayOfSpecifiedSize(15);

        column.addMultipleCards(List.of(cardsForTest));

        for (int i = cardsForTest.length - 1; i >= 0; i--) {
            assertEquals(cardsForTest[i], column.removeCard());
        }
    }

    /**
     * Tests that a card can be viewed at any position so long as the card is there
     */
    @Test
    public void viewCardAtPos() {
        assertNull(column.viewCardAtPos(3));

        Card[] cardsForTest = GeneralTestHelper.generateRandomUniqueCardArrayOfSpecifiedSize(4);

        column.addMultipleCards(List.of(cardsForTest));

        assertEquals(cardsForTest[2], column.viewCardAtPos(3));
    }

    /**
     * Tests that the card column flips its bottom card face up when the card is revealed
     */
    @Test
    public void verifyBottomCardFaceUpTest() {
        column.addMultipleCards(List.of(GeneralTestHelper.generateRandomUniqueCardArrayOfSpecifiedSize(2)));

        column.removeCard();

        assertNotEquals("UC", column.viewCard().toString());
    }
}
