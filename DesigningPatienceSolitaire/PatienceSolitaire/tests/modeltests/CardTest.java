package modeltests;


import models.Card;
import models.enumerations.Rank;
import models.enumerations.Suit;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the Card class to detect defects before they become an issue.
 *
 * Axioms:
 * I.	(card.createCard(rank, suit)).getRank() == rank
 * II.	(card.createCard (rank, suit)).getSuit() == suit
 * III.	(card.createCard (rank, suit)).toFullName() == “Unknown Card”
 * IV.	card.createCard (rank, suit)
 * card.show()
 * card.toString() == “{rank.asAbbreviation()}{suit.asUnicodeCharacter}”
 * V.	card.createCard(rank, suit)
 * card.show()
 * card.hide()
 * card.toString() == “UC”
 * VI.	card.createCard(rank, suit)
 * card.show()
 * card.toFullName () == “{rank} of {suit}”
 * VII.	card.createCard(rank, suit)
 * card.show()
 * card.hide()
 * card.toFullName () == “Unknown Card”
 * VIII.	(card.createCard (rank, suit)).toString() == “UC”
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-24
 */
public class CardTest {
    /**
     * Tests that when a card is created, it has the correct rank and suit
     *
     * Relevant Axioms:
     * I.	(card.createCard(rank, suit)).getRank() == rank
     * II.	(card.createCard (rank, suit)).getSuit() == suit
     */
    @Test
    public void constructorCorrectRankAndSuitTest() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                //Arrange
                Card card = new Card(rank, suit);

                //Act

                //Assert
                assertEquals("The rank for this new card was not: " + rank, rank, card.getRank());
                assertEquals("The suit for this new card was not: " + suit, suit, card.getSuit());
            }
        }
    }

    /**
     * Tests that a card only shows its rank and suit in text form when visible/face-up for its full name.
     * This test also shows that hide() and show() function as intended when combined with similar tests.
     *
     * Relevant Axioms:
     * III.	(card.createCard (rank, suit)).toFullName() == “Unknown Card”
     * VI.	card.createCard(rank, suit)
     * card.show()
     * card.toFullName () == “{rank} of {suit}”
     * VII.	card.createCard(rank, suit)
     * card.show()
     * card.hide()
     * card.toFullName () == “Unknown Card”
     */
    @Test
    public void fullNameIsShownCorrectlyForVisibilityTest() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                //Arrange
                Card card = new Card(rank, suit);

                //Act

                //Assert
                assertEquals("With the rank: " + rank + " and the suit: " + suit + ", the full name was visible when it should of not been (creation)",
                        "Unknown Card", card.toFullName());

                // ---- Arrange Above ----

                //Act
                card.show();

                //Assert
                assertEquals("With the rank: " + rank + " and the suit: " + suit + ", the full name was not visible when it should of been (shown)",
                        rank + " of " + suit, card.toFullName());

                // ---- Arrange Above ----

                //Act
                card.hide();

                //Assert
                assertEquals("With the rank: " + rank + " and the suit: " + suit + ", the full name was visible when it should of not been (hidden)",
                        "Unknown Card", card.toFullName());
            }
        }
    }

    /**
     * Tests that a card only shows its rank and suit in text form when visible/face-up for its abbreviated name.
     * This test also shows that hide() and show() function as intended when combined with similar tests.
     *
     * Relevant Axioms:
     * IV.	card.createCard (rank, suit)
     * card.show()
     * card.toString() == “{rank.asAbbreviation()}{suit.asUnicodeCharacter}”
     * V.	card.createCard(rank, suit)
     * card.show()
     * card.hide()
     * card.toString() == “UC”
     * VIII.	(card.createCard (rank, suit)).toString() == “UC”
     *
     */
    @Test
    public void abbreviatedNameIsShownCorrectlyForVisibilityTest() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                //Arrange
                Card card = new Card(rank, suit);

                //Act

                //Assert
                assertEquals("With the rank: " + rank + " and the suit: " + suit + ", the abbreviated name was visible when it should of not been (creation)",
                        "UC", card.toString());

                // ---- Arrange Above ----

                //Act
                card.show();

                //Assert
                assertEquals("With the rank: " + rank + " and the suit: " + suit + ", the abbreviated name was not visible when it should of been (shown)",
                        rank.asAbbreviation() + suit.asUnicodeCharacter(), card.toString());

                // ---- Arrange Above ----

                //Act
                card.hide();

                //Assert
                assertEquals("With the rank: " + rank + " and the suit: " + suit + ", the abbreviated name was visible when it should of not been (hidden)",
                        "UC", card.toString());
            }
        }
    }
}
