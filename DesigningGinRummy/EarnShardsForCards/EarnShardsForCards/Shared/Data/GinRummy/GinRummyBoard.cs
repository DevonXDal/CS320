using EarnShardsForCards.Shared.Data.GenericGameObjects;
using EarnShardsForCards.Shared.Data.Interfaces;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EarnShardsForCards.Shared.Data.GinRummy
{
    /// <summary>
    /// A GinRummyBoard represents the real-world table that Gin Rummy is played on. 
    /// In the aspect of the virtual game, it is used as an object to hold the other objects. 
    /// The one game support capability it includes not typical of a normal table is knowing 
    /// whether the pass button is being shown.
    /// </summary>
    public class GinRummyBoard
    {
        public GinRummyComputerPlayer<PlayingCard> ComputerPlayer { get; private set; }
        public Player<PlayingCard> Player { get; private set; }
        public PlayingCardDeck<PlayingCard> Deck { get; private set; }
        public DiscardPile<PlayingCard> DiscardPile { get; private set; }

        /// <summary>
        /// Create a new GinRummyBoard holding information related to the board and its players
        /// </summary>
        public GinRummyBoard(IConfiguration config, IGinRummyController controller)
        {
            ComputerPlayer = new GinRummyComputerPlayer<PlayingCard>(config, controller);
            Player = new Player<PlayingCard>();

            Deck = new PlayingCardDeck<PlayingCard>();
            DiscardPile = new DiscardPile<PlayingCard>();
        }
    }
}
