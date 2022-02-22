import java.util.Collection;
import java.util.List;

/**
 * This CardColumn class represents one of the seven columns of cards in the middle of the table.
 *
 * Why: The CardColumn class is its own entity because it can have multiple cards moved from one card column to another
 * in the same move. It also has unique destination rules, bottom card face up rules, etc.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-21
 */
public class CardColumn extends SelectablePile {

    /**
     * Removes and returns the bottom most card from the column. It also ensures that the bottom card of the column after
     * removal is face up. This way as the user plays, more and more cards in the columns are revealed.
     *
     * @return The card at the bottom of the column
     */
    @Override
    public Card removeCard() {
        return super.removeCard();
    }

    /**
     * This card column checks the incoming card to see if it would be following the rules for Patience Solitaire by allowing
     * the card to be added to the pile/column.
     *
     * Rules: 1. If no other cards are in the pile, the card must be a King; 2. If there are other cards in the pile, then
     * the new card can be placed if it is one rank lower than the bottom card and is of the opposite suit color
     *
     * @param cardToTry The card that should be checked to see if the rules would allow it to be moved here
     * @return True if the card is valid as per Patience Solitaire rules, false otherwise
     */
    @Override
    public boolean verifyMoveIsLegal(Card cardToTry) {
        return super.verifyMoveIsLegal(cardToTry);
    }

    /**
     * Accepts cards and places them in the order provided into the card collection.
     *
     * Why: This command allows one simple command to be used to add cards more easily into the card column from the controller
     * or another source.
     *
     * @param cards The collection of cards to insert in order into the column
     */
    public void addMultipleCards(List<Card> cards) {

    }

    /**
     * Removes and returns the bottom most cards and returns them in the order of top to bottom.
     *
     * Why: This is done to perform the move action of taking multiple cards from one card column and placing them into
     * another. This is why it structures the order in the way it appears (top most card is the first in the collection).
     *
     * @return The cards that were removed in an order from top to bottom or null if those cards are not there
     */
    public List<Card> removeMultipleCards(int amount) {
        return null;
    }

    /**
     * Returns the card at a specified position. The position of the card is the number of cards it is from the bottom
     * (bottom card has a position of 1). Returns null if no card is there. The card returns null if the card is face down.
     *
     * Why: This is done in order to check the legal move of moving a collection of cards from one card column to another.
     * If the top card can legally be moved than the rest can by the rules, which simplifies this process.
     *
     * @param positionFromBottom The position to grab the card from the bottom at
     * @return The card at that position or null if no card is there or the card is face down
     */
    public Card viewCardAtPos(int positionFromBottom) {
        return null;
    }

    /**
     * Return the cards in the column without removing them.
     *
     * Why: This method exists to aid the table in creating a representation of itself to display to the user.
     *
     * @return The cards currently in the column
     */
    public List<Card> getCards() {
        return null;
    }

    // Attempts to turn the bottom card face up whenever a card is removed after the card has been removed.
    private void verifyBottomIsFaceUp() {

    }
}
