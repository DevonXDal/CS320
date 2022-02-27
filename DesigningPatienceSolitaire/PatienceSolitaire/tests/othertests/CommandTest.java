package othertests;

import org.junit.Test;
import other.Command;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Tests the Command class so that it can be ensured that commands can be sent correctly
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-27
 */
public class CommandTest {
    /**
     * Tests that the arguments provided to the constructor can be returned as expected
     */
    @Test
    public void constructorTest() {
        String commandWord = getRandomString();
        String[] arguments = new String[]{getRandomString(), getRandomString(), getRandomString()};

        Command commandObject = new Command(commandWord, arguments);

        assertEquals(commandWord, commandObject.getCommand());
        assertArrayEquals(arguments, commandObject.getArguments());
    }

    // Generates a random string word/separated part (no spaces) of up to 25 characters
    private String getRandomString() {
        StringBuilder builder = new StringBuilder();

        Random rand = new Random();
        int stringSize = rand.nextInt(25) + 1;

        for (int i = 1; i <= stringSize; i++) {
            char nextCharacter =  (char) (rand.nextInt(43) + 48); // ASCII from 0-Z

            builder.append(nextCharacter);
        }

        return builder.toString();
    }
}
