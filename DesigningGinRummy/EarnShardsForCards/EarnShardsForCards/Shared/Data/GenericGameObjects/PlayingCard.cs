using EarnShardsForCards.Shared.Data.Enumerations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EarnShardsForCards.Shared.Data.GenericGameObjects
{
    public class PlayingCard : Card
    {
        public Rank Rank { get; init; }
        public Suit Suit { get; init; }
        public int Value { get; init; }

        /// <summary>
        /// Create the playing card, assigning it a rank, suit, and point value.
        /// </summary>
        /// <param name="rank">The alphanumeric character that aids in identification</param>
        /// <param name="suit">The shape that aids in identification</param>
        /// <param name="value">The value that the card has in relation to a game</param>
        public PlayingCard(Rank rank, Suit suit, int value) : base()
        {
            Rank = rank;
            Suit = suit;
            Value = value;
        }

        /// <summary>
        /// Checks to see if both cards have the same rank and suit combination.
        /// </summary>
        /// <param name="obj">The other object, likely a playing card, to check</param>
        /// <returns>Are both the same card?</returns>
        public override bool Equals(object? obj)
        {
            return false;
        }
    }
}
