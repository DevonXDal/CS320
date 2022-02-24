package controller;

import models.Player;
import models.Table;
import other.Command;
import view.ICommandLine;

/**
 * This controller.GameController represents the controller in an MVC pattern. It handles the internal game loop and logic.
 * It controls the actions taken by the other classes and makes what the other classes do actually useful.
 *
 * @author Devon X. Dalrymple
 * @version 2022-02-24
 */
public class GameController implements IController {
    private Table table;
    private ICommandLine userInterface;
    private Player player;
    private boolean isGameLoopContinuing; // If this becomes false, the game loop will end when it next starts the loop.

    /**
     * Creates a new controller.GameController object with the command line, player, and table being set up by the contoller itself.
     */
    public GameController() {

    }

    /**
     * Creates a new controller.GameController object with the command line, player, and table being specified by something else.
     *
     * Why: This overloaded constructor is mostly just a method of getting enough access to the moving parts in order to
     * run tests against it.
     *
     * @param commandLine The command line to use
     * @param table The table
     * @param player The player/hand object
     */
    public GameController(ICommandLine commandLine, Table table, Player player) {

    }

    /**
     * Starts the initialization process to get the game loop up and running. The game loop will run after the initial set
     * up has been completed.
     */
    public void initializeGame() {

    }

    /**
     * Accepts commands that could not be identified at the command line level to handle. If the command can be carried out
     * as per game rules, it will be.
     *
     * Why: This provides a way that the command line can communicate with the controller. Without it, the command line would not being able
     * to send input to the controller due to the interface and testing the command line would be harder.
     *
     * @param command The command object that provides the command and its arguments to be handled
     * @return Anything that the controller wishes to print to the screen that is not the game status
     */
    @Override
    public String sendInput(Command command) {
        return null;
    }

    // This runs the game loop and queries for the command line for input as needed.
    private void runGameLoop() {

    }

    // This gets the current card selected and the view of the table to send back to the command line.
    private String fetchGameStatusUpdate() {
        return null;
    }

    // This sends a request formatted as needed to get the desired pile back from the table and attempt to select it.
    private String selectPile(String[] args) {
        return null;
    }

    // This attempts to draw a card from the deck and place it face up into the waste pile. If the deck is empty, it
    // attempts to refill the deck with waste pile cards.
    private String doDrawOrRefill() {
        return null;
    }

    // This tries to move the selected card from the selected source to a destination deciphered from the arguments.
    private String moveSelectionToDestination(String[] args) {
        return  null;
    }

    // This deselects the currently selected card and requests for a game status update.
    private String deselectSourceSelection() {
        return null;
    }

    // This resets the game so that a new board of cards is played out and the game starts anew.
    private String resetGame() {
        return null;
    }

    // This ends the game and stops the game loop so that the program can be safely closed.
    private void endGameAndStopLoop() {

    }
}
