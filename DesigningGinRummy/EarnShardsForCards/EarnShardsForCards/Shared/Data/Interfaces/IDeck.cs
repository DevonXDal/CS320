using EarnShardsForCards.Shared.Data.GenericGameObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EarnShardsForCards.Shared.Data.Interfaces
{
    /// <summary>
    /// Interface for a game deck that identifies the common functions of each type of deck.
    /// </summary>
    public abstract class Deck
    {
        /// <summary>
        /// Returns the number of cards that are currently in the deck.
        /// </summary>
        /// <returns>The number of cards in the deck</returns>
        public abstract int Count();

        /// <summary>
        /// Remove and return a card from the top of the deck.
        /// </summary>
        /// <returns>The card from the top of the deck</returns>
        public abstract Card Draw();

        /// <summary>
        /// Randomizes the positions of cards in the deck.
        /// </summary>
        public abstract void Shuffle();
    }
}
