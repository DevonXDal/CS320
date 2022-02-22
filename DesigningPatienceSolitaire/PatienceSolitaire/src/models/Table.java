package models;

import models.Deck;
import models.piles.SelectablePile;

import java.util.Map;

/**
 * This models.Table class represents the physical table space used to hold the components for the game Patience Solitaire.
 * It serves mostly as a central location to access these components. It is also responsible for generating a view of
 * itself that can be displayed to the user.
 *
 * Why: This class helps to reduce what the controller ends up being responsible for in addition to having it handle its
 * own display generation.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-22
 */
public class Table {
    private Deck deck;
    private Map<String, SelectablePile> selectablePiles;


    /**
     * Creates a new deck with fifty-two unique playing cards and shuffles it. This also sets up the mapping of string
     * arguments to their related models.piles that can be selectable.
     */
    public Table() {

    }


    /**
     * Returns the deck that is being used
     *
     * @return The table's deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Returns either null or selectable pile that was found in the map.
     *
     * @param pileInfo The information that might identify the pile from its key mapping
     * @return The pile if it was found or null
     */
    public SelectablePile getSelectablePile(String pileInfo) {
        return null;
    }

    /**
     * Generates a command line representation of a table set up with the current state of the game for Patience Solitaire.
     *
     * @return The current state of the table represented by text
     */
    public String generateTableRepresentation() {
        return null;
    }
}
