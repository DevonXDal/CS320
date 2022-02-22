/**
 * This Card class represents a playing card. Playing cards can be “face up” or “face down” with face up cards
 * revealing what rank and suit the cards have. Playing cards contain a rank and a suit.
 *
 * @author Devon X. Dalrymple
 * @version 2022-01-17
 */
public class Card {
    private Rank rank;
    private Suit suit;
    private boolean isVisible;

    /**
     * Creates a card with the given rank and suit combination. This card will default to not being visible (face down).
     *
     * @param rank The alphanumeric character found on a playing card represented as an enumeration.
     * @param suit The shape found on a playing card represented as an enumeration.
     */
    public Card(Rank rank, Suit suit) { }

    /**
     * Gets the card's rank.
     *
     * @return The alphanumeric character found on a playing card represented as an enumeration.
     */
    public Rank getRank() {
        return null;
    }

    /**
     * Gets the card's suit.
     *
     * @return The shape found on a playing card represented as an enumeration.
     */
    public Suit getSuit() {
        return null;
    }

    /**
     * Returns the name of the card in the format of "{Rank} of {Suit}" if the card is visible (face up). Otherwise,
     * it will return the name as "Unknown Card".
     *
     * @return The name of the card.
     */
    @Override
    public String toString() {
        return null;
    }
}
