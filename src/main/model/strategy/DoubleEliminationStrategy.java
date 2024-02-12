package model.strategy;

import model.Game;
import model.Team;
import model.participant.Participant;
import model.participant.ResultParticipant;
import model.participant.TeamParticipant;

import java.util.ArrayList;
import java.util.List;

public class DoubleEliminationStrategy extends SingleEliminationStrategy implements Strategy {
    // REQUIRES: teams.size() > 0 && (teams.size() & (teams.size() - 1)) == 0
    // EFFECTS: generates a schedule for the given teams using a double elimination strategy
    public List<Game> generateSchedule(List<Team> teams) {
        List<Game> winnersBracket = super.generateSchedule(teams);
        List<Game> losersBracket = new ArrayList<>();

        int id = 1 + winnersBracket.size();
        int roundSize = teams.size() >> 1;
        int currentIndex = roundSize;

        List<Participant> participants = new ArrayList<>();

        for (int i = 0; i < roundSize; i++) {
            participants.add(new ResultParticipant(winnersBracket.get(i), false));
        }

        roundSize >>= 1;

        while (roundSize > 1) {
            if (participants.size() > roundSize) {
                List<Game> round = generateRound(id, participants);
                id += round.size();
                losersBracket.addAll(round);
                continue;
            }

            List<Participant> newParticipants = new ArrayList<>();

            for (int i = 0; i < roundSize; i++) {
                newParticipants.add(new ResultParticipant(winnersBracket.get(currentIndex + i), false));
                newParticipants.add(new ResultParticipant(losersBracket.get(i), true));
            }

            List<Game> round = generateRound(id, newParticipants);
            id += round.size();
            losersBracket.addAll(round);

            participants.clear();

            for (Game game : round) {
                participants.add(new ResultParticipant(game, true));
            }

            currentIndex += roundSize;
            roundSize >>= 1;
        }

        winnersBracket.addAll(losersBracket);
        return winnersBracket;
    }
}
