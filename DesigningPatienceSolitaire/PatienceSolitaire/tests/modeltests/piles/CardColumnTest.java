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
 * Axioms:
 * I.	cardColumn = cardColumn.create()
 * cardColumn.viewCard() == null or None or similar
 * II.	cardColumn = cardColumn.create()
 * cardColumn.addCard({the card})
 * cardColumn.viewCard() == {the card}
 * cardColumn.viewCard() == {the card} // The card is still there
 * cardColumn.removeCard() == {the card}
 * cardColumn.removeCard() == null or None or similar
 * III.	cardColumn = cardColumn.create()
 * cardColumn.verifyMoveIsLegal({an king of some sort}) == true
 * cardColumn.verifyMoveIsLegal({a non-king card}) == false
 * IV.	cardColumn = cardColumn.create()
 * cardColumn.addCard({an king of any suit})
 * cardColumn.verifyMoveIsLegal({a different king} == false
 * cardColumn.verifyMoveIsLegal({a Queen but a different suit} == true
 * cardColumn.verifyMoveIsLegal({a Queen of the same suit} == false
 * Continue building down to Ace, checking other cards that should or should not work
 * V.	A card column with cards
 * cardColumn.getCards() == {all of the cards in a collection}
 * cardColumn.getCards() == {all of the cards in a collection again} // No cards are removed
 * VI.	A card column with a few cards
 * cardColumn.removeMultipleCards({amount greater than what is in the column}) == null
 * VII.	A card column with a few cards
 * cardColumn.removeMultipleCards({amount equal or less}) == {the cards that were removed with the first being the card that was the furthest to the top of the column}
 * if all of them were removed
 * cardColumn.removeCard() == null
 * viewCard == null
 * removeMultipleCards({an amount}) == null
 * VIII.	cardColumn = cardColumn.create()
 * cardColumn.addMultipleCards({a collection of cards})
 * cardColumn.viewCard() == {the last card in the collection that was provided}
 * IX.	A card column with no cards
 * cardColumn.viewCardAtPos({any position}) == null
 * X.	A card column with enough cards
 * cardColumn.viewCardAtPos({any position}) == {the card at that position}
 * cardColumn.viewCardAtPos({any position}) == {the card at that position} // Card is still there
 * XI.	Card column with every card face down besides the bottom one
 * cardColumn.remove()
 * cardColumn.viewCard().toString() == “{Rank} of {Suit}” not “Unknown Card”
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-28
 */
public class CardColumnTest {
    private CardColumn column;

    @Before
    public void setUp() {
        column = new CardColumn();
    }

    /**
     * Tests the CardColumn's implementation against the rules put forth by the abstract class' methods.
     *
     * Relevant Axioms:
     * I.	cardColumn = cardColumn.create()
     * cardColumn.viewCard() == null or None or similar
     * II.	cardColumn = cardColumn.create()
     * cardColumn.addCard({the card})
     * cardColumn.viewCard() == {the card}
     * cardColumn.viewCard() == {the card} // The card is still there
     * cardColumn.removeCard() == {the card}
     * cardColumn.removeCard() == null or None or similar
     */
    @Test
    public void runInterfaceTests() {
        SelectablePileTestHelper helper = new SelectablePileTestHelper(column);

        helper.runAbstractClassImplementationTests();
    }

    /**
     * Tests that the card column appropriately checks the legality of its moves.
     *
     * Relevant Axioms:
     * III.	cardColumn = cardColumn.create()
     * cardColumn.verifyMoveIsLegal({an king of some sort}) == true
     * cardColumn.verifyMoveIsLegal({a non-king card}) == false
     * IV.	cardColumn = cardColumn.create()
     * cardColumn.addCard({an king of any suit})
     * cardColumn.verifyMoveIsLegal({a different king} == false
     * cardColumn.verifyMoveIsLegal({a Queen but a different suit} == true
     * cardColumn.verifyMoveIsLegal({a Queen of the same suit} == false
     * Continue building down to Ace, checking other cards that should or should not work
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
            int suitOrdinalColor = suit.ordinal() % 2;


            column.addCard(new Card(rank, suit));

            for (Card card : everyCard) {
                if (rank != Rank.Ace && card.getRank() == Rank.values()[rank.ordinal() - 1] && card.getSuit().ordinal() % 2 != suitOrdinalColor) { // If rank is one less and suit is the opposite color
                    assertTrue(column.verifyMoveIsLegal(card));
                } else {
                    assertFalse(column.verifyMoveIsLegal(card));
                }
            }
        }
    }

    /**
     * Tests that the cards are returned but not removed from the card column.
     *
     * Relevant Axioms:
     * V.	A card column with cards
     * cardColumn.getCards() == {all of the cards in a collection}
     * cardColumn.getCards() == {all of the cards in a collection again} // No cards are removed
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
     *
     * Relevant Axioms:
     * VI.	A card column with a few cards
     * cardColumn.removeMultipleCards({amount greater than what is in the column}) == null
     * VII.	A card column with a few cards
     * cardColumn.removeMultipleCards({amount equal or less}) == {the cards that were removed with the first being the card that was the furthest to the top of the column}
     * if all of them were removed
     * cardColumn.removeCard() == null
     * viewCard == null
     * removeMultipleCards({an amount}) == null
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
            assertEquals(cardsForTest[i + 7].getRank(), firstSetBack.get(firstSetBack.size() - (i + 1)).getRank());
            assertEquals(cardsForTest[i + 7].getSuit(), firstSetBack.get(firstSetBack.size() - (i + 1)).getSuit());
        }

        assertNull(column.removeMultipleCards(8)); // This will verify null because 8 out of the 15 have already been removed

    }

    /**
     * Tests that adding multiple cards works as intended
     *
     * Relevant Axioms:
     * VIII.	cardColumn = cardColumn.create()
     * cardColumn.addMultipleCards({a collection of cards})
     * cardColumn.viewCard() == {the last card in the collection that was provided}
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
     *
     * Relevant Axioms:
     * IX.	A card column with no cards
     * cardColumn.viewCardAtPos({any position}) == null
     * X.	A card column with enough cards
     * cardColumn.viewCardAtPos({any position}) == {the card at that position}
     * cardColumn.viewCardAtPos({any position}) == {the card at that position} // Card is still there
     */
    @Test
    public void viewCardAtPosTest() {
        assertNull(column.viewCardAtPos(3));

        Card[] cardsForTest = GeneralTestHelper.generateRandomUniqueCardArrayOfSpecifiedSize(4);

        for (Card card : cardsForTest) {
            card.show();
        }

        column.addMultipleCards(List.of(cardsForTest));

        assertEquals(cardsForTest[1], column.viewCardAtPos(3));
    }

    /**
     * Tests that the card column flips its bottom card face up when the card is revealed
     *
     * Relevant Axioms:
     * XI.	Card column with every card face down besides the bottom one
     * cardColumn.remove()
     * cardColumn.viewCard().toString() == “{Rank} of {Suit}” not “Unknown Card”
     */
    @Test
    public void verifyBottomCardFaceUpTest() {
        column.addMultipleCards(List.of(GeneralTestHelper.generateRandomUniqueCardArrayOfSpecifiedSize(2)));

        column.removeCard();

        assertNotEquals("UC", column.viewCard().toString());
    }
}
