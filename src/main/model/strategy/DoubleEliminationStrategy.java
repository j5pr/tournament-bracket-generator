package model.strategy;

import model.Game;
import model.Team;
import model.participant.Participant;
import model.participant.ResultParticipant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DoubleEliminationStrategy extends SingleEliminationStrategy implements Strategy {
    // REQUIRES: teams.size() > 0 && (teams.size() & (teams.size() - 1)) == 0
    // EFFECTS: generates a schedule for the given teams using a double elimination strategy
    public List<Game> generateSchedule(List<Team> teams) {
        List<Game> allGames = new ArrayList<>();

        List<List<Game>> winnersBracket = super.generateRounds(teams);
        int id = 1 + winnersBracket.size();

        List<Game> losersBracket = generateRoundFromGames(id, winnersBracket.get(0), false);
        id += losersBracket.size();

        for (List<Game> round : winnersBracket) {
            allGames.addAll(round);

            while (losersBracket.size() > round.size()) {
                losersBracket = generateRoundFromGames(id, losersBracket, true);
                id += losersBracket.size();
                allGames.add(losersBracket.get(0));
            }

            List<Participant> participants = new ArrayList<>();

            for (int i = 0; i < round.size(); i++) {
                participants.add(new ResultParticipant(round.get(i), false));
                participants.add(new ResultParticipant(losersBracket.get(i), true));
            }

            losersBracket = generateRound(id, participants);
            id += losersBracket.size();
            allGames.addAll(losersBracket);
        }

        allGames.sort(Comparator.comparingInt(Game::getId));
        return allGames;
    }

    // REQUIRES: games.size() > 0 && (games.size() & (games.size() - 1)) == 0
    // EFFECTS: generates a round of games with the starting id, and a list of
    //          games that precedes this round and the winner argument provided
    private List<Game> generateRoundFromGames(int id, List<Game> games, boolean winner) {
        List<Participant> participants = new ArrayList<>();

        for (Game game : games) {
            participants.add(new ResultParticipant(game, winner));
        }

        return super.generateRound(id, participants);
    }
}
