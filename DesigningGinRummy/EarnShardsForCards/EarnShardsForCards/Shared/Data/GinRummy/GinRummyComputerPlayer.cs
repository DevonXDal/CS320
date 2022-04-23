using EarnShardsForCards.Shared.Data.Enumerations;
using EarnShardsForCards.Shared.Data.GenericGameObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EarnShardsForCards.Shared.Data.GinRummy
{
    /// <summary>
    /// A GinRummyComputerPlayer is a virtual game support object related to a real-world player. 
    /// This player makes decisions in order to beat the human player. 
    /// It represents the other player playing against the one playing the game. 
    /// It maintains a hand and makes better or worse decisions based on its set skill level/difficulty.
    /// </summary>
    /// <typeparam name="T">A playing card or a subtype of playing card</typeparam>
    public class GinRummyComputerPlayer<T> : Player<T> where T : PlayingCard
    {
        public SkillLevel SkillLevel { get; set; }

        /// <summary>
        /// Create a new computer player with an empty hand with a skill level of beginner.
        /// </summary>
        public GinRummyComputerPlayer() : base()
        {
            SkillLevel = SkillLevel.Beginner;
        }

        /// <summary>
        /// Check to see if the discard pile card will be drawn by the computer player.
        /// If the discard pile is not drawn, the computer player will choose to draw a card from the deck or pass.
        /// </summary>
        /// <param name="topCardOfDiscardPile">What the card on the discard pile was</param>
        /// <returns>Whether the computer chose to draw the discard pile card</returns>
        public bool ShouldDrawFromDiscardPile(T topCardOfDiscardPile)
        {
            return false;
        }
    }
}
