package services;

import java.util.ArrayList;
import java.util.List;

/**
 * FightDetails is a collection of rounds which saves the details of a fight.
 */
public class FightDetails {

    private int roundsNumber;
    private List<Round> rounds;

    public FightDetails() {
        this.roundsNumber = 0;
        this.rounds = new ArrayList<>();
    }

    public void addRound(Round round){
        this.rounds.add(round);
        this.roundsNumber += 1;
    }

    public int getRoundsNumber() {
        return roundsNumber;
    }

    public Round getRoundByRoundNumber(int roundNumber){
        return this.rounds.get(roundNumber);
    }
}
