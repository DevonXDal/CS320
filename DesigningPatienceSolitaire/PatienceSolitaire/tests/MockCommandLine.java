import controller.IController;
import other.Command;
import view.ICommandLine;

/**
 * Pretends to be the command line to a controller. It handles the public API that the controller would call in order to
 * perform its responsibilities.
 *
 * Why: This mock class is pretty much a necessity in order to ensure that the controller is performing the right actions
 * when expected during its testing. Without a mock command line, the controller will be extremely difficult to test correctly.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-22
 */
public class MockCommandLine implements ICommandLine {
    private IController controller;

    /**
     * Tests the setting up of the controller
     *
     * @param controller The controller to send the input from this command line
     */
    @Override
    public void setController(IController controller) {

    }

    @Override
    public void collectAndHandleInput() {

    }

    @Override
    public Command requestMultiCardMoveDestination() {
        return null;
    }

    @Override
    public void printGameUpdate(String updateInformation) {

    }
}
