package models.piles;

import models.Card;
import models.enumerations.Rank;

/**
 * This models.piles.Foundation class represents one of the four models.piles in Patience Solitaire that are built up from Ace to King by the
 * player. The win condition is also checked using foundation objects.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-28
 */
public class Foundation extends SelectablePile {

    /**
     * This foundation checks the incoming card to see if it would be following the rules for Patience Solitaire by allowing
     * the card to be added to the pile.
     *
     * Rules: 1. If no other cards are in the pile, the card must be an ace; 2. If there are other cards in the pile, then
     * the new card can be placed if it is one rank higher than the top card and is of the same suit
     *
     * @param cardToTry The card that should be checked to see if the rules would allow it to be moved here
     * @return True if the card is valid as per Patience Solitaire rules, false otherwise
     */
    @Override
    public boolean verifyMoveIsLegal(Card cardToTry) {
        if (cards.isEmpty()) {
            if (cardToTry.getRank() == Rank.Ace) {
                return true;
            } else {
                return false;
            }
        } else {
            Card topCard = viewCard();

            if (topCard.getRank().ordinal() + 1 == cardToTry.getRank().ordinal() && topCard.getSuit() == cardToTry.getSuit()) {
                return true;
            } else  {
                return false;
            }
        }
    }

    /**
     * Returns true if the foundation has thirteen cards in it. (King is the top card)
     *
     * Why: This method aids the controller in determining if the foundation is completed when checking the win condition.
     * By having the controller figure it out itself, it would be adding more responsibilities to the already most complex class.
     *
     * @return True if there are all thirteen cards of the suit in the pile, false otherwise
     */
    public boolean checkFoundationCompleted() {
        return (viewCard() != null && viewCard().getRank() == Rank.King && cards.size() == 13);
    }

    /**
     * Returns the representation of the foundation in this Patience Solitaire. This is "[??]", where the question marks
     * represent the abbreviated two or three characters used to represent the top card. If no card is in the foundation,
     * then it appears as "[  ]"
     */
    @Override
    public String toString() {
        if (viewCard() == null) {
            return "[  ]";
        } else {
            return "[" + viewCard() + "]";
        }
    }
}
