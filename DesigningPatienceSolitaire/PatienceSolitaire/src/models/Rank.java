package models;

/**
 * DO NOT ALTER THE ORDER OF WHICH THESE APPEAR
 *
 * Represents the alphanumeric character found on a playing card that helps to differentiate the playing card from
 * others.
 *
 * @author Devon X. Dalrymple
 * @version 2022-01-17
 */
public enum Rank {
    Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King;

    /**
     * Returns the one or two letter abbreviation representing the selected rank.
     *
     * @return The abbreviation of the selected rank
     */
    public String asAbbreviation() {
        return null;
    }
}
