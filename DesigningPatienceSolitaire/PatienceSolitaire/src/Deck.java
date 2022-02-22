import java.util.List;
import java.util.Stack;

/**
 * This Deck class represents a physical deck of 52 playing, with each containing a unique combination of rank and suit.
 * Cards can be drawn from the deck or put back in. Cards are face down when in the deck. The deck also handles the
 * creation of the set of cards that it uses.
 *
 * @author Devon X. Dalrymple
 * @version 2022-01-17
 */
public class Deck {
    private Stack<Card> cards;

    /**
     * Creates the stack of cards and fills it with 52 cards built from the 13 rank and 4 suit possibilities.
     * The stack of cards will then be shuffled.
     */
    public Deck() { }

    /**
     * Returns the number of cards that are currently in the deck.
     *
     * @return The number of cards in this deck
     */
    public int size() {
        return -1;
    }

    /**
     * Remove and return a card from the top of the deck.
     *
     * @return The card at the top of the deck.
     */
    public Card draw() {
        return null;
    }

    /**
     * Adds cards one at a time from the beginning of the list until the end to the top of the deck.
     * This is necessary to refill the cards from the discard pile into the deck in Patience Solitaire.
     *
     * @param newCards The cards to insert to the top of the deck one at a time in order.
     */
    public void insertCardsInMaintainedOrder(List newCards) { }

    /**
     * Randomize the positions of the cards in the deck
     */
    public void shuffle() { }

}
