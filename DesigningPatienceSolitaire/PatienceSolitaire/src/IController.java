/**
 * The IController class provides a contract that game controllers for the game Patience Solitaire are expected to implement.
 *
 * Why: This allows the project to better follow the dependency-inversion principle for larger moving pieces and to
 * allow mock controllers to be implemented so that testing the command line is feasible.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-21
 */
public interface IController {

    /**
     * Accepts commands that could not be identified at the command line level to handle. If the command can be carried out
     * as per game rules, it will be.
     *
     * Why: This provides a way that the API can communicate with the controller. Without it, the API would not being able
     * to send input to the controller due to the interface and testing the command line would be harder.
     *
     * @param command The command object that provides the command and its arguments to be handled
     * @return Anything that the controller wishes to print to the screen that is not the game status
     */
    String sendInput(Command command);
}
