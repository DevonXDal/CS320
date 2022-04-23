using EarnShardsForCards.Shared.Data.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EarnShardsForCards.Shared.Data.GenericGameObjects
{
    /// <summary>
    /// A Playing Card Deck represents a real-world deck of cards. It is also used for the draw pile for real-world games. 
    /// It is created usually with 52 playing cards. Cards can be drawn from it and return as requested. 
    /// It also randomizes the position of the cards it holds when requested.
    /// </summary>
    public class PlayingCardDeck : Deck
    {
        protected IList<PlayingCard> cards;

        /// <summary>
        /// Reveal the number of cards that are currently in the deck.
        /// </summary>
        /// <returns>The number of cards left in the deck</returns>
        public override int Count()
        {
            return cards.Count;
        }

        /// <summary>
        /// Draw a card from the top of the deck.
        /// </summary>
        /// <returns>The top card of the deck</returns>
        public override PlayingCard Draw()
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Randomize the position of the cards
        /// </summary>
        public override void Shuffle()
        {
            
        }
    }
}
