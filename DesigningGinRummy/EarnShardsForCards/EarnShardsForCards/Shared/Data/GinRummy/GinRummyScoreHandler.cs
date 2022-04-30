using EarnShardsForCards.Shared.Data.Enumerations;
using EarnShardsForCards.Shared.Data.GenericGameObjects;
using EarnShardsForCards.Shared.Data.Interfaces;
using EarnShardsForCards.Shared.Models.Records;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static EarnShardsForCards.Shared.Helpers.GeneralAssistanceMethods;

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
        private bool _isAroundTheWorld;

        /// <summary>
        /// Create a GinRummyScoreHandler with a reference to the controller, human player, and computer player.
        /// </summary>
        /// <param name="controller">The game controller</param>
        /// <param name="player">The user's player</param>
        /// <param name="computerPlayer">The computer player</param>
        /// <param name="isAroundTheWorld">Whether or not the game is the around the world variation</param>
        public GinRummyScoreHandler(
            IGinRummyController controller, 
            Player<PlayingCard> player, 
            GinRummyComputerPlayer<PlayingCard> computerPlayer,
            bool isAroundTheWorld = false)
        {
            _controller = controller;
            _humanPlayer = player;
            _computerPlayer = computerPlayer;
            _isAroundTheWorld = isAroundTheWorld;
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
            IList<MeldSet> setsRemovingDeadwood = setsFromOpponent ?? new List<MeldSet>();
            IList<MeldRun> runsRemovingDeadwood = runsFromOpponents ?? new List<MeldRun>();

            // Clone the player's hand
            IList<PlayingCard> hand = player.Hand.ToList();

            FindMeldSets(hand, setsRemovingDeadwood); // Adds sets formed by the hand to the list of sets to remove deadwood from
            FindMeldRuns(hand, runsRemovingDeadwood); // Adds runs formed by the hand to the list of runs to remove deadwood from

            List<List<IMeld>> meldCombinations = new();

            foreach (MeldSet set in setsRemovingDeadwood) // Add the initial sets as single meld combinations
            {
                var meldCombination = new List<IMeld>();
                meldCombination.Add(set);
                meldCombinations.Add(meldCombination);
            }

            foreach (MeldRun run in runsRemovingDeadwood) // Add the initial runs as single meld combinations
            {
                var meldCombination = new List<IMeld>();
                meldCombination.Add(run);
                meldCombinations.Add(meldCombination);
            }

            meldCombinations.Add((List<IMeld>)setsRemovingDeadwood);
            meldCombinations.Add((List<IMeld>)runsRemovingDeadwood);

            foreach (List<IMeld> meldCombination in meldCombinations) // For each meld combination
            {
                var deadwood = GetRemainingDeadwoodCards(hand, meldCombination); // Get the deadwood cards that can be eliminated
                RecursivelyIdentifyMeldCombinations(deadwood, meldCombinations, meldCombination); // Recursively identify all possible meld combinations
            }

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

        // Find all the meld sets that can be created from the provided cards
        private void FindMeldSets(IList<PlayingCard> handCards, IList<MeldSet> currentSets)
        {
            // Cards for the hand sorted by rank and then suit
            List<PlayingCard> sortedHandForSetDiscoveries = handCards.OrderBy(c => c.Rank).ThenBy(c => c.Suit).ToList();

            // Index of first card with that rank
            int firstCardWithRank = -1;

            // Index of last card with that rank
            int lastCardWithRank = -1;

            // Previous card's rank
            Rank previousRank = Rank.Ace;

            for (int i = 0; i < handCards.Count; i++) 
            {
                PlayingCard card = handCards[i];

                if (i == handCards.Count - 1)
                {
                    // If the current card is the last card in the hand,
                    // then the current card is the last card with that rank
                    lastCardWithRank = i;
                } else if (card.Rank != previousRank)
                {
                    // If the current card's rank is different than the previous card's rank,
                    // then the previous card is the last card with that rank
                    lastCardWithRank = i - 1;
                }

                if ((i == handCards.Count - 1 || card.Rank != previousRank && lastCardWithRank != -1) && lastCardWithRank - firstCardWithRank >= 2)
                {
                    // If the current card's rank is not the same as the previous card's rank,
                    // and the last card with that rank has been found,
                    // then create a set from the cards with that rank
                    MeldSet set = (MeldSet) MeldSet.GenerateMeldFromCards((sortedHandForSetDiscoveries.GetRange(firstCardWithRank, firstCardWithRank + 2)));
                    currentSets.Add(set);

                    if (set.CanAddNewCard(handCards[lastCardWithRank]))
                    {
                        // If the set can be extended with the next card,
                        // then extend the set with the next card
                        currentSets.Add((MeldSet) set.Insert(handCards[lastCardWithRank]));
                    }

                    // Reset the first and last card with that rank
                    firstCardWithRank = i;
                    lastCardWithRank = -1;
                } else if (card.Rank != previousRank)
                {
                    // If the previous card's rank was not the same as the current card's rank,
                    // then the current card is the first card with that rank
                    firstCardWithRank = i;
                }

                previousRank = card.Rank;
            }


        }

        // Find meld runs that can be created from the provided cards
        private void FindMeldRuns(IList<PlayingCard> handCards, IList<MeldRun> currentRuns)
        {
            // Cards for the hand sorted by suit and then rank
            List<PlayingCard> sortedHandForRunDiscoveries = handCards.OrderBy(c => c.Suit).ToList();
            sortedHandForRunDiscoveries = (List<PlayingCard>)(new List<PlayingCard>())
                .Concat(sortedHandForRunDiscoveries.Where(c => c.Suit == Suit.Clubs))
                .Concat(sortedHandForRunDiscoveries.Where(c => c.Suit == Suit.Diamonds))
                .Concat(sortedHandForRunDiscoveries.Where(c => c.Suit == Suit.Hearts))
                .Concat(sortedHandForRunDiscoveries.Where(c => c.Suit == Suit.Spades)
                .ToList());

            // The previous card's suit
            Suit? previousSuit = null;

            // The previous card's rank
            Rank? previousRank = null;

            // Number of cards with the same rank in a row
            int runLength = 0;

            // Index of the first card with the same rank
            int firstCardOfRun = -1;

            // Index of the last card with the same rank
            int lastCardOfRun = -1;

            for (int i = 0; i < handCards.Count; i++)
            {
                PlayingCard card = handCards[i];

                if (i == handCards.Count - 1)
                {
                    // If the current card is the last card in the hand,
                    // then the current card is the last card with that rank
                    lastCardOfRun = i;
                } else if (card.Rank != previousRank + 1 || card.Suit != previousSuit)
                {
                    // If the current card's rank is different than the previous card's rank,
                    // then the previous card is the last card with that rank
                    lastCardOfRun = i - 1;
                }

                if (lastCardOfRun != -1 && runLength >= 3) // If the last card of the run is found and the run is at least three long
                {
                    for (int j = firstCardOfRun; j <= lastCardOfRun - 2; j++) // For each possible starting number of a run (3.4.5.6,7); (4,5,6,7), (5,6,7), etc.
                    {
                        // If the current card's rank is not the same as the previous card's rank,
                        // and the last card with that rank has been found,
                        // then create a set from the cards with that rank
                        MeldRun run = (MeldRun)MeldRun.GenerateMeldFromCards((sortedHandForRunDiscoveries.GetRange(j, j + 2)), _isAroundTheWorld);
                        currentRuns.Add(run);

                        for (int k = j + 3; k <= lastCardOfRun; k++) // Add each possible run combination such as (3,4,5); (3,4,5,6), (3,4,5,6,7), etc.
                        {
                            if (run.CanAddNewCard(handCards[k])) // Check if the new card is valid before adding
                            {
                                // If the run can be extended with the next card,
                                // then extend the run with the next card
                                currentRuns.Add((MeldRun)run.Insert(handCards[k]));
                            }
                        }
                    }
                    

                    // Reset the first and last card with that rank
                    firstCardOfRun = i;
                    lastCardOfRun = -1;
                } else if (card.Rank != previousRank + 1 || card.Suit != previousSuit)
                {
                    // If the current card's rank is not the same as the previous card's rank + 1,
                    // or the current card's suit is not the same as the previous card's suit,
                    // then the current card is the first card with the same rank
                    firstCardOfRun = i;
                    runLength = 0; // This will get incremented before next iteration.
                }

                previousRank = card.Rank; // Set the previous rank to the current card's rank
                previousSuit = card.Suit; // Set the previous suit to the current card's suit
                runLength++; // Increase the run length by one to show that the current card is part of the run

            }
        }
        
        // This takes in a list of cards to use to find melds, the combinations list to extend, and the list of current melds to locate
        private void RecursivelyIdentifyMeldCombinations(IList<PlayingCard> cardsRemaining, List<List<IMeld>> meldCombinations, List<IMeld> meldCombinationToFurther)
        {
            if (cardsRemaining.Count < 3)
            {
                // If there are less than three cards left, then there are no more combinations to find
                return;
            }

            // Try to form one or more combinations from meld sets
            List<MeldSet> meldSets = new();
            FindMeldSets(cardsRemaining, meldSets);

            // If any meld sets were found, then add them to the combinations list and recurse more deeplu
            if (meldSets.Count > 0)
            {
                foreach (MeldSet meldSet in meldSets)
                {
                    List<IMeld> newMeldCombination = new List<IMeld>(meldCombinationToFurther);
                    newMeldCombination.Add(meldSet);
                    meldCombinations.Add(newMeldCombination);
                    RecursivelyIdentifyMeldCombinations(GetRemainingDeadwoodCards(cardsRemaining, newMeldCombination), meldCombinations, newMeldCombination);
                }
            }

            // Try to form one or more combinations from meld runs
            List<MeldRun> meldRuns = new();
            FindMeldRuns(cardsRemaining, meldRuns);

            // If any meld runs were found, then add them to the combinations list and recurse more deeplu
            if (meldRuns.Count > 0)
            {
                foreach (MeldRun meldRun in meldRuns)
                {
                    List<IMeld> newMeldCombination = new List<IMeld>(meldCombinationToFurther);
                    newMeldCombination.Add(meldRun);
                    meldCombinations.Add(newMeldCombination);
                    RecursivelyIdentifyMeldCombinations(GetRemainingDeadwoodCards(cardsRemaining, newMeldCombination), meldCombinations, newMeldCombination);
                }
            }
        }

        // Get remaining deadwood cards
        private List<PlayingCard> GetRemainingDeadwoodCards(IList<PlayingCard> originallyRemainingCards, IList<IMeld> meldCombination)
        {
            List<PlayingCard> deadwood = originallyRemainingCards.ToList(); // Clone the hand again

            foreach (IMeld meld in meldCombination) // Eliminate cards already used
            {
                // https://stackoverflow.com/questions/3944803/use-linq-to-get-items-in-one-list-that-are-not-in-another-list
                deadwood.RemoveAll(deadwoodCard => meld.Cards.Any(meldCard => meldCard.Equals(deadwoodCard))); // Remove cards already used in melds
            }

            return deadwood;
        }
    }
}
