package modeltests.piles;

import models.piles.WastePile;
import org.junit.Test;

/**
 * Tests the implementation of the waste pile to find defects early on.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-24
 */
public class WastePileTest {
    /**
     * Tests the WastePile implementation against the rules put forth by the abstract class' methods.
     */
    @Test
    public void runInterfaceTests() {
        SelectablePileTestHelper helper = new SelectablePileTestHelper(new WastePile());
    }
}
