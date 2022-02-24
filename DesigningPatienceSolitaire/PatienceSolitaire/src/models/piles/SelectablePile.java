package models.piles;

import models.Card;

import java.util.ArrayList;

/**
 * This models.piles.SelectablePile class is an abstract version of the selectable models.piles that exist in Patience Solitaire.
 * Common functionality is set up in this location to reduce code duplication in the implementation.
 *
 * Why: The main reason for the models.piles.SelectablePile abstract class is not necessarily to reduce code duplication. Its main purpose
 * is to simplify the public interface for various classes and to allow similar objects for the game to be stored in others as one variable.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-21
 */
public abstract class SelectablePile {
    protected ArrayList<Card> cards;

    /**
     * Set up the empty list of cards for use.
     */
    public SelectablePile() {

    }

    /**
     * Adds a card to the end of the list. Rules for the card is not checked and must be done separately.
     * Duplicates are checked when the card is added and the new card will never make it to the list if it is a duplicate.
     *
     * @param cardToAdd The card to be appended to the end of the list
     */
    public void addCard(Card cardToAdd) {

    }

    /**
     * Removes and returns the card at the end of the list. Returns null if no cards are in the list.
     *
     * @return The most recently added card
     */
    public Card removeCard() {
        return null;
    }

    /**
     * Returns but does not remove the card at the end of the list. Returns null if no cards are in the list.
     *
     * Why: This method is use to view which card the user has selected and to aid in testing whether a card can be legally moved
     * to a destination.
     *
     * @return The most recently added card
     */
    public Card viewCard() {
        return null;
    }

    /**
     * Uses the card taken in to perform checks to see if that card could legally be moved to this collection.
     * By default, this selectable pile is never a legal destination for a card.
     *
     * Why: This method is used for the foundation and card column to determine which cards can be moved to them.
     * This is added because it can default to false and allow all three objects to be associated with each other.
     *
     * @param cardToTry The card that should be checked to see if the rules would allow it to be moved here
     * @return True if the card could be moved here as per the rules of Patience Solitaire, otherwise False
     */
    public boolean verifyMoveIsLegal(Card cardToTry) {
        return false;
    }
}