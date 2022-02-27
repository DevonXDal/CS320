package modeltests.piles;

import models.Card;
import models.enumerations.Rank;
import models.enumerations.Suit;
import models.piles.SelectablePile;
import other.GeneralTestHelper;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * This itself is not a test class to test a certain concrete class. This is meant to provide a means to test the concrete classes
 * of the SelectablePile abstract class. It does not test move legality as that is implementation specific.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-26
 */
public class SelectablePileTestHelper {
    private SelectablePile pile;
    private Class<? extends SelectablePile> classBeingTested;

    Rank randomRank;
    Suit randomSuit;

    /**
     * Non-default constructor of the unit test, used to test a specific implementation
     */
    public SelectablePileTestHelper(SelectablePile pile)
    {
        classBeingTested = pile.getClass();
    }

    /*
     * Sets up the concrete object for use in tests, using the declared constructor of the subclass,
     * this requires the default constructor to be implemented for the subclass
     *
     * Other than the creation of the object, nothing else happens at this stage
     *
     * https://stackoverflow.com/questions/6094575/creating-an-instance-using-the-class-name-and-calling-constructor
     */
    private void setUp()
    {
        try
        {
            pile = classBeingTested.getDeclaredConstructor().newInstance();

            randomRank = GeneralTestHelper.generateRandomRank();
            randomSuit = GeneralTestHelper.generateRandomSuit();
        }
        catch (Exception exception)
        {
            System.out.println("The concrete class could not be recreated" + exception.getMessage());
        }
    }

    /**
     * Runs a series of tests for the concrete class to ensure that it is implemented as per the specifications of the
     * abstract class. Does not test the verifyMoveIsLegal() method as its implementation is very dependent on the concrete
     * implementation.
     */
    public void runAbstractClassImplementationTests() {
        constructorTest();
        addCardTest();
        addCardNoDuplicatesTest();
        viewCardTest();
        removeCardTest();
    }

    /**
     * Tests that the new selectable pile starts off as empty
     */
    public void constructorTest() {
        setUp();

        assertNull("The empty pile was not empty with the implementation: " + classBeingTested, pile.viewCard());
    }

    /**
     * Tests the addCard() implementation for concrete classes. Ensures that no verification is done and that the card is added correctly.
     */
    public void addCardTest() {
        setUp();

        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                try {
                    pile.addCard(new Card(rank, suit));
                } catch (Exception e) { // No verification or exceptions are supposed to be thrown.
                    fail("No exception should ever be thrown when adding cards");
                }

                assertEquals("The implementation: " + classBeingTested + " did not store the correct card; Rank Issue",
                        rank, pile.viewCard().getRank());
                assertEquals("The implementation: " + classBeingTested + " did not store the correct card; Suit Issue",
                        suit, pile.viewCard().getSuit());
            }
        }
    }

    /**
     * Tests the addCard() implementation for concrete classes. Ensures that no verification is done and that the card is added correctly.
     */
    public void addCardNoDuplicatesTest() {
        setUp();

        for (int i = 1; i < 4; i++) {
            pile.addCard(new Card(randomRank, randomSuit));
        }

        assertNotNull("The card was not removed or stored correctly for the duplicate test for the implementation: " + classBeingTested,
                pile.removeCard());
        assertNull("Duplicates were detected during the duplicate test for the implementation: " + classBeingTested,
                pile.removeCard());

    }

    /**
     * Tests that the removeCard() implementation both removes and returns a card from its list. It also checks that null is returned
     * for when the implementation is empty.
     */
    public void removeCardTest() {
        setUp();

        pile.addCard(new Card(randomRank, randomSuit));

        Card insertedCard = pile.removeCard();
        Card shouldBeNull = pile.removeCard();

        assertEquals("Rank issue; The wrong card was returned when removing a card for implementation: " + classBeingTested, randomRank, insertedCard.getRank());
        assertEquals("Suit issue; The wrong card was returned when removing a card for implementation: " + classBeingTested, randomSuit, insertedCard.getSuit());
        assertNull(shouldBeNull);

    }

    /**
     * Tests that the viewCard() implementation returns but does not remove the card from its list. It also checks that null
     * is returned when the pile is empty.
     */
    public void viewCardTest() {
        setUp();

        pile.addCard(new Card(randomRank, randomSuit));

        Card currentlyObservedCard = pile.viewCard();
        assertEquals("Rank issue; The wrong card was returned when viewing a card for implementation: " + classBeingTested, randomRank, currentlyObservedCard.getRank());
        assertEquals("Suit issue; The wrong card was returned when viewing a card for implementation: " + classBeingTested, randomSuit, currentlyObservedCard.getSuit());

        currentlyObservedCard = pile.viewCard();
        assertEquals("Rank issue; A different card was returned when viewing a card for implementation: " + classBeingTested, randomRank, currentlyObservedCard.getRank());
        assertEquals("Suit issue; A different card was returned when viewing a card for implementation: " + classBeingTested, randomSuit, currentlyObservedCard.getSuit());

    }


}
