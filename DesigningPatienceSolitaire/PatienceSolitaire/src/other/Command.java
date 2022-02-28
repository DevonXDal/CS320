package other;

/**
 * This other.Command class handles the holding of commands that are handled and passed along to the controller in an information holding
 * object. This also aids to set up the other.Command design pattern.
 *
 * Why: This other.Command class is utilized to keep the command line as stupid as possible instead of having it handle some
 * elements of the program meant to be handled by the controller.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-21
 */
public class Command {
    private String command;
    private String[] arguments;

    /**
     * Creates a new other.Command object with the command word and any extra arguments.
     *
     * @param command The command word that will decide the action to be taken
     * @param args The arguments, if useful, that will be used to carry out the command
     */
    public Command(String command, String[] args) {
        this.command = command;
        arguments = args;
    }

    /**
     * Returns the command word
     *
     * @return The command word as a string
     */
    public String getCommand() {
        return command;
    }

    /**
     * Returns the arguments that are for the command word
     *
     * @return The arguments that may be used with the command word to perform an action
     */
    public String[] getArguments() {
        return arguments;
    }
}
