using EarnShardsForCards.Shared.Data.Enumerations;
using EarnShardsForCards.Shared.Data.GenericGameObjects;
using EarnShardsForCards.Shared.Data.Interfaces;
using EarnShardsForCards.Shared.Data.Records;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EarnShardsForCards.Shared.Data.GinRummy
{
    /// <summary>
    /// A GinRummyController is a virtual game support object specifically for Gin Rummy '
    /// that handles the general game loop taken by the game. 
    /// Once created, only one copy of it will exist and many objects will have references 
    /// to that one reference. When an action is performed the controller can be notified 
    /// and the action carried out if able. When the game is started or restarted, the 
    /// controller will handle the setup of the game board.
    /// </summary>
    public class GinRummyController : IGinRummyController
    {
        private GinRummyBoard _board;
        private GinRummyScoreHandler _scoreHandler;
        private GinRummyGameState _gameState;
        private Notifier _notifier;
        private bool _awaitingDisplayFOrEndOfRound;
        private static GinRummyController? _controller;

        /// <summary>
        /// Create a new controller reference to use with the game.
        /// </summary>
        private GinRummyController()
        {
            _board = new GinRummyBoard();

        }

        /// <summary>
        /// Gets the singleton instance of the controller and sets one up if one does not already exist.
        /// </summary>
        /// <returns>The singleton of the controller</returns>
        public static GinRummyController GetInstance()
        {
            if (_controller == null)
            {
                _controller = new GinRummyController();
            }

            return _controller;
        }

        /// <summary>
        /// Set up the models and controller objects necessary to run a game.
        /// </summary>
        public void InitializeGame()
        {

        }

        /// <summary>
        /// Restart the game, performs similar to initialize the game but ensures that the notifier is recreated.
        /// </summary>
        public void ReinitializeGame()
        {

        }

        /// <summary>
        /// Try to pass the human player's turn if validation succeeds.
        /// Must be the player's special draw phase.
        /// </summary>
        /// <exception cref="InvalidOperationException">Thrown with a message why the action was not done when an illegal action occured</exception>
        public void RequestPassTurn()
        {

        }

        /// <summary>
        /// Try to perform a draw from deck action after validation for the human player.
        /// Must be the player's normal draw phase.
        /// </summary>
        /// <exception cref="InvalidOperationException">Thrown with a message why the action was not done when an illegal action occured</exception>
        public void RequestDrawFromDeck()
        {

        }

        /// <summary>
        /// Try to perform a draw from discard action after validation for the human player.
        /// Must be the player's draw phase.
        /// </summary>
        /// <exception cref="InvalidOperationException">Thrown with a message why the action was not done when an illegal action occured</exception>
        public void RequestDrawFromDiscard()
        {

        }

        /// <summary>
        /// Try to perform a discard action after validation for the human player.
        /// Must be the player's discard phase.
        /// </summary>
        /// <exception cref="InvalidOperationException">Thrown with a message why the action was not done when an illegal action occured</exception>
        public void RequestDiscardWithCardAt(int index)
        {

        }

        /// <summary>
        /// Try to perform a knock action after validation for the human player.
        /// Must be the player's discard phase.
        /// </summary>
        /// <exception cref="InvalidOperationException">Thrown with a message why the action was not done when an illegal action occured</exception>
        public void RequestKnockWithCardAt(int index)
        {

        }

        /// <summary>
        /// Try to reposition the cards within the human player's hand. 
        /// Must be the player's turn.
        /// </summary>
        /// <exception cref="InvalidOperationException">Thrown with a message why the action was not done when an illegal action occured</exception>
        public void RequestCardReposition(int initialIndex, int newIndex)
        {

        }

        /// <summary>
        /// Get the deadwood remaining for the computer player.
        /// </summary>
        /// <param name="handToCalculateWith">The hand to calculate with. This hand should be either 10 or 11 cards</param>
        /// <returns>The deadwood remaining for the computer player</returns>
        public int CheckComputerPlayerDeadwood(IList<PlayingCard> handToCalculateWith)
        {
            return 0;
        }

        /// <summary>
        /// Receive Indication that display of end of round information has begun displaying
        /// </summary>
        public void NotifyThatEndOfRoundIsDisplayed()
        {

        }

        /// <summary>
        /// Recieve notice that the end of round display is done displaying and the next round/game should begin.
        /// </summary>
        public void EndOfRoundDisplayIsFinished()
        {

        }

        /// <summary>
        /// Recieve results to document about the end of the round.
        /// </summary>
        /// <param name="winner">The reference to the player that won</param>
        /// <param name="points">The amount of points won by the player</param>
        /// <param name="reason">The round ending reason</param>
        /// <param name="laidOffDeadwood">The amount of deadwood laid off by the non-knocking player. Defaults to 0</param>
        public void DocumentRoundResults(Player<PlayingCard> winner, int points, GinRummyRoundEndingCase reason, int laidOffDeadwood = 0)
        {

        }

        /// <summary>
        /// Returns data used to render graphical elements to the screen after each state update.
        /// </summary>
        /// <returns>The data the view needs to redisplay itself</returns>
        public GinRummyViewData FetchViewData()
        {
            return null;
        }

        /// <summary>
        /// Returns the data for the end of display to work.
        /// </summary>
        /// <returns>End of round related information</returns>
        public EndOfRoundData FetchEndOfRoundData()
        {
            return null;
        }

        /// <summary>
        /// Checks to see if Big Gin has just occured.
        /// </summary>
        public void CheckForBigGin()
        {

        }

        /// <summary>
        /// Checks to see if some player has won the game.
        /// </summary>
        public void CheckIfWinConditionIsMet()
        {
            
        }

        /// <summary>
        /// Sets up the next round with the player who did not earn points last round going first.
        /// A tie will have the game move to the next player's turn and have them start.
        /// </summary>
        public void SetupNextRound()
        {

        }
    }
}
