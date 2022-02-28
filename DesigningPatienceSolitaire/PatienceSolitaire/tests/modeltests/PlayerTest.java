package modeltests;

import models.Player;
import models.piles.SelectablePile;
import models.piles.WastePile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
    private Player player;

    @Before
    public void setUp() {
        player = new Player();
    }

    /**
     * Tests that when a player object is created, that nothing is selected as a source
     *
     * Relevant Axioms:
     * I.	player = player.create()
     * player.getSelectedSource() == null  (or something like None)
     */
    @Test
    public void constructorTest() {
        assertNull(player.getSelectedSource());
    }

    /**
     * Tests that when the current selection is altered, the selected source is changed.
     *
     * Relevant Axioms:
     * II.	player = player.create()
     * player.alterCurrentSelection({a specific selectable pile})
     * player.getSelectedSource() == {a specific selectable pile}
     */
    @Test
    public void alterCurrentSelectionTest() {
        SelectablePile pile = new WastePile();

        player.alterCurrentSelection(pile);

        assertEquals(pile, player.getSelectedSource());
    }
}
