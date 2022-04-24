using EarnShardsForCards.Shared.Data.GenericGameObjects;
using EarnShardsForCards.Shared.Data.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EarnShardsForCards.Shared.Data.GinRummy
{
    /// <summary>
    /// A MeldSet is more of a virtual game support object than a real-world object. 
    /// It represents the three or four cards that a player lays down at the end of a round 
    /// that form a three-of-a-kind or four-of-a-kind such that there are three or four cards 
    /// with the same rank but differing suits. Its primary purpose is to assist the scoring 
    /// at the end of a round.
    /// </summary>
    public class MeldSet : IMeld
    {
        public IList<PlayingCard> Cards { get; private set; }

        // Creates the meld using the cards provided.
        private MeldSet(IList<PlayingCard> cards)
        {
            Cards = cards;
        }

        /// <summary>
        /// Verifies that a new card can be added to the meld set
        /// </summary>
        /// <param name="newCard">The new card to be added to the set</param>
        /// <returns>Whether the new card can be added</returns>
        public bool CanAddNewCard(PlayingCard newCard)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Determines the deadwood that is removed from this set being formed.
        /// </summary>
        /// <returns>The deadwood removed by the set</returns>
        public int DeadwoodRemovedByMeld()
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Create a meld set from three or four cards that have the same rank
        /// </summary>
        /// <param name="cards">The cards used to create the meld</param>
        /// <returns>Either the meld that was created, or if the meld cannot be formed by the cards, null</returns>
        public IMeld? GenerateMeldFromCards(IList<PlayingCard> cards)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Adds a card to the meld without verification that the card can be added.
        /// </summary>
        /// <param name="card">The card to be added</param>
        public IMeld Insert(PlayingCard card)
        {
            throw new NotImplementedException();
        }
    }
}
