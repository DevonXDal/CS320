package models.piles;

import models.Card;

import java.util.List;

/**
 * This models.piles.WastePile class represents the waste/discard pile of cards used in Patience Solitaire that receives cards from the
 * deck and is refilled into the deck.
 *
 * Why: This class is different from the other piles because it can have all of its cards removed and returned from it at
 * once.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-24
 */
public class WastePile {

    /**
     * Removes and returns all the cards from the waste pile in the order that they were in, in the waste pile (bottom to top).
     *
     * Why: This method exists in order to refill the stock/deck with cards when empty like the waste would in the actual game.
     *
     * @return The cards from the waste pile from bottom to top
     */
    public List<Card> removeAll() {
        return null;
    }

    /**
     * Returns the representation of the waste pile in this Patience Solitaire. This is "[??]", where the question marks
     * represent the abbreviated two or three characters used to represent the top card. If no card is in the waste pile,
     * then it appears as "[  ]"
     */
    public String toString() {
        return null;
    }
}
