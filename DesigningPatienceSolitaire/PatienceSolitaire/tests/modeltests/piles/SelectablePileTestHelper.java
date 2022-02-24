package modeltests.piles;

import models.Card;
import models.enumerations.Rank;
import models.enumerations.Suit;
import models.piles.SelectablePile;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * This itself is not a test class to test a certain concrete class. This is meant to provides a means to test the concrete classes
 * of the SelectablePile abstract class
 */
public class SelectablePileTestHelper {
    private SelectablePile pile;
    private Class<? extends SelectablePile> classBeingTested;

    /**
     * Non-default constructor of the unit test, used to test a specific implementation
     */
    public SelectablePileTestHelper(SelectablePile pile)
    {
        classBeingTested = pile.getClass();
    }

    /**
     * Sets up the concrete object for use in tests, using the declared constructor of the subclass,
     * this requires the default constructor to be implemented for the subclass
     *
     * https://stackoverflow.com/questions/6094575/creating-an-instance-using-the-class-name-and-calling-constructor
     */
    private void setUp()
    {
        try
        {
            pile = classBeingTested.getDeclaredConstructor().newInstance();
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
        addCardTest();
        addCardNoDuplicatesTest();
    }

    /*
     * Tests the addCard() implementation for concrete classes. Ensures that no verification is done and that the card is added correctly.
     */
    private void addCardTest() {
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

    /*
     * Tests the addCard() implementation for concrete classes. Ensures that no verification is done and that the card is added correctly.
     */
    private void addCardNoDuplicatesTest() {
        Rank randomRank = generateRandomRank();
        Suit randomSuit = generateRandomSuit();

        for (int i = 1; i < 4; i++) {
            pile.addCard(new Card(randomRank, randomSuit));
        }

        assertNotNull("The card was not removed or stored correctly for the duplicate test for the implementation: " + classBeingTested,
                pile.removeCard());
        assertNull("Duplicates were detected during the duplicate test for the implementation: " + classBeingTested,
                pile.removeCard());

    }

    // https://stackoverflow.com/questions/609860/convert-from-enum-ordinal-to-enum-type - For converting ordinal back to enum
    // Returns a randomly generated rank
    private Rank generateRandomRank() {
        Random rand = new Random();

        return Rank.values()[rand.nextInt(Rank.values().length)];
    }

    // https://stackoverflow.com/questions/609860/convert-from-enum-ordinal-to-enum-type - For converting ordinal back to enum
    // Returns a randomly generated suit
    private Suit generateRandomSuit() {
        Random rand = new Random();

        return Suit.values()[rand.nextInt(Suit.values().length)];
    }
}
