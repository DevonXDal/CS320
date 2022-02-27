package models;

import models.piles.SelectablePile;

/**
 * This models.Player class represents the player, or more specifically the player's hand. When a card is selected by the player
 * through the command line to move using, the player class is used to keep track of it.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-22
 */
public class Player {
    private SelectablePile selectedSource;

    /**
     * Takes in the new pile and replaces the previous selection with the new pile.
     *
     * @param pile The new pile to select
     */
    public void alterCurrentSelection(SelectablePile pile) {

    }

    /**
     * Returns null if nothing is selected or the pile that is selected.
     *
     * @return null if nothing is selected or the pile
     */
    public SelectablePile getSelectedSource() {
       return null ;
    }
}
