package models;

import models.Deck;
import models.piles.CardColumn;
import models.piles.Foundation;
import models.piles.SelectablePile;
import models.piles.WastePile;

import java.util.LinkedHashMap;
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
 * @version 2022-02-28
 */
public class Table {
    private Deck deck;
    private Map<String, SelectablePile> selectablePiles;


    /**
     * Creates a new deck with fifty-two unique playing cards and shuffles it. This also sets up the mapping of string
     * arguments to their related models.piles that can be selectable.
     */
    public Table() {
        selectablePiles = new LinkedHashMap<>(); // This was added before testing was completed so that it would not be forgotten
        deck = new Deck();

        selectablePiles.put("waste", new WastePile()); //The order of these matter because this is a LinkedHashMap

        for (int i = 1; i <= 4; i++) {
            selectablePiles.put("foundation " + i, new Foundation());
        }

        for (int i = 1; i <= 7; i++) {
            selectablePiles.put("column " + i, new CardColumn());
        }
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
        return selectablePiles.getOrDefault(pileInfo, null);
    }

    /**
     * Generates a command line representation of a table set up with the current state of the game for Patience Solitaire.
     *
     * @return The current state of the table represented by text
     */
    @Override
    public String toString() {
        SelectablePile[] pilesToDisplay = selectablePiles.values().toArray(new SelectablePile[0]);
        StringBuilder builder = new StringBuilder();

        final int SPACING_SEPARATION = 7; // Spacing for the displayed table = SPACING_SEPARATION - length of the string for each pile

        // Deck
        String deckRepresentation = deck.toString();
        builder.append(deckRepresentation);

        for (int i = 1; i <= SPACING_SEPARATION - deckRepresentation.length(); i++) {
            builder.append(" ");
        }

        // Waste pile
        String wastePileRepresentation = pilesToDisplay[0].toString();
        builder.append(wastePileRepresentation);

        // 3 more to separate the foundations from the deck and waste pile
        for (int i = 1; i <= (SPACING_SEPARATION - wastePileRepresentation.length() + 3); i++) {
            builder.append(" ");
        }

        // Foundations
        for (int i = 1; i < 5; i++) {
            String foundationRepresentation = pilesToDisplay[i].toString();
            builder.append(foundationRepresentation);

            for (int j = 1; j <= SPACING_SEPARATION - foundationRepresentation.length(); j++) {
                builder.append(" ");
            }
        }

        builder.append("\n"); // Move to the next line to start the columns

        //Columns
        CardColumn[] columns = new CardColumn[7];

        for (int i = 5; i < 12; i++) { // Get the columns out and cast them
            columns[i - 5] = (CardColumn) pilesToDisplay[i];
        }

        int sizeOfLargestColumn = 0;
        for (CardColumn column : columns) {
            int currentColumnSize = column.getCards().size();

            if (sizeOfLargestColumn < currentColumnSize) {
                sizeOfLargestColumn = currentColumnSize;
            }
        }

        for (int rowNumber = 1; rowNumber <= sizeOfLargestColumn; rowNumber++) { // For each row that needs added
            for (CardColumn column : columns) { // For every column
                if (column.getCards().size() < rowNumber) {
                    builder.append("[  ]   ");
                } else {
                    Card possibleCard = column.viewCardAtPos(column.getCards().size() - rowNumber + 1); // Flips the methods card usage to get from the top
                    String representationOfColumnRow = null;

                    if (possibleCard != null) {
                        representationOfColumnRow = "[" + possibleCard + "]";
                    } else {
                        representationOfColumnRow = "[UC]";
                    }

                    builder.append(representationOfColumnRow);

                    for (int i = 1; i <= SPACING_SEPARATION - representationOfColumnRow.length(); i++) {
                        builder.append(" ");
                    }
                }
            }

            builder.append("\n"); // Start next row
        }

        return builder.toString();
    }
}
