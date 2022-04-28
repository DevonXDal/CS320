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
    public class PlayingCardDeck<T> : Deck<T> where T : PlayingCard
    {
    }
}
