package modeltests;

import org.junit.Test;

/**
 * Tests the Player class to find defects in it early on.
 *
 * Axioms:
 * I.	player = player.create()
 * player.getSelectedSource() == null  (or something like None)
 * II.	player = player.create()
 * player.alterCurrentSelection({a specific selectable pile})
 * player.getSelectedSource() == {a specific selectable pile}
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-27
 */
public class PlayerTest {
    /**
     * Tests that when a player object is created, that nothing is selected as a source
     */
    @Test
    public void constructorTest() {

    }

    /**
     * Tests that when the current selection is altered, the selected source is changed.
     */
}
