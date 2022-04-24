using EarnShardsForCards.Shared.Data.Enumerations;
using EarnShardsForCards.Shared.Data.GenericGameObjects;
using EarnShardsForCards.Shared.Data.Interfaces;
using EarnShardsForCards.Shared.Models.Records;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EarnShardsForCards.Shared.Data.GinRummy
{
    /// <summary>
    /// A GinRummyScoreHandler is a virtual game support object that separates the 
    /// handling of deadwood, knocking, Gin, and similar aspects from the main controller class.
    /// </summary>
    public class GinRummyScoreHandler
    {
        private Player<PlayingCard> _humanPlayer;
        private GinRummyComputerPlayer<PlayingCard> _computerPlayer;
        private IGinRummyController _controller;

        /// <summary>
        /// Create a GinRummyScoreHandler with a reference to the controller, human player, and computer player.
        /// </summary>
        /// <param name="controller">The game controller</param>
        /// <param name="player">The user's player</param>
        /// <param name="computerPlayer">The computer player</param>
        public GinRummyScoreHandler(
            IGinRummyController controller, 
            Player<PlayingCard> player, 
            GinRummyComputerPlayer<PlayingCard> computerPlayer)
        {
            _controller = controller;
            _humanPlayer = player;
            _computerPlayer = computerPlayer;
        }

        /// <summary>
        /// Calculates the number of points at the end of around. Once points are calculated
        /// it handles notifying of the score and other information to the user.
        /// </summary>
        /// <param name="isBigGin">Whether the round ended by Big Gin</param>
        public void RewardPoints(bool isBigGin)
        {

        }

        /// <summary>
        /// Documents the round results by notifying the controller.
        /// </summary>
        /// <param name="winner">A reference to whoever won the round</param>
        /// <param name="points">The points to award that player</param>
        /// <param name="reason">The reason the round ended</param>
        private void DocumentResults(Player<PlayingCard> winner, int points, GinRummyRoundEndingCase reason)
        {

        }

        /// <summary>
        /// Determine the largest amount of deadwood that can be eliminated for a player.
        /// Also returns the melds created in order allow laying off deadwood if needed.
        /// </summary>
        /// <param name="player">The player to view the hand of</param>
        /// <param name="setsFromOpponent">The sets the opponent eliminated deadwood using</param>
        /// <param name="runsFromOpponents">The runs the opponent eliminated deadwood using</param>
        /// <returns>Data related to removed deadwood</returns>
        public EliminateDeadwoodData EliminateDeadwood(
            Player<PlayingCard> player, 
            IList<MeldSet>? setsFromOpponent, 
            IList<MeldRun>? runsFromOpponents)
        {
            return null;
        }

        /// <summary>
        /// Determine if the provided player can knock given the cards in their hand.
        /// </summary>
        /// <param name="player">The player to see if knocking is allowed</param>
        /// <returns>True if the player can knock (10 or less deadwood)</returns>
        public bool CanPlayerKnock(Player<PlayingCard> player)
        {
            return false;
        }

        /// <summary>
        /// Determine if the provided player has gone Big Gin this turn.
        /// </summary>
        /// <param name="player">The player to see if they have Big Gin</param>
        /// <returns>True if the player has Big Gin</returns>
        public bool DoesPlayerHaveBigGin(Player<PlayingCard> player)
        {
            return false;
        }
    }
}
