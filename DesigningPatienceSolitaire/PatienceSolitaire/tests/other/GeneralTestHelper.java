package other;

import models.Card;
import models.enumerations.Rank;
import models.enumerations.Suit;

import java.util.Random;

/**
 * This GeneralTestHelper class provides methods that can be used in assisting other testing classes that make use of the same
 * methods.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-26
 */
public class GeneralTestHelper {
    /**
     * https://stackoverflow.com/questions/609860/convert-from-enum-ordinal-to-enum-type - For converting ordinal back to enum
     *
     * Returns a randomly generated rank
     */
    public static Rank generateRandomRank() {
        Random rand = new Random();

        return Rank.values()[rand.nextInt(Rank.values().length)];
    }

    /**
     * https://stackoverflow.com/questions/609860/convert-from-enum-ordinal-to-enum-type - For converting ordinal back to enum
     *
     * Returns a randomly generated suit
     */
    public static Suit generateRandomSuit() {
        Random rand = new Random();

        return Suit.values()[rand.nextInt(Suit.values().length)];
    }

    /**
     * Returns a randomly generated card.
     */
    public static Card generateRandomCard() {
        return new Card(generateRandomRank(), generateRandomSuit());
    }

    /**
     * Returns an array of unique random cards. Any amount less than 0 or greater than 52 becomes 52 cards.
     *
     * @param num The number of unique cards (0-52)
     * @return An array of unique cards
     */
    public static Card[] generateRandomUniqueCardArrayOfSpecifiedSize(int num) {
        if (num > 52 || num < 0) {
            num = 52;
        }

        Card[] uniqueCards = new Card[num];
        int currentIndex = 0;

        while (currentIndex < uniqueCards.length) { // Until each index has been filled
            Card newCard = generateRandomCard(); // Get a random card
            boolean isNewCard = true; // Set to false if it is later detected to be a duplicate

            for (int i = 0; i < currentIndex; i++) { // Check the current cards to see if any are the same
                if (uniqueCards[i].getRank() == newCard.getRank() && uniqueCards[i].getSuit() == newCard.getSuit()) {
                    isNewCard = false;
                    break;
                }
            }

            if (isNewCard) { // Add the card if it is a new/unique card
                uniqueCards[currentIndex] = newCard;
            }
        }

        return uniqueCards;
    }

    /**
     * Returns an array of each of the 52 unique playing cards.
     *
     * @return An array with 52 unique cards
     */
    public static Card[] getAllPlayingCardsOrderedBySuitThenRank() {
        Card[] cards = new Card[52];
        int index = 0;

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards[index] = new Card(rank, suit);
            }
        }

        return cards;
    }
}
