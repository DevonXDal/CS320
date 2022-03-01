package controller;

import models.Card;
import models.Deck;
import models.Player;
import models.Table;
import models.piles.CardColumn;
import models.piles.Foundation;
import models.piles.SelectablePile;
import models.piles.WastePile;
import other.Command;
import view.CommandLine;
import view.ICommandLine;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This controller.GameController represents the controller in an MVC pattern. It handles the internal game loop and logic.
 * It controls the actions taken by the other classes and makes what the other classes do actually useful.
 *
 * @author Devon X. Dalrymple
 * @version 2022-03-01
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
        userInterface = new CommandLine();
        table = new Table();
        player = new Player();
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
        userInterface = commandLine;
        this.table = table;
        this.player = player;
    }

    /**
     * Starts the initialization process to get the game loop up and running. The game loop will run after the initial set
     * up has been completed.
     */
    public void initializeGame() {
        CardColumn[] columns = new CardColumn[7];
        Deck deck = table.getDeck();
        deck.shuffle();

        userInterface.setController(this);

        for (int i = 1; i <= 7; i++) { // For each foundation, if the foundation is null, an exception will fail the test
            columns[i - 1] = (CardColumn) table.getSelectablePile("column " + i);
        }

        for (int currentColumnIndex = 0, indexOfCurrentStartingColumn = 0; indexOfCurrentStartingColumn != columns.length; currentColumnIndex++) {
            columns[currentColumnIndex].addCard(deck.draw());

            if (currentColumnIndex + 1 == columns.length) {
                indexOfCurrentStartingColumn++;
                currentColumnIndex = indexOfCurrentStartingColumn - 1;
            }
        }

        for (CardColumn column : columns) {
            column.viewCard().show();
        }

        isGameLoopContinuing = true;

        userInterface.printGameUpdate(fetchGameStatusUpdate());
        runGameLoop();
    }

    /**
     * Starts the initialization process to get the game loop up and running. The game loop will run after the initial set
     * up has been completed. When set to true, this will not set up the table or player.
     *
     * @param flag When set to true, this will not set up the table or player and will assume that it has already been done
     */
    public void initializeGame(boolean flag) {
        if (flag) {
            isGameLoopContinuing = true;

            userInterface.printGameUpdate(fetchGameStatusUpdate());
            runGameLoop();
        } else {
            initializeGame();
        }
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
        switch (command.getCommand()) {
            case "select":
                return selectPile(command.getArguments());
            case "move":
                return moveSelectionToDestination(command.getArguments());
            case "draw":
                return doDrawOrRefill();
            case "deselect":
                return deselectSourceSelection();
            case "restart":
                return resetGame();
            case "exit":
                endGameAndStopLoop();
                return null;
            default:
                return "Unrecognized command";
        }
    }

    // This runs the game loop and queries for the command line for input as needed.
    private void runGameLoop() {
        Foundation[] foundations = new Foundation[4];

        for (int i = 1; i <= 4; i++) { // For each foundation
            foundations[i - 1] = (Foundation) table.getSelectablePile("foundation " + i);
        }

        while (isGameLoopContinuing) {
            userInterface.collectAndHandleInput();

            // Check if the game has been won
            int finishedFoundations = 0;
            for (Foundation foundation : foundations) {
                if (foundation.checkFoundationCompleted()) {
                    finishedFoundations++;
                }
            }

            if (finishedFoundations == 4) {
                userInterface.printWinMessage();

                try {
                    Thread.sleep(5000); // Gives the user time to read the goodbye message.
                } catch (InterruptedException ignored) {

                }

                initializeGame();
            }
        }

        try {
            Thread.sleep(5000); // Gives the user time to read the goodbye message.
        } catch (InterruptedException ignored) {

        }
        System.exit(0);
    }

    // This gets the current card selected and the view of the table to send back to the command line.
    private String fetchGameStatusUpdate() {
        StringBuilder builder = new StringBuilder();
        SelectablePile source = player.getSelectedSource();

        builder.append(table);
        builder.append("\n");
        builder.append("Currently Selected Card(s): ");

        if (source == null) {
            builder.append("N/A\n");
        } else {
            builder.append(source.viewCard().toFullName() + "\n");
        }

        return builder.toString();
    }

    // This sends a request formatted as needed to get the desired pile back from the table and attempt to select it.
    private String selectPile(String[] args) {
        if (args.length == 5) {
            try {
                int amount = Integer.parseInt(args[0]);

                SelectablePile sourcePile = table.getSelectablePile(args[3] +" "+ args[4]);

                if (sourcePile == null) {
                    throw new RuntimeException();
                }

                CardColumn sourceColumn = (CardColumn) sourcePile;

                if (sourceColumn.viewCardAtPos(amount) == null) {
                    return "Could not select the top card at that position from the bottom, either that position does not exist or the card is face down";
                } else {
                    player.alterCurrentSelection(sourcePile);
                }

                Command moveCommand;
                while (true) {
                    userInterface.printGameUpdate("What card column do you wish to move the group of cards to? Use 'column #' with # being the column's identifying position or 'deselect'");
                    moveCommand = userInterface.requestMultiCardMoveDestination();

                    switch (moveCommand.getCommand()) {
                        case "column":
                            int destinationColumnPos = Integer.parseInt(moveCommand.getArguments()[0]);

                            CardColumn destinationColumn = (CardColumn) table.getSelectablePile("column " + destinationColumnPos);

                            Card toVerify = sourceColumn.viewCardAtPos(amount);

                            if (destinationColumn.verifyMoveIsLegal(toVerify)) {
                                List<Card> movingCards = sourceColumn.removeMultipleCards(amount);
                                Collections.reverse(movingCards); // Turn the order of the collection, so it is inserted correctly to the destination
                                destinationColumn.addMultipleCards(movingCards);
                                player.alterCurrentSelection(null);
                                return fetchGameStatusUpdate();
                            } else {
                                player.alterCurrentSelection(null);
                                return "The top card of the group of cards does not meet the game rule requirements to be moved to that card column.";
                            }
                        case "deselect":
                            player.alterCurrentSelection(null);
                            return fetchGameStatusUpdate();
                        default:
                            return "Unrecognized command";
                    }
                }
            } catch (Exception ignored) {
                return "One or more bad arguments were provided";
            }

        } else if (args.length >= 1 && args.length <= 2) { // General selection
            String pileKey = args[0];

            if (args.length == 2) {
                pileKey = args[0] + " " + args[1];
            }

            SelectablePile pile = table.getSelectablePile(pileKey);

            if (pile != null && pile.viewCard() != null) {
                player.alterCurrentSelection(pile);
                return fetchGameStatusUpdate();
            } else {
                return "Selection must have at least one card";
            }
        } else { // Invalid
            return "The select command requires one, two, or five arguments";
        }
    }

    // This attempts to draw a card from the deck and place it face up into the waste pile. If the deck is empty, it
    // attempts to refill the deck with waste pile cards.
    private String doDrawOrRefill() {
        Deck deck = table.getDeck();
        WastePile waste = (WastePile) table.getSelectablePile("waste");

        if (deck.size() == 0 && waste.viewCard() != null) { // Refill the deck
            deck.insertCardsInInvertedOrder(waste.removeAll());
            return fetchGameStatusUpdate();
        } else if (deck.size() > 0) {
            waste.addCard(deck.draw());
            return fetchGameStatusUpdate();
        } else {
            return "No cards are left in either the waste pile or draw pile to draw from.";
        }
    }

    // This tries to move the selected card from the selected source to a destination deciphered from the arguments.
    private String moveSelectionToDestination(String[] args) {
        if (player.getSelectedSource() == null) {
            return "Cannot move a card without selecting its source first";
        } else if (args.length >= 1 && args.length <= 2) {
            String pileKey = args[0];

            if (args.length == 2) {
                pileKey = args[0] + " " + args[1];
            }

            SelectablePile destination = table.getSelectablePile(pileKey);

            if (destination == null) {
                return "Invalid pile chosen as destination";
            }

            if (destination.verifyMoveIsLegal(player.getSelectedSource().viewCard())) {
                destination.addCard(player.getSelectedSource().removeCard());
                player.alterCurrentSelection(null);
                return fetchGameStatusUpdate();
            } else {
                userInterface.printGameUpdate("Cannot make an illegal move");
                return fetchGameStatusUpdate();
            }
        } else {
            return "Bad or missing arguments";
        }
    }

    // This deselects the currently selected card and requests for a game status update.
    private String deselectSourceSelection() {
        player.alterCurrentSelection(null);
        return fetchGameStatusUpdate();
    }

    // This resets the game so that a new board of cards is played out and the game starts anew.
    private String resetGame() {
        table = new Table();
        player = new Player();

        initializeGame();

        return null;
    }

    // This ends the game and stops the game loop so that the program can be safely closed.
    private void endGameAndStopLoop() {
        isGameLoopContinuing = false;
    }
}
