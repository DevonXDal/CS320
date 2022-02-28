package modeltests;

import models.Card;
import models.Deck;
import models.enumerations.Rank;
import models.enumerations.Suit;
import org.junit.Before;
import org.junit.Test;
import other.GeneralTestHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests the Deck class to find defects before they become an issue
 *
 * Axioms:
 * I.	(deck.createDeck()).size() == 52
 * II.	deck.createDeck()
 * deck.shuffle()
 * check the difference between the cards
 * adjacentCardsCount <= 26 (Ensure that cards are shuffled well and do not have cards in sequential order too frequently
 * or of just differing suits)
 * III.	deck.createDeck()
 * card = deck.draw()
 * card.show()
 * card.toFullName() == “Ace of Diamonds”
 * Repeat to verify all 13 ranks appear for each suit
 * deck.size() == 0
 * IV.	deck.createDeck()
 * deck.draw()
 * deck.size() == 51
 * deck.draw()
 * deck.size() == 50
 * V.	V.	deck = [a deck with cards missing]
 * deck.insertCardsInInvertedOrder (cardList)
 * deck == [the deck with the original cards on the bottom in their order followed by the cards provided in the order
 * that they came with the last card being at the top of the deck with cards face down]
 * VI.	Any deck the has been created
 * deck.toString() == [#] where # is the number of cards in the deck
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-24
 */
public class DeckTest {
    private static Deck deck;

    /**
     * Sets up a fresh deck for use with testing.
     */
    @Before
    public void setUp() {
        deck = new Deck();
    }

    /**
     * Tests that the deck is created with an initial 52 cards.
     *
     * Relevant Axioms:
     * I.	(deck.createDeck()).size() == 52
     */
    @Test
    public void newDeckHasFiftyTwoCardsTest() {
        // Arrange

        // Act

        // Assert
        assertEquals("The deck did not have fifty two cards as expected on creation", 52, deck.size());
    }

    /**
     * Tests that the deck is well shuffled after the shuffle command is used
     *
     * Relevant Axioms:
     * II.	deck.createDeck()
     * deck.shuffle()
     * check the difference between the cards
     * adjacentCardsCount <= 26 (Ensure that cards are shuffled well and do not have cards in sequential order too frequently
     * or of just differing suits)
     */
    @Test
    public void wellShuffledDeckTest() {
        // Arrange
        int initialDeckSize = deck.size();

        Rank lastCardsRank = Rank.Ace;
        Suit lastCardsSuit = Suit.Diamonds;

        int similarAdjacentCardsAmount = 0;

        // Act
        deck.shuffle();

        for (int i = 1; i <= initialDeckSize; i++) {
            Card currentCard = deck.draw();

            if (Math.abs(lastCardsRank.ordinal() - currentCard.getRank().ordinal()) < 2) { // If the difference in rank is less than two
                similarAdjacentCardsAmount++; // Ranks are adjacent or the same
            } else if (lastCardsSuit == currentCard.getSuit()) {
                similarAdjacentCardsAmount++; // Suits are the same
            } else {
                // Cards are not similar enough, indication of randomization
            }

            lastCardsRank = currentCard.getRank();
            lastCardsSuit = currentCard.getSuit();
        }

        // Assert
        assertTrue("Deck was not shuffled well or at all", similarAdjacentCardsAmount <= 26);

    }

    /**
     * Tests that the deck is created with the right cards, in the right order
     *
     * Relevant Axioms:
     * III.	deck.createDeck()
     * card = deck.draw()
     * card.show()
     * card.toFullName() == “Ace of Diamonds”
     * Repeat to verify all 13 ranks appear for each suit
     * deck.size() == 0
     */
    @Test
    public void newDeckHasCorrectCardsInCorrectOrderTest() {
        Card[] cardsToCompare = GeneralTestHelper.getAllPlayingCardsOrderedBySuitThenRank();

        for (int i = cardsToCompare.length - 1; i >= 0; i--) {
            Card fromDeck = deck.draw();
            fromDeck.show();
            cardsToCompare[i].show();

            assertEquals(cardsToCompare[i].toFullName(), fromDeck.toFullName());
        }
    }

    /**
     * Tests that drawing a card decreases the size of the deck appropriately.
     *
     * Relevant Axioms:
     * IV.	deck.createDeck()
     * deck.draw()
     * deck.size() == 51
     * deck.draw()
     * deck.size() == 50
     */
    @Test
    public void drawingDecreasesSizeTest() {
        // Arrange
        int previousSize = deck.size();


        while (deck.size() > 0) {
            // Act
            deck.draw();

            // Assert
            assertEquals("Deck did not decrease in size after drawing a card", --previousSize, deck.size());
        }
    }

    /**
     * Tests that the deck refills in inverted order correctly, and that the cards are face down after going into the deck.
     *
     * Relevant Axioms:
     * V.	deck = [a deck with cards missing]
     * deck.insertCardsInInvertedOrder (cardList)
     * deck == [the deck with the original cards on the bottom in their order followed by the cards provided in the order
     * that they came with the last card being at the top of the deck with cards face down]
     */
    @Test
    public void validateCorrectInsertionOfCardsBeingRefilledTest() {
        // Arrange
        List<Card> listToRefillFrom = new ArrayList<>();
        List<Card> listToCheckForInvertedOrder = new ArrayList<>();

        int sizeOfDeck = deck.size();

        // Act
        for (int i = 1; i <= sizeOfDeck; i++) {
            listToRefillFrom.add(deck.draw()); // Iterate through and take the cards out of the deck
        }

        listToRefillFrom.forEach(card -> card.show()); // Show every card so we can test if it is hidden when refilled

        deck.insertCardsInInvertedOrder(listToRefillFrom); // Refill the deck

        if (deck.size() != 52) { // Ensure cards were not lost
            fail("The deck lost cards when it was refilling");
        }

        for (int i = 1; i <= sizeOfDeck; i++) {
            listToCheckForInvertedOrder.add(deck.draw()); // Iterate through and take the cards out of the deck, again
        }

        Collections.reverse(listToCheckForInvertedOrder); // Reverse the inverted list to check to see if the lists line up

        for (int i = 0; i < listToRefillFrom.size(); i++) {
            assertEquals("The card was not hidden when refilled", "UC", listToCheckForInvertedOrder.get(i).toString());
        }


    }

    /**
     * Tests that the deck display represents the number of cards left in the deck correctly
     *
     * Relevant Axioms:
     * VI.	Any deck the has been created
     * deck.toString() == [#] where # is the number of cards in the deck
     */
    @Test
    public void toStringTest() {
        for (int i = 52; i > 0; i--) {
            assertEquals("[" + i + "]", deck.toString());
            deck.draw();
        }
    }
}
