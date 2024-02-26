package model.strategy;

import model.game.Game;
import model.Team;
import model.game.GameContext;
import model.participant.Participant;
import model.participant.ResultParticipant;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// a bracket generation strategy that uses double elimination: i.e. to be eliminated, any team must lose twice
public class DoubleEliminationStrategy extends SingleEliminationStrategy implements Strategy {
    // REQUIRES: canGenerateSchedule(teams)
    // MODIFIES: ctx
    // EFFECTS: generates a schedule for the given teams using a double elimination strategy
    @Override
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

    // REQUIRES: winners.size() == losers.size()
    // MODIFIES: ctx
    // EFFECTS: pairs the winners and losers of a round of games, into a list of
    //          participants
    private List<Game> pairParticipants(GameContext ctx, List<Game> winners, List<Game> losers) {
        List<Participant> participants = new ArrayList<>();

        for (int i = 0; i < winners.size(); i++) {
            participants.add(new ResultParticipant(winners.get(i), false));
            participants.add(new ResultParticipant(losers.get(i), true));
        }

        return ctx.pairParticipants(participants);
    }

    // EFFECTS: serialize this strategy to a JSON object
    @Override
    public JSONObject serialize() {
        return new JSONObject().put("type", "DoubleEliminationStrategy");
    }
}
