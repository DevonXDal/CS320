package models;

/**
 * DO NOT ALTER THE ORDER OF WHICH THESE APPEAR
 *
 * Represents the shape that appears on a playing card that helps to differentiate it from the others.
 *
 * @author Devon X. Dalrymple
 * @version 2022-01-17
 */
public enum Suit {
    Diamonds, Clubs, Hearts, Spades;

    /**
     * Returns the unicode character shape of the suit.
     *
     * @return The unicode character for the selected suit
     */
    public char asUnicodeCharacter() {
        return 'a';
    }
}
