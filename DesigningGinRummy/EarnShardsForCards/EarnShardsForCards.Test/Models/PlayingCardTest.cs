using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EarnShardsForCards.Test.Models
{
    /// <summary>
    /// Tests the PlayingCard class to ensure that it conforms to that specified in the design document.
    /// 
    /// Axioms:
    /// I.	(card.new(rank, suit, value)).Rank == rank
    ///II.	(card.new (rank, suit, value)).Suit == suit
    ///III. card.new (rank, suit, value)
    ///card.Show()
    ///card.GetImageFilePath() == “~/img/PlayingCard/{rank
    ///}
    ///-{ suit}.webp”
    ///IV. card.new (rank, suit, value)
    ///card.GetImageFilePath() == “~/ img / PlayingCard / Back.webp”
    ///V. card.new (rank, suit, value)
    ///card.Value == value
    ///VI.	card.new(rank, suit, value)
    /// card.Equals({another card with the same rank and suit}) == true
    ///card.Equals({ another card with a different rank}) == false
    ///card.Equals({ another card with a different suit}) == false
    ///card.Equals({ another card with both a different rank and a different suit}) == false
    /// </summary>
    public class PlayingCardTest
    {
        public PlayingCardTest()
        {
            
        }

        
    }
}
