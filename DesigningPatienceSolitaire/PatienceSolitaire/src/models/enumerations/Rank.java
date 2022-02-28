package models.enumerations;

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
        switch (this) {
            case Ace:
                return "A";
            case Two:
                return "2";
            case Three:
                return "3";
            case Four:
                return "4";
            case Five:
                return "5";
            case Six:
                return "6";
            case Seven:
                return "7";
            case Eight:
                return "8";
            case Nine:
                return "9";
            case Ten:
                return "10";
            case Jack:
                return "J";
            case Queen:
                return "Q";
            case King:
                return "K";
            default:
                throw new IllegalArgumentException();
        }
    }
}
