using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EarnShardsForCards.Shared.Data.GenericGameObjects
{
    /// <summary>
    /// A DiscardPile represents the real-world pile of cards that grows throughout the play of the game. 
    /// It is accessible from the top and is able to be cleared out. 
    /// It can be used as a source of cards for various games. 
    /// It may be at points empty and at other points full of cards.
    /// </summary>
    /// <typeparam name="T">The type of card that this discard pile contains</typeparam>
    public class DiscardPile<T> where T : Card
    {
        public IList<Card> Cards { get; protected set; }

        public static string EmptyImageRepresentation { get; protected set; }

        /// <summary>
        /// Create a discard pile with no cards
        /// </summary>
        public DiscardPile()
        {
            Cards = new List<Card>();
        }

        /// <summary>
        /// Add a card to the top of this pile.
        /// </summary>
        /// <param name="card">The card to be added</param>
        /// <param name="insertFaceUp">Whether the card should be face up (true by default)</param>
        public void Add(T card, bool insertFaceUp = true)
        {
            Cards.Add(card);
        }

        /// <summary>
        /// Remove a card from the top and return it
        /// </summary>
        /// <returns>The card removed and returned from the top of the pile</returns>
        public T Draw()
        {
            return null;
        }

        /// <summary>
        /// Returns the current image representation for this pile.
        /// </summary>
        /// <returns>The image representation of this discard pile</returns>
        public string GetImageFilePath()
        {
            return EmptyImageRepresentation;
        }

        /// <summary>
        /// Empties the discard pile of all of its cards but does not return them.
        /// </summary>
        public void Clear()
        {
            Cards.Clear();
        }
    }
}
