package model.strategy;

import model.game.Game;
import model.Team;
import model.game.GameContext;
import model.participant.Participant;
import model.participant.ResultParticipant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DoubleEliminationStrategy extends SingleEliminationStrategy implements Strategy {
    // REQUIRES: teams.size() > 0 && (teams.size() & (teams.size() - 1)) == 0
    // MODIFES: ctx
    // EFFECTS: generates a schedule for the given teams using a double elimination strategy
    public List<Game> generateSchedule(GameContext ctx, List<Team> teams) {
        if (teams.size() < 4) {
            return super.generateSchedule(ctx, teams);
        }

        List<List<Game>> winnersBracket = super.generateRounds(ctx, teams);
        List<Game> losersBracket = ctx.pairGames(winnersBracket.get(0), false);

        List<Game> allGames = new ArrayList<>(losersBracket);

        for (List<Game> round : winnersBracket) {
            allGames.addAll(round);

            if (losersBracket.size() < round.size()) {
                continue;
            }

            while (losersBracket.size() > round.size()) {
                losersBracket = ctx.pairGames(losersBracket, true);
                allGames.addAll(losersBracket);
            }

            losersBracket = pairParticipants(ctx, round, losersBracket);
            allGames.addAll(losersBracket);
        }

        Game upperBracketFinal = winnersBracket.get(winnersBracket.size() - 1).get(0);
        Game lowerBracketFinal = losersBracket.get(0);
        allGames.add(ctx.createGame(upperBracketFinal, lowerBracketFinal, true, true));

        return allGames.stream().sorted(Comparator.comparingInt(Game::getId)).collect(Collectors.toList());
    }

    // MODIFES: ctx
    // EFFECTS: pairs the winners and losers of a round of games, into a list of
    //          participants; throws IllegalArgumentException if the winners and
    //          losers lists have different sizes
    private List<Game> pairParticipants(GameContext ctx, List<Game> winners, List<Game> losers) {
        List<Participant> participants = new ArrayList<>();

        if (winners.size() != losers.size()) {
            throw new IllegalArgumentException("Winners and losers must have the same size");
        }

        for (int i = 0; i < winners.size(); i++) {
            participants.add(new ResultParticipant(winners.get(i), false));
            participants.add(new ResultParticipant(losers.get(i), true));
        }

        return ctx.pairParticipants(participants);
    }
}
